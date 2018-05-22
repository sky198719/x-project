package com.xxd.h5.service;

import com.xxd.common.extension.spring.GlobalProperties;
import com.xxd.h5.common.constants.Constants;
import com.xxd.h5.common.enums.BrrowStatusEnum;
import com.xxd.h5.service.transform.TransformService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 统一获取
 * h5页面的url.
 * @author zhangshengwen
 */
@Service
public class H5UrlService extends H5BaseService {

    /**
     * 获取h5风险提示页面.
     *
     * @return
     */
    public String getDangerTipH5url() {
        return GlobalProperties.getProperty("app.url.web.risk");
    }


    /**
     * 返回产品对应的QA.
     *
     * @param productType
     * @return
     */
    public String getProductQA(int productType, String productId) {

        String url;
        switch (productType) {
            case Constants.PRODUCT_BBGS_TYPE:
                url = GlobalProperties.getProperty("app.url.web.bbgs.qa");
                break;
            case Constants.PRODUCT_QTDS_TYPE:
                url = GlobalProperties.getProperty("app.url.web.qtds.qa");
                break;
            case Constants.PRODUCT_YJDJ_TYPE:
                url = GlobalProperties.getProperty("app.url.web.yjdj.qa");
                break;
            case Constants.PRODUCT_XYB_TYPE:
                url = GlobalProperties.getProperty("app.url.web.xyb.qa");
                break;
            case Constants.PRODUCT_YYP_TYPE:
                url = GlobalProperties.getProperty("app.url.web.yyp.qa");
                break;
            case Constants.PRODUCT_30_DAY_TYPE:
                url = GlobalProperties.getProperty("app.url.web.xs30.qa");
                break;
            default:
                url = "";
        }
        if (StringUtils.isNotBlank(url)) {
            url = addProductCodeParameter(url, transformService.convertType2PinYin(productType));
            url = addProductIdParameter(url, productId);
        }
        return url;
    }

    /**
     * 获取产品的服务协议.
     *
     * @param productType
     * @param token
     * @param productId
     * @param productJoinId 只有投资详情页才需要传递该值.
     * @return
     */
    public String getProductProtocol(int productType, String token, String productId, String productJoinId) {

        String url;
        switch (productType) {
            case Constants.PRODUCT_BBGS_TYPE:
                url = GlobalProperties.getProperty("app.url.web.bbgs.agreement");
                break;
            case Constants.PRODUCT_QTDS_TYPE:
                url = GlobalProperties.getProperty("app.url.web.qtds.agreement");
                break;
            case Constants.PRODUCT_YJDJ_TYPE:
                url = GlobalProperties.getProperty("app.url.web.yjdj.agreement");
                break;
            case Constants.PRODUCT_XYB_TYPE:
                url = GlobalProperties.getProperty("app.url.web.xyb.agreement");
                break;
            case Constants.PRODUCT_YYP_TYPE:
                url = GlobalProperties.getProperty("app.url.web.yyp.agreement");
                break;
            case Constants.PRODUCT_30_DAY_TYPE:
                url = GlobalProperties.getProperty("app.url.web.xs30.agreement");
                break;
            default:
                url = "";
        }
        if (StringUtils.isNotBlank(url)) {
            url = addTokenParameter(url, token);
            url = addProductIdParameter(url, productId);
            url = addProductCodeParameter(url, transformService.convertType2PinYin(productType));
            url = addProductJoinIdParameter(url, productJoinId);
        }
        return url;
    }

    /**
     * 获取产品的服务协议.
     *
     * @param productType
     * @param token
     * @param productId
     * @return
     */
    public String getProductProtocol(int productType, String token, String productId) {
        return getProductProtocol(productType, token, productId, "");
    }

    /**
     * 返回首页最下面图片url.
     *
     * @return
     */
    public String getBottomPictureUrl() {
        return GlobalProperties.getProperty("app.url.picture.bottomBanner");
    }

    /**
     * 获取产品介绍页面url.
     *
     * @param productType
     * @return
     */
    public String getProductIntroduce(int productType, String productId) {

        String url;
        switch (productType) {
            case Constants.PRODUCT_BBGS_TYPE:
                url = GlobalProperties.getProperty("app.url.web.bbgs.introduce");
                break;
            case Constants.PRODUCT_QTDS_TYPE:
                url = GlobalProperties.getProperty("app.url.web.qtds.introduce");
                break;
            case Constants.PRODUCT_YJDJ_TYPE:
                url = GlobalProperties.getProperty("app.url.web.yjdj.introduce");
                break;
            case Constants.PRODUCT_XYB_TYPE:
                url = GlobalProperties.getProperty("app.url.web.xyb.introduce");
                break;
            case Constants.PRODUCT_YYP_TYPE:
                url = GlobalProperties.getProperty("app.url.web.yyp.introduce");
                break;
            case Constants.PRODUCT_30_DAY_TYPE:
                url = GlobalProperties.getProperty("app.url.web.xs30.introduce");
                break;
            default:
                url = "";
        }
        if (StringUtils.isNotBlank(url)) {
            url = addProductIdParameter(url, productId);
            url = addProductCodeParameter(url, transformService.convertType2PinYin(productType));
        }
        return url;
    }

    /**
     * 获取用户授权协议.
     *
     * @return
     */
    public String getUserAgreementUrl() {
        return GlobalProperties.getProperty("app.url.web.userAgreement");
    }

    /**
     * 散标债券合同页url.
     *
     * @return
     */
    public String getProductContractUrl() {
        return GlobalProperties.getProperty("app.url.web.bidAndBond.contract");
    }

    /**.
     * 获取散标借款详情。
     * 根据不同的散标类型返回不同的详情页面。
     * @return
     */
    public String getBidBorrowDetailUrlByLabel(String id, String label) {
        String url = GlobalProperties.getProperty("app.url.web.bid.detail");
        // 消费贷，单独搞一个页面
        if (Constants.LABEL_TYPE_XFJRXJD.equals(label)) {
            url = GlobalProperties.getProperty("app.uri.web.bid.xfd.detail");
        }
        return addBorrowIdParameter(url, id);
    }

    /**.
     * 获取债权转让借款详情。
     * @param id 原借款散标id
     * @return
     */
    public String getBondBorrowDetailUrl(String id) {
        String url = GlobalProperties.getProperty("app.url.web.bond.detail");
        return addBorrowIdParameter(url, id);
    }

    /**
     * 获取还款保障措施页面url.
     *
     * @return
     */
    public String getRepaymentGuaranteeUrl(String productId, int productType) {
        String url = GlobalProperties.getProperty("api.url.web.repaymentGuarantee");
        url = addProductIdParameter(url, productId);
        url = addProductCodeParameter(url, transformService.convertType2PinYin(productType));
        return url;
    }

    /**
     * 新手注册红包页.
     *
     * @return
     */
    public String getRegisterRedPackageUrl() {
        return GlobalProperties.getProperty("api.url.web.registerRedPackage");
    }

    /**
     * 获取七天大胜红包页面.
     *
     * @return
     */
    public String getQtdsUrl() {
        return GlobalProperties.getProperty("api.url.web.qtds");
    }


    /**
     * 请求h5的url添加产品id参数.
     *
     * @param url
     * @return
     */
    public String addProductIdParameter(String url, String productId) {
        if (StringUtils.isBlank(url)) {
            return url;
        }
        StringBuffer sb = new StringBuffer(url);
        // 如果是第一个请求参数
        if (StringUtils.indexOf(url, "?") == -1) {
            sb.append("?productId=");
        } else {
            sb.append("&productId=");
        }
        sb.append(productId);
        return sb.toString();
    }

    /**
     * 请求h5的url添加token参数.
     *
     * @param url
     * @return
     */
    public String addTokenParameter(String url, String token) {
        if (StringUtils.isBlank(url)) {
            return url;
        }
        StringBuffer sb = new StringBuffer(url);
        // 如果是第一个请求参数
        if (StringUtils.indexOf(url, "?") == -1) {
            sb.append("?token=");
        } else {
            sb.append("&token=");
        }
        sb.append(token);
        return sb.toString();
    }

    /**
     * 请求h5的url添加productCode参数.
     *
     * @param url
     * @return
     */
    public String addProductCodeParameter(String url, String productCode) {
        if (StringUtils.isBlank(url)) {
            return url;
        }
        StringBuffer sb = new StringBuffer(url);
        // 如果是第一个请求参数
        if (StringUtils.indexOf(url, "?") == -1) {
            sb.append("?productCode=");
        } else {
            sb.append("&productCode=");
        }
        sb.append(productCode);
        return sb.toString();
    }

    /**
     * 请求h5的url添加产品加入id参数.
     *
     * @param url
     * @return
     */
    public String addProductJoinIdParameter(String url, String productJoinId) {
        if (StringUtils.isBlank(url)) {
            return url;
        }
        StringBuffer sb = new StringBuffer(url);
        // 如果是第一个请求参数
        if (StringUtils.indexOf(url, "?") == -1) {
            sb.append("?productJoinId=");
        } else {
            sb.append("&productJoinId=");
        }
        sb.append(productJoinId);
        return sb.toString();
    }

    /**
     * 请求h5的url中添加产品加入id参数.
     * @param url
     * @param tenderId
     * @return
     */
    public String addTenderIdParameter(String url, String tenderId) {
        if (StringUtils.isBlank(url)) {
            return url;
        }
        StringBuffer sb = new StringBuffer(url);
        // 如果是第一个请求参数
        if (StringUtils.indexOf(url, "?") == -1) {
            sb.append("?tenderId=");
        } else {
            sb.append("&tenderId=");
        }
        sb.append(tenderId);
        return sb.toString();
    }

    /**
     * 请求h5的url中添加产品加入id参数.
     * @param url
     * @param borrowId
     * @return
     */
    public String addBorrowIdParameter(String url, String borrowId) {
        if (StringUtils.isBlank(url)) {
            return url;
        }
        StringBuffer sb = new StringBuffer(url);
        // 如果是第一个请求参数
        if (StringUtils.indexOf(url, "?") == -1) {
            sb.append("?borrowId=");
        } else {
            sb.append("&borrowId=");
        }
        sb.append(borrowId);
        return sb.toString();
    }

    /**
     * 提现说明h5.
     *
     * @param token
     * @return
     */
    public String getWithDrawUrl(String token) {
        String url = GlobalProperties.getProperty("app.url.web.draw");
        url = addTokenParameter(url, token);
        return url;
    }

    /**
     * 获取自动配标委托书url.
     *
     * @param token
     * @return
     */
    public String getProxyUrl(String token) {
        String url = GlobalProperties.getProperty("api.url.web.proxy");
        url = addTokenParameter(url, token);
        return url;
    }

    /**
     * 获取债券的转让协议url.
     * @param id 债券的加入id.
     * @param brrowStatus 债券状态
     * @return
     */
    public String getBidTransferUrl(int productType, String id, int brrowStatus) {
        String url;
        switch (productType) {
            case Constants.PRODUCT_BID_TYPE:
                url = GlobalProperties.getProperty("app.url.web.bid.transfer");
                break;
            case Constants.PRODUCT_ZQ_TYPE:
                url = GlobalProperties.getProperty("app.url.web.bid.transfer");
                break;
            default:
                return "";
        }
        // 已转让才显示具体转让人的信息
        if (BrrowStatusEnum.TRANSFERED.getStatus() == brrowStatus) {
            return addTenderIdParameter(url, id);
        }
        return url;
    }

    /**
     * 根据产品类型获取产品h5分享链接.
     * @param productType
     * @param id
     * @return
     */
    public String getProductShardUrl(int productType, String id) {
        String url;
        switch (productType) {
            case Constants.PRODUCT_BBGS_TYPE:
                url = GlobalProperties.getProperty("app.url.web.bbgs.url");
                break;
            case Constants.PRODUCT_QTDS_TYPE:
                url = GlobalProperties.getProperty("app.url.web.qtds.url");
                break;
            case Constants.PRODUCT_YJDJ_TYPE:
                url = GlobalProperties.getProperty("app.url.web.yjdj.url");
                break;
            case Constants.PRODUCT_XYB_TYPE:
                url = GlobalProperties.getProperty("app.url.web.xyb.url");
                url = url + "&planId=" + id;
                break;
            case Constants.PRODUCT_YYP_TYPE:
                url = GlobalProperties.getProperty("app.url.web.yyp.url");
                url = url + "?yypid=" + id;
                break;
            case Constants.PRODUCT_30_DAY_TYPE:
                url = GlobalProperties.getProperty("app.url.web.xs30.url");
                break;
            default:
                url = "";
        }
        return url;
    }

    public String getProblemsUrl() {
        return GlobalProperties.getProperty("app.url.web.problems.url");
    }
}
