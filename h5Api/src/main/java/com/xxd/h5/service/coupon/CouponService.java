package com.xxd.h5.service.coupon;

import com.xxd.common.bo.Message;
import com.xxd.common.enums.ApiEnum;
import com.xxd.common.enums.MessageStatus;
import com.xxd.common.remoting.http.parameters.Headers;
import com.xxd.common.remoting.http.parameters.QueryStrings;
import com.xxd.common.util.JsonUtil;
import com.xxd.h5.common.enums.CanUseStatusEnum;
import com.xxd.h5.common.enums.CouponStatusEnum;
import com.xxd.h5.common.enums.CouponTypeEnum;
import com.xxd.h5.service.H5BaseService;
import com.xxd.h5.vo.coupon.CouponVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * 优惠券.
 *
 * @author zhangshengwen
 */
@Service
public class CouponService extends H5BaseService {

    /**
     * 获取优惠券、红包列表.
     *
     * @return
     */
    public CouponVO getCouponList(Headers headers, int status, int currentPage, int pageSize) {

        // 查询红包列表 0.全部,1.可使用,2.已使用,3.已过期
        QueryStrings queryStrings = QueryStrings.create()
                .addParameter("currentPage", 1)
                .addParameter("status", status)
                .addParameter("pageSize", pageSize * currentPage == 0 ? 10 : pageSize * currentPage);
        Message message = apiUtil.get(ApiEnum.API_INVESTMENT_PRIZE_REDPACKAGES, headers, queryStrings);
        CouponVO redPackageVo = null;
        if (message.getCode() == MessageStatus.SUCCESS.getStatus()) {
            redPackageVo = couponTransformService.transform(JsonUtil.toJSONObject(message.getData()));
        }
        // 查询优惠券
        // 优惠券状态：0.全部,1.可使用,2.已使用,3.已过期
        QueryStrings couponQueryStrings = QueryStrings.create()
                .addParameter("status", status);
        Message couponMessage = apiUtil.get(ApiEnum.API_TRADECENTER_COUPON_LIST, headers, couponQueryStrings);
        CouponVO couponVO = null;
        if (couponMessage.getCode() == MessageStatus.SUCCESS.getStatus()) {
            couponVO = couponTransformService.transformCoupon(JsonUtil.toJSONObject(couponMessage.getData()));
        }

        return bizSortAndPage(redPackageVo, couponVO, currentPage, pageSize);
    }

    /**
     * 获取可用优惠券、红包列表.
     *
     * @param headers
     * @param currentPage
     * @param pageSize
     * @return
     */
    public CouponVO getCanUseCouponList(Headers headers, String productId, int productType, String amount, int currentPage, int pageSize) {

        // 获取可用红包
        QueryStrings queryStrings = QueryStrings.create()
                .addParameter("currentPage", 1)
                .addParameter("status", 1)
                .addParameter("pageSize", pageSize * currentPage == 0 ? 10 : pageSize * currentPage);

        Message message = apiUtil.get(ApiEnum.API_INVESTMENT_PRIZE_REDPACKAGES, headers, queryStrings);

        CouponVO redPackageVo = null;
        if (message.getCode() == MessageStatus.SUCCESS.getStatus() &&
                message.getData() != null) {
            redPackageVo = couponTransformService.transformHasCanUse(JsonUtil.toJSONObject(message.getData()));
        }

        if (redPackageVo != null && redPackageVo.getItems() != null) {
            LinkedList<CouponVO.Item> items = redPackageVo.getItems();
            double amountLimit = 0;
            if (StringUtils.isNotEmpty(amount)){
                amountLimit = Double.valueOf(amount);
            }
            for (CouponVO.Item item : items) {
                if (item.getAmountLimit() > amountLimit || productType == 10 || productType == 16) {
                    item.setCanUse(0);
                }else {
                    item.setCanUse(1);
                }
            }
        }


        // 获取可用优惠券
        // 优惠券状态：0.全部,1.可使用,2.已使用,3.已过期
        QueryStrings couponQueryStrings = QueryStrings.create()
                .addParameter("productId", productId)
                .addParameter("productType", transformService.transformProductType2DataType(productType, null));
        if (null != amount) {
            couponQueryStrings.addParameter("amount", amount);
        }
        Message couponMessage = apiUtil.get(ApiEnum.API_TRADECENTER_COUPON_CAN_USE_LIST, headers, couponQueryStrings);
        CouponVO couponVO = null;
        if (couponMessage.getCode() == MessageStatus.SUCCESS.getStatus()) {
            couponVO = couponTransformService.transform2CanUseCoupon(JsonUtil.toJSONObject(couponMessage.getData()));
        }

        return bizSortAndPage(redPackageVo, couponVO, currentPage, pageSize);
    }

    /**
     * 对红包、优惠券结果集合并、业务排序、分页。
     *
     * @param vo
     * @param vo1
     * @return
     */
    private CouponVO bizSortAndPage(CouponVO vo, CouponVO vo1, int currentPage, int pageSize) {

        // 合并结果集
        if (vo != null && vo1 != null) {
            vo.setTotalCount(vo.getTotalCount() + vo1.getTotalCount());
            List<CouponVO.Item> itemList = vo.getItems();
            itemList.addAll(vo1.getItems());
        }

        /**
         * 结果排序
         * 1). 可用在前 2) 红包在前 3) 大金额在前
         */

        // 金额排序
        Collections.sort(vo.getItems(), new AmountSort());


        int size = vo.getTotalCount();
        LinkedList<CouponVO.Item> items = vo.getItems();


        // 红包移动到队首
        for (int i = 0; i < size; i++) {
            if (items.get(i).getType() == CouponTypeEnum.REDPACKAGE.getType()) {
                CouponVO.Item item = items.remove(i);
                items.addFirst(item);
            }
        }

        // 不可用的移动到队尾, 满足可用在前的排序规则
        LinkedList<CouponVO.Item> tempItems = new LinkedList<>();
        for (int i = size - 1; i >= 0; i--) {
            if (items.get(i).getStatus() != CouponStatusEnum.CAN_USE.getStatus() ||
                    items.get(i).getCanUse() == CanUseStatusEnum.USED.getStatus()) {
                CouponVO.Item item = items.remove(i);
                tempItems.addLast(item);
            }
        }

        Collections.sort(tempItems, new AmountSort());

        for (int i = tempItems.size() - 1; i >= 0; i--) {
            items.addLast(tempItems.get(i));
        }

        // 分页
        if (items.size() > (currentPage - 1) * pageSize) {

            if (items.size() >= currentPage * pageSize) {
                List<CouponVO.Item> items1 = items.subList((currentPage - 1) * pageSize, currentPage * pageSize);
                CouponVO result = new CouponVO();
                result.setTotalCount((currentPage - 1) * pageSize + items1.size());
                result.getItems().addAll(items1);
                if (hasCanUse(result)) {
                    result.setHasCanUse("1");
                }
                return result;
            }
            // 不足一页
            else {
                List<CouponVO.Item> items1 = items.subList((currentPage - 1) * pageSize, items.size());
                CouponVO result = new CouponVO();
                result.setTotalCount((currentPage - 1) * pageSize + items1.size());
                result.getItems().addAll(items1);
                if (hasCanUse(result)) {
                    result.setHasCanUse("1");
                }
                return result;
            }

        }
        return new CouponVO();

    }


    private boolean hasCanUse(CouponVO couponVO) {
        List<CouponVO.Item> items = couponVO.getItems();

        if (CollectionUtils.isEmpty(items)) {
            return false;
        }

        for (CouponVO.Item it : items) {
            if (it.getType() == 1) {
                //优惠券
                return true;
            } else if (it.getCanUse() == 1) {
                // 红包
                return true;
            }
        }
        return false;
    }

}

/**
 * 自定义金额排序.从小到大
 */
class AmountSort implements Comparator<CouponVO.Item> {
    @Override
    public int compare(CouponVO.Item o1, CouponVO.Item o2) {
        return o1.getAmount() >= o2.getAmount() ? 1 : 0;
    }
}
