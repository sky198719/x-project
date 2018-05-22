package com.xxd.h5.service.finance;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.xxd.common.bo.ErrorMessage;
import com.xxd.common.bo.Message;
import com.xxd.common.bo.SuccessMessage;
import com.xxd.common.enums.ApiEnum;
import com.xxd.common.enums.MessageStatus;
import com.xxd.common.exception.CoreException;
import com.xxd.common.exception.ServiceException;
import com.xxd.common.extension.spring.GlobalProperties;
import com.xxd.common.remoting.http.parameters.Headers;
import com.xxd.common.remoting.http.parameters.QueryStrings;
import com.xxd.common.util.JsonUtil;
import com.xxd.h5.common.constants.Constants;
import com.xxd.h5.common.enums.BannerTypeEnum;
import com.xxd.h5.common.enums.ProductTypeEnum;
import com.xxd.h5.service.H5BaseService;
import com.xxd.h5.service.H5UrlService;
import com.xxd.h5.service.transform.CouponTransformService;
import com.xxd.h5.service.user.UserService;
import com.xxd.h5.vo.coupon.CouponVO;
import com.xxd.h5.vo.finance.*;
import com.xxd.h5.vo.home.BannerVo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

/**
 * 理财相关业务处理层.
 * @author zhangshengwen
 */
@Service
public class FinanceService extends H5BaseService {

    @Autowired
    private H5UrlService h5UrlService;
    @Autowired
    private CouponTransformService couponTransformService;

    @Autowired
    private UserService userService;

    /**
     * 获取产品
     *
     * @return
     */
    public Message getHotProducts(String clientId, String token, String userAgent) throws CoreException, ServiceException, Exception {

        // 广告页
        Headers headers = Headers.createHeaders()
                .addHeader("token", token)
                .addHeader("clientId", clientId)
                .addHeader("clientTime", System.currentTimeMillis())
                .addHeader("User-Agent", userAgent);

        // 热门理财 非登录和登陆数据展现的顺序不一致,需要分别调用两个接口
        Message hotProductMessage;
        boolean login = userService.isLogin(headers);
        if (StringUtils.isEmpty(token)  || !login) {
            hotProductMessage = apiUtil.get(ApiEnum.API_INVESTMENT_FINANCES_HOT, headers);
        } else {
            hotProductMessage = apiUtil.get(ApiEnum.API_INVESTMENT_FINANCES_HOT_LOGIN, headers);
        }
        if (hotProductMessage.getCode() != MessageStatus.SUCCESS.getStatus()) {
            throw new ServiceException(hotProductMessage.getMessage());
        }

        // 聚合数据
        List<HotProductVo> products = Lists.newArrayList();
        JSONArray hotProductArray = JsonUtil.toJSONArray(hotProductMessage.getData());
        for (Iterator<Object> iterator = hotProductArray.iterator(); iterator.hasNext(); ) {
            Object object = iterator.next();
            if (object == null) {
                break;
            }
            products.add(fillFieldOfHotProduct(JsonUtil.toJSONObject(object)));
        }
        return new SuccessMessage(products);
    }


    /**
     * 获取理财产品详情
     */
    public Message financeDetail(String clientId, String token, String productId, int productType, String userAgent) throws CoreException, ServiceException, Exception {
        Headers headers = Headers.createHeaders()
                .addHeader("clientId", clientId)
                .addHeader("clientTime", System.currentTimeMillis())
                .addHeader("User-Agent", userAgent);

        String productCode = transformService.convertType2PinYin(productType);
        if (StringUtils.isBlank(productCode)) {
            throw new ServiceException("error parameter");
        }
        if (StringUtils.isBlank(productId)) {
            throw new ServiceException("error parameter");
        }

        ProductDetailVO productDetailVO = new ProductDetailVO();

        // 1 产品加入记录
        QueryStrings pageQueryString = QueryStrings.create()
                .setPath("/").addPath(productId)
                .addParameter("currentPage", 1)
                .addParameter("pageSize", 20)
                .addParameter("productCode", productCode);
        Message joinRecordMessage = apiUtil.get(ApiEnum.API_INVESTMENT_FINANCES_JOIN_RECORD, headers, pageQueryString);


        String accumulatedInvestors = "0";
        String totalCount = "0";
        if (joinRecordMessage != null && joinRecordMessage.getCode() == MessageStatus.SUCCESS.getStatus()) {
            // 产品加入人数
            accumulatedInvestors = JsonUtil.toJSONObject(joinRecordMessage.getData()).getString("accumulatedInvestors");
            totalCount = JsonUtil.toJSONObject(joinRecordMessage.getData()).getString("totalCount");

            productDetailVO.setAccumulatedInvestors(accumulatedInvestors);
            productDetailVO.setTotalCount(totalCount);
        }

        // 2 产品详情
        QueryStrings queryStrings = QueryStrings.create()
                .addPath("/").addPath(productCode).addPath("/").addPath(productId);
        Message message;
        if (StringUtils.isBlank(token)) {
            message = apiUtil.get(ApiEnum.API_INVESTMENT_FINANCES_PRODUCT_DETAIL, headers, queryStrings);
        }
        // 登陆状态下详情查询
        else {
            headers.addHeader("token", token);
            message = apiUtil.get(ApiEnum.API_INVESTMENT_FINANCES_PRODUCT_DETAIL_LOGIN, headers, queryStrings);
        }

        if(message.getCode() != MessageStatus.SUCCESS.getStatus()){
            return new ErrorMessage(message.getMessage());
        }

        JSONObject jsonObject = JsonUtil.toJSONObject(message.getData());
        ProductDetailVO.ProductDetail detail = fillFieldOfProductDetail(jsonObject, token);
        detail.setAccumulatedInvestors(accumulatedInvestors);
        // 3 可用红包
        if (StringUtils.isNotBlank(token)) {
            QueryStrings redPackageQueryString = QueryStrings.create()
                    .addParameter("currentPage", 1)
                    .addParameter("pageSize", 20);
            Message redPackageMessage = apiUtil.get(ApiEnum.API_INVESTMENT_PRIZE_REDPACKAGES, headers, redPackageQueryString);
            if (redPackageMessage != null &&
                    redPackageMessage.getCode() == MessageStatus.SUCCESS.getStatus()) {
                CouponVO vo = couponTransformService.transformHasCanUse(JsonUtil.toJSONObject(redPackageMessage.getData()));
                detail.setHaveRedPacket(vo.getHasCanUse());
            }
        }

        productDetailVO.setProductDetail(detail);
        return new SuccessMessage(productDetailVO);
    }


    /**
     * 热门理财产品增加一些文案信息
     *
     * @param jsonObject
     * @return
     */
    public HotProductVo fillFieldOfHotProduct(JSONObject jsonObject) {
        if (jsonObject == null) {
            return new HotProductVo();
        }

        int productType = getProductType(jsonObject);

        switch (productType) {
            case Constants.PRODUCT_QTDS_TYPE:
                HotProductVo qtds = JsonUtil.toObject(jsonObject, HotProductVo.class);
                if (qtds == null) {
                    return new HotProductVo();
                }
                qtds.setProductType(ProductTypeEnum.PRODUCT_QTDS.getType());
                qtds.setTipAction(Constants.PRODUCT_QTDS_RECOMMEND_TIPACTION);
                qtds.setIntroduction(Constants.PRODUCT_QTDS_RECOMMEND_INTRODUCTION);
                return qtds;
            case Constants.PRODUCT_30_DAY_TYPE:
                HotProductVo xs30 = JsonUtil.toObject(jsonObject, HotProductVo.class);
                if (xs30 == null) {
                    return new HotProductVo();
                }
                xs30.setProductType(ProductTypeEnum.PRODUCT_XS30T.getType());
                xs30.setTipAction(Constants.PRODUCT_30_DAY_RECOMMEND_TIPACTION);
                xs30.setIntroduction(Constants.PRODUCT_30_DAY_RECOMMEND_INTRODUCTION);
                return xs30;
            case Constants.PRODUCT_BBGS_TYPE:
                HotProductVo bbgs = JsonUtil.toObject(jsonObject, HotProductVo.class);
                if (bbgs == null) {
                    return new HotProductVo();
                }
                bbgs.setProductType(ProductTypeEnum.PRODUCT_BBGS.getType());
                bbgs.setTipAction(Constants.PRODUCT_BBGS_RECOMMEND_TIPACTION);
                bbgs.setIntroduction(Constants.PRODUCT_BBGS_RECOMMEND_INTRODUCTION);
                return bbgs;
            case Constants.PRODUCT_YJDJ_TYPE:
                HotProductVo yjdj = JsonUtil.toObject(jsonObject, HotProductVo.class);
                if (yjdj == null) {
                    return new HotProductVo();
                }
                yjdj.setProductType(ProductTypeEnum.PRODUCT_YJDJ.getType());
                yjdj.setTipAction(Constants.PRODUCT_YJDJ_RECOMMEND_TIPACTION);
                yjdj.setIntroduction(Constants.PRODUCT_YJDJ_RECOMMEND_INTRODUCTION);
                return yjdj;
            case Constants.PRODUCT_XYB_TYPE:
                List<HotProductVo.Item> xybList = JsonUtil.toList(jsonObject, HotProductVo.Item.class);
                if (CollectionUtils.isNotEmpty(xybList)) {
                    for (HotProductVo.Item item : xybList) {
                        item.setProductType(ProductTypeEnum.PRODUCT_XYB.getType());
                        item.setTipAction(Constants.PRODUCT_XYB_RECOMMEND_TIPACTION);
                        item.setIntroduction(Constants.PRODUCT_XYB_RECOMMEND_INTRODUCTION);
                    }
                }
                HotProductVo item = new HotProductVo();
                boolean isHaveValue = jsonObject.get("isHaveValue") != null ? jsonObject.getBoolean("isHaveValue") : false;
                item.setIsHaveValue(isHaveValue);
                item.setName(Constants.PRODUCT_XYB_NAME);
                item.setProductType(ProductTypeEnum.PRODUCT_XYB.getType());
                item.setTipAction(Constants.PRODUCT_XYB_RECOMMEND_TIPACTION);
                item.setIntroduction(Constants.PRODUCT_XYB_RECOMMEND_INTRODUCTION);
                item.setMultipleDeadline(xybList);
                return item;
            case Constants.PRODUCT_YYP_TYPE:
                List<HotProductVo.Item> yypList = JsonUtil.toList(jsonObject, HotProductVo.Item.class);
                if (CollectionUtils.isNotEmpty(yypList)) {
                    for (HotProductVo.Item t : yypList) {
                        t.setProductType(ProductTypeEnum.PRODUCT_YYP.getType());
                        t.setTipAction(Constants.PRODUCT_YYP_RECOMMEND_TIPACTION);
                        t.setIntroduction(Constants.PRODUCT_YYP_RECOMMEND_INTRODUCTION);
                    }
                }
                HotProductVo i = new HotProductVo();
                boolean isHaveValue2 = jsonObject.get("isHaveValue") != null ? jsonObject.getBoolean("isHaveValue") : false;
                i.setIsHaveValue(isHaveValue2);
                i.setName(Constants.PRODUCT_YYP_NAME);
                i.setProductType(ProductTypeEnum.PRODUCT_YYP.getType());
                i.setTipAction(Constants.PRODUCT_YYP_RECOMMEND_TIPACTION);
                i.setIntroduction(Constants.PRODUCT_YYP_RECOMMEND_INTRODUCTION);
                i.setMultipleDeadline(yypList);
                return i;
            default:
                return new HotProductVo();
        }
    }





    /**
     * 解析产品对应的类型.
     *
     * @param jsonObject
     * @return
     */
    public int getProductType(JSONObject jsonObject) {
        Object productTypeObj = jsonObject.get("productType");
        int productType = -1;
        // 正常的封装理财产品
        if (productTypeObj != null) {
            productType = Integer.parseInt(productTypeObj.toString());
        }
        // 散标、债券
        else {
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            if (jsonArray != null && jsonArray.size() > 0) {
                JSONObject bidOrBond = jsonArray.getJSONObject(0);
                if (bidOrBond != null) {
                    Object obj = bidOrBond.get("productType");
                    if (obj != null) {
                        productType = Integer.parseInt(obj.toString());
                    }
                }
            }
        }
        return productType;
    }


    /**
     * 产品详情填充h5链接.
     *
     * @param jsonObject
     * @return
     */
    public ProductDetailVO.ProductDetail fillFieldOfProductDetail(JSONObject jsonObject, String token) {

        if (jsonObject == null) {
            return new ProductDetailVO.ProductDetail();
        }

        int productType = getProductType(jsonObject);
        String productId = jsonObject.getString("productId");
        ProductDetailVO.ProductDetail data = JsonUtil.toObject(jsonObject, ProductDetailVO.ProductDetail.class);
        if (null == data) {
            return new ProductDetailVO.ProductDetail();
        }
        switch (productType) {
            case Constants.PRODUCT_QTDS_TYPE:
                data.setProductType(ProductTypeEnum.PRODUCT_QTDS.getType());
                data.setTipAction(Constants.PRODUCT_QTDS_TIPACTION);
                data.setIntroduction(Constants.PRODUCT_QTDS_INTRODUCTION);
                data.setProductProtocolH5url(h5UrlService.getProductProtocol(productType, token, productId));
                data.setProductH5url(h5UrlService.getProductIntroduce(productType, productId));
                data.setProductQAH5url(h5UrlService.getProductQA(productType, productId));
                data.setDangerTipH5url(h5UrlService.getDangerTipH5url());
                data.setShareUrl(h5UrlService.getProductShardUrl(productType, ""));
                return data;
            case Constants.PRODUCT_30_DAY_TYPE:
                data.setProductType(ProductTypeEnum.PRODUCT_XS30T.getType());
                data.setTipAction(Constants.PRODUCT_30_DAY_TIPACTION);
                data.setIntroduction(Constants.PRODUCT_30_DAY_INTRODUCTION);
                data.setProductProtocolH5url(h5UrlService.getProductProtocol(productType, token, productId));
                data.setProductH5url(h5UrlService.getProductIntroduce(productType, productId));
                data.setProductQAH5url(h5UrlService.getProductQA(productType, productId));
                data.setDangerTipH5url(h5UrlService.getDangerTipH5url());
                data.setShareUrl(h5UrlService.getProductShardUrl(productType, ""));
                return data;
            case Constants.PRODUCT_BBGS_TYPE:
                data.setProductType(ProductTypeEnum.PRODUCT_BBGS.getType());
                data.setTipAction(Constants.PRODUCT_BBGS_TIPACTION);
                data.setIntroduction(Constants.PRODUCT_BBGS_INTRODUCTION);
                data.setProductProtocolH5url(h5UrlService.getProductProtocol(productType, token, productId));
                data.setProductH5url(h5UrlService.getProductIntroduce(productType, productId));
                data.setProductQAH5url(h5UrlService.getProductQA(productType, productId));
                data.setDangerTipH5url(h5UrlService.getDangerTipH5url());
                data.setShareUrl(h5UrlService.getProductShardUrl(productType, ""));
                return data;
            case Constants.PRODUCT_YJDJ_TYPE:
                data.setProductType(ProductTypeEnum.PRODUCT_YJDJ.getType());
                data.setTipAction(Constants.PRODUCT_YJDJ_TIPACTION);
                data.setIntroduction(Constants.PRODUCT_YJDJ_INTRODUCTION);
                data.setProductProtocolH5url(h5UrlService.getProductProtocol(productType, token, productId));
                data.setProductH5url(h5UrlService.getProductIntroduce(productType, productId));
                data.setProductQAH5url(h5UrlService.getProductQA(productType, productId));
                data.setDangerTipH5url(h5UrlService.getDangerTipH5url());
                data.setShareUrl(h5UrlService.getProductShardUrl(productType, ""));
                return data;
            case Constants.PRODUCT_XYB_TYPE:
                data.setProductType(ProductTypeEnum.PRODUCT_XYB.getType());
                data.setTipAction(Constants.PRODUCT_XYB_TIPACTION);
                data.setIntroduction(Constants.PRODUCT_XYB_INTRODUCTION);
                data.setProductProtocolH5url(h5UrlService.getProductProtocol(productType, token, productId));
                data.setProductH5url(h5UrlService.getProductIntroduce(productType, productId));
                data.setProductQAH5url(h5UrlService.getProductQA(productType, productId));
                data.setDangerTipH5url(h5UrlService.getDangerTipH5url());
                data.setShareUrl(h5UrlService.getProductShardUrl(productType, productId));
                data.setPeriods(jsonObject.getJSONObject("periods"));
                return data;
            case Constants.PRODUCT_YYP_TYPE:
                data.setProductType(ProductTypeEnum.PRODUCT_YYP.getType());
                data.setTipAction(Constants.PRODUCT_YYP_TIPACTION);
                data.setIntroduction(Constants.PRODUCT_YYP_INTRODUCTION);
                data.setProductProtocolH5url(h5UrlService.getProductProtocol(productType, token, productId));
                data.setProductH5url(h5UrlService.getProductIntroduce(productType, productId));
                data.setProductQAH5url(h5UrlService.getProductQA(productType, productId));
                data.setDangerTipH5url(h5UrlService.getDangerTipH5url());
                data.setShareUrl(h5UrlService.getProductShardUrl(productType, productId));
                JSONArray yypItemsJSONArray = jsonObject.getJSONArray("items");
                if (yypItemsJSONArray != null) {
                    data.setItems(JsonUtil.toList(yypItemsJSONArray, ProductDetailVO.Item.class));
                }
                return data;
            case Constants.PRODUCT_BID_TYPE:
                data.setProductType(ProductTypeEnum.PRODUCT_BID.getType());
                data.setTipAction(GlobalProperties.getProperty("app.text.bid.tips"));
                data.setProductProtocolH5url(h5UrlService.getProductProtocol(productType, token, productId));
                data.setProductH5url(h5UrlService.getProductIntroduce(productType, productId));
                data.setProductQAH5url(h5UrlService.getProductQA(productType, productId));
                data.setDangerTipH5url(h5UrlService.getDangerTipH5url());
                data.setProductContractH5url(h5UrlService.getProductContractUrl());
                data.setBorrowDetailH5url(h5UrlService.getBidBorrowDetailUrlByLabel(productId, data.getLabel()));
                data.setRepaymentGuaranteeH5url(h5UrlService.getRepaymentGuaranteeUrl(data.getProductId(), data.getProductType()));
                data.setLabelName(transformService.transformLabel2LabelName(data.getLabel()));
                return data;
            case Constants.PRODUCT_ZQ_TYPE:
                data.setProductType(ProductTypeEnum.PRODUCT_ZQ.getType());
                data.setProductH5url(h5UrlService.getProductIntroduce(productType, productId));
                data.setProductProtocolH5url(h5UrlService.getProductProtocol(productType, token, productId));
                data.setProductQAH5url(h5UrlService.getProductQA(productType, productId));
                data.setDangerTipH5url(h5UrlService.getDangerTipH5url());
                data.setProductContractH5url(h5UrlService.getProductContractUrl());
                data.setBorrowDetailH5url(h5UrlService.getBondBorrowDetailUrl(data.getBorrowId()));
                data.setRepaymentGuaranteeH5url(h5UrlService.getRepaymentGuaranteeUrl(data.getProductId(), data.getProductType()));
                data.setLabelName(transformService.transformLabel2LabelName(data.getLabel()));
                return data;
            default:
                return new ProductDetailVO.ProductDetail();
        }
    }

    /**
     * 产品匹配债券列表,填充合同字段.
     *
     * @return
     */
    public Borrows fillContractH5UrlFieldOfBorrow(Borrows borrows) {
        if (borrows == null || borrows.getItems() == null) {
            return borrows;
        }
        for (Borrows.BorrowVO vo : borrows.getItems()) {
            vo.setProductContractH5url(h5UrlService.getProductContractUrl());
        }
        return borrows;
    }

    /**
     * 获取标的列表.
     *
     * @param clientId
     * @param currentPage
     * @param pageSize
     * @return
     * @throws ServiceException
     * @throws Exception
     */
    public Message getBids(String clientId, int currentPage, int pageSize, String userAgent) throws CoreException, ServiceException, Exception {
        return getBidOrCreditor(ApiEnum.API_INVESTMENT_FINANCES_BIDS, clientId, currentPage, pageSize, userAgent);
    }

    /**
     * 获取债券列表.
     *
     * @param clientId
     * @param currentPage
     * @param pageSize
     * @return
     * @throws ServiceException
     * @throws Exception
     */
    public Message getCreditors(String clientId, int currentPage, int pageSize, String userAgent) throws CoreException, ServiceException, Exception {
        return getBidOrCreditor(ApiEnum.API_INVESTMENT_FINANCES_CREDITORS, clientId, currentPage, pageSize, userAgent);
    }

    /**
     * 债券和散标共同方法.
     *
     * @param apiEnum     调用散标和债券的列表接口.
     * @param clientId
     * @param currentPage
     * @param pageSize
     * @return
     * @throws ServiceException
     * @throws Exception
     */
    private Message getBidOrCreditor(ApiEnum apiEnum, String clientId, int currentPage, int pageSize, String userAgent) throws CoreException, ServiceException, Exception {
        Headers headers = Headers.createHeaders()
                .addHeader("clientId", clientId)
                .addHeader("clientTime", System.currentTimeMillis())
                .addHeader("User-Agent", userAgent);
        // 广告页 现在只有 1  后台
        QueryStrings bannerQueryString = QueryStrings.create()
                .addParameter("position", BannerTypeEnum.BANNER_TYPE_START.getType());
        Message bannerMessage = apiUtil.get(ApiEnum.API_INVESTMENT_HOME_BANNERS, headers, bannerQueryString);
        if (bannerMessage.getCode() != MessageStatus.SUCCESS.getStatus()){
            throw new ServiceException("bad request");
        }

        // 列表详情
        QueryStrings queryStrings = QueryStrings.create()
                .addParameter("currentPage", currentPage)
                .addParameter("pageSize", pageSize);
        Message bidMessage = apiUtil.get(apiEnum, headers, queryStrings);
        if (bidMessage.getCode() != MessageStatus.SUCCESS.getStatus()){
            throw new ServiceException("bad request");
        }

        // 聚合数据
        List<BannerVo> ads = JsonUtil.toList(bannerMessage.getData(), BannerVo.class);
        List<BidCreditorDTO.Item> products = JsonUtil.toList(bidMessage.getData(), BidCreditorDTO.Item.class);
        BidCreditorDTO dto = new BidCreditorDTO();
        dto.setAds(ads);
        dto.setProducts(products);
        dto = dto.transform();
        return new SuccessMessage(dto);
    }



    public Message getLoanInfo(Headers headers, String  bidCode){

        BidInfoVo bidInfoVo = new BidInfoVo();

        QueryStrings loadInfoQuery = QueryStrings.create()
                .addPath("/").addPath(bidCode).addPath("/loanInfoDisclosure");

        Message loanInfoData = apiUtil.get(ApiEnum.API_USERCENTER_BORROWER_INFO, headers, loadInfoQuery);

        if (loanInfoData.getCode() != MessageStatus.SUCCESS.getStatus()) {
            throw new ServiceException(loanInfoData.getMessage());
        }
        JSONObject loanObject = JsonUtil.toJSONObject(loanInfoData.getData());
        if (StringUtils.isEmpty(loanObject.getString("code"))){
            BidInfoVo.LoanInfo loanInfo = JsonUtil.toObject(loanObject, BidInfoVo.LoanInfo.class);
            bidInfoVo.setLoanInfo(loanInfo);
            bidInfoVo.setLoanStatus(1);
        }

        QueryStrings bidInfoQuery = QueryStrings.create()
                .addPath("/").addPath(bidCode);
        Message bidInfoData = apiUtil.get(ApiEnum.API_USERCENTER_BORROWER_INFO, headers, bidInfoQuery);

        if (bidInfoData.getCode() != MessageStatus.SUCCESS.getStatus()) {
            throw new ServiceException(bidInfoData.getMessage());
        }

        JSONObject bidInfoObject = JsonUtil.toJSONObject(bidInfoData.getData());
        BidInfoVo.BidInfo bidInfo = JsonUtil.toObject(bidInfoObject, BidInfoVo.BidInfo.class);
        bidInfoVo.setBidInfo(bidInfo);

        return new SuccessMessage(bidInfoVo);
    }


    public Message getXydInfo(Headers headers, String bidCode){


        XydDetailVo xydDetailVo = new XydDetailVo();

        QueryStrings bidInfoQuery = QueryStrings.create()
                .setPath("/").addPath(bidCode);
        Message bidInfoMessage = apiUtil.get(ApiEnum.API_INTEGRATION_BIDS_BORROWER, headers, bidInfoQuery);
        if (bidInfoMessage.getCode() == MessageStatus.SUCCESS.getStatus()){
            xydDetailVo.setBidInfo(bidInfoMessage.getData());
        }else {
            xydDetailVo.setBidInfo("");
        }

        QueryStrings borrowerQuery = QueryStrings.create()
                .setPath("/").addPath(bidCode).addPath("/borrower");
        Message borrowerMessage = apiUtil.get(ApiEnum.API_INTEGRATION_BIDS_BORROWER, headers, borrowerQuery);
        if (borrowerMessage.getCode() == MessageStatus.SUCCESS.getStatus()){
            xydDetailVo.setBorrowerInfo(borrowerMessage.getData());
        }else {
            xydDetailVo.setBorrowerInfo("");
        }

        QueryStrings loansQuery = QueryStrings.create()
                .setPath("/").addPath(bidCode).addPath("/loans")
                .addParameter("currentPage",1)
                .addParameter("pageSize", 10);
        Message loansMessage = apiUtil.get(ApiEnum.API_INTEGRATION_BIDS_BORROWER, headers, loansQuery);
        if (loansMessage.getCode() == MessageStatus.SUCCESS.getStatus()){
            xydDetailVo.setLoansInfo(loansMessage.getData());
        }else {
            xydDetailVo.setLoansInfo("");
        }


        QueryStrings queryStrings = QueryStrings.create()
                .setPath("/").addPath(bidCode).addPath("/infoDisclosures");
        Message infoDisclosuresMessage = apiUtil.get(ApiEnum.API_INTEGRATION_BIDS_BORROWER, headers, queryStrings);
        if (infoDisclosuresMessage.getCode() == MessageStatus.SUCCESS.getStatus()){
            xydDetailVo.setInfoDisclosures(infoDisclosuresMessage.getData());
        }else {
            xydDetailVo.setInfoDisclosures("");
        }

        return new SuccessMessage(xydDetailVo);

    }





}
