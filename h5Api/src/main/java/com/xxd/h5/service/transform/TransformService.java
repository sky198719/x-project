package com.xxd.h5.service.transform;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xxd.common.util.JsonUtil;
import com.xxd.h5.common.constants.Constants;
import com.xxd.h5.common.enums.ProductCategoryTypeEnum;
import com.xxd.h5.common.enums.ProductTypeEnum;
import com.xxd.h5.service.H5BaseService;
import com.xxd.h5.vo.home.HomeDataVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;


/**
 * @author zhangshengwen
 * @date 2017/11/20
 */
@Service
public class TransformService extends H5BaseService {


    /**
     * 将类型转换成产品对应的拼音
     *
     * @param type 产品类型
     * @return 转换后的数据
     */
    public String convertType2PinYin(int type) {
        switch (type) {
            case Constants.PRODUCT_BBGS_TYPE:
                return Constants.PRODUCT_BBGS_PINYIN;
            case Constants.PRODUCT_QTDS_TYPE:
                return Constants.PRODUCT_QTDS_PINYIN;
            case Constants.PRODUCT_YJDJ_TYPE:
                return Constants.PRODUCT_YJDJ_PINYIN;
            case Constants.PRODUCT_XYB_TYPE:
                return Constants.PRODUCT_XYB_PINYIN;
            case Constants.PRODUCT_YYP_TYPE:
                return Constants.PRODUCT_YYP_PINYIN;
            case Constants.PRODUCT_BID_TYPE:
                return Constants.PRODUCT_BID_PINYIN;
            case Constants.PRODUCT_ZQ_TYPE:
                return Constants.PRODUCT_ZQ_PINYIN;
            case Constants.PRODUCT_30_DAY_TYPE:
                return Constants.PRODUCT_30_DAY_PINYIN;
            default:
                return "";
        }
    }


    /**
     * 将散标和债券对应的label转成汉字
     * ic-xsd --> 新
     * ic-xcd --> 车
     * ic-xfd --> 房
     * ic-xfjrxjd --> 消
     *
     * @param label
     * @return
     */
    public String transformLabel2LabelName(String label) {
        if (StringUtils.isBlank(label)) {
            return "";
        }
        label = StringUtils.trim(label);
        switch (label) {
            //车贷
            case Constants.LABEL_TYPE_XCD:
                return "车";
            // 房贷
            case Constants.LABEL_TYPE_XFD:
                return "房";
            // 商贷
            case Constants.LABEL_TYPE_XSD:
                return "商";
            case Constants.LABEL_TYPE_XFJRXJD:
                return "宜";
            default:
                return "";
        }
    }


    /**
     * 首页的精品投资产品的部分参数转换
     *
     * @param jsonObject 精品投资产品的json 对象
     * @return 返回转换后的精品产品数据对象
     */
    public HomeDataVo.WonderfulProduct wonderfulTransform(JSONObject jsonObject) {
        if (jsonObject == null) {
            return new HomeDataVo.WonderfulProduct();
        }
        int productType = jsonObject.getInteger("productType");
        HomeDataVo.WonderfulProduct data = JsonUtil.toObject(jsonObject, HomeDataVo.WonderfulProduct.class);
        if (null == data) {
            return new HomeDataVo.WonderfulProduct();
        }
        switch (productType) {
            case Constants.PRODUCT_QTDS_TYPE:
                data.setProductType(ProductTypeEnum.PRODUCT_QTDS.getType());
                data.setTipAction(Constants.PRODUCT_QTDS_TIPACTION);
                data.setIntroduction(Constants.PRODUCT_QTDS_INTRODUCTION);
                return data;
            case Constants.PRODUCT_30_DAY_TYPE:
                data.setProductType(ProductTypeEnum.PRODUCT_XS30T.getType());
                data.setTipAction(Constants.PRODUCT_30_DAY_TIPACTION);
                data.setIntroduction(Constants.PRODUCT_30_DAY_INTRODUCTION);
                return data;
            case Constants.PRODUCT_BBGS_TYPE:
                data.setProductType(ProductTypeEnum.PRODUCT_BBGS.getType());
                data.setTipAction(Constants.PRODUCT_BBGS_TIPACTION);
                data.setIntroduction(Constants.PRODUCT_BBGS_INTRODUCTION);
                return data;
            case Constants.PRODUCT_YJDJ_TYPE:
                data.setProductType(ProductTypeEnum.PRODUCT_YJDJ.getType());
                data.setTipAction(Constants.PRODUCT_YJDJ_TIPACTION);
                data.setIntroduction(Constants.PRODUCT_YJDJ_INTRODUCTION);
                return data;
            case Constants.PRODUCT_XYB_TYPE:
                data.setName("新元宝");
                data.setProductType(ProductTypeEnum.PRODUCT_XYB.getType());
                data.setTipAction(Constants.PRODUCT_XYB_TIPACTION);
                data.setIntroduction(Constants.PRODUCT_XYB_INTRODUCTION);
                return data;
            case Constants.PRODUCT_YYP_TYPE:
                data.setProductType(ProductTypeEnum.PRODUCT_YYP.getType());
                data.setTipAction(Constants.PRODUCT_YYP_TIPACTION);
                data.setIntroduction(Constants.PRODUCT_YYP_INTRODUCTION);
                return data;
            default:
                return new HomeDataVo.WonderfulProduct();
        }
    }


    /**
     * 根据菜单的code生成对应的APP页面跳转的连接.
     * 具体规则参照:@link http://gitlab.xxd.com/xxdMobile/Xxd-Finance-Files-Only/wikis/%E6%96%B0%E6%96%B0%E7%90%86%E8%B4%A2%E9%87%8D%E6%9E%84h5native%E4%BA%A4%E4%BA%92wiki
     */
    public String generateUrlByMenuCode(String menuCode) {
        menuCode = StringUtils.trimToEmpty(menuCode);
        switch (menuCode) {
            case Constants.MENU_CODE_ZCZL:
                return Constants.MENU_CODE_ZCZL_URL;
            case Constants.MENU_CODE_ZHGL:
                return Constants.MENU_CODE_ZHGL_URL;
            case Constants.MENU_CODE_YECZ:
                return Constants.MENU_CODE_YECZ_URL;
            case Constants.MENU_CODE_DQTZ:
                return Constants.MENU_CODE_DQTZ_URL;
            case Constants.MENU_CODE_HKJH:
                return Constants.MENU_CODE_HKJH_URL;
            case Constants.MENU_CODE_LSTZ:
                return Constants.MENU_CODE_LSTZ_URL;
            case Constants.MENU_CODE_JYJL:
                return Constants.MENU_CODE_JYJL_URL;
            case Constants.MENU_CODE_WDHB:
                return Constants.MENU_CODE_WDHB_URL;
            case Constants.MENU_CODE_WDXXB:
                return Constants.MENU_CODE_WDXXB_URL;
            case Constants.MENU_CODE_ZJTX:
                return Constants.MENU_CODE_ZJTX_URL;
            case Constants.MENU_CODE_WDTQ:
                return Constants.MENU_CODE_WDTQ_URL;
            case Constants.MENU_CODE_AQZX:
                return Constants.MENU_CODE_AQZX_URL;
            default:
                return "";

        }
    }


    /**
     * 将自定义的产品类型转成数据库对应的产品类型.
     * {@link ProductCategoryTypeEnum }
     */
    public int transformProductType2DataType(int productType, String label) {

        if (StringUtils.isNotBlank(label)) {
            label = StringUtils.trim(label);
            switch (label) {
                // 车贷
                case Constants.LABEL_TYPE_XCD:
                    return 14;

                // 房贷
                case Constants.LABEL_TYPE_XFD:
                    return 10;

                // 商贷
                case Constants.LABEL_TYPE_XSD:
                    return 9;
                default:
                    return -1;
            }
        }
        switch (productType) {
            case Constants.PRODUCT_BBGS_TYPE:
                return Constants.PRODUCT_BBGS_DATABASE_TYPE;
            case Constants.PRODUCT_30_DAY_TYPE:
                return Constants.PRODUCT_30_DAY_DATABASE_TYPE;
            case Constants.PRODUCT_QTDS_TYPE:
                return Constants.PRODUCT_QTDS_DATABASE_TYPE;
            case Constants.PRODUCT_YJDJ_TYPE:
                return Constants.PRODUCT_YJDJ_DATABASE_TYPE;
            case Constants.PRODUCT_XYB_TYPE:
                return Constants.PRODUCT_XYB_DATABASE_TYPE;
            case Constants.PRODUCT_YYP_TYPE:
                return Constants.PRODUCT_YYP_DATABASE_TYPE;
            case Constants.PRODUCT_XSB:
                return Constants.PRODUCT_XSB;
            default:
                return -1;
        }
    }


    /**
     * 根据产品小类转成大的分类.
     * {@link ProductCategoryTypeEnum}
     */
    public int transformProductType2ProductCategory(int productType) {

        switch (productType) {
            case Constants.PRODUCT_BBGS_TYPE:
                return 0;
            case Constants.PRODUCT_QTDS_TYPE:
                return 0;
            case Constants.PRODUCT_30_DAY_TYPE:
                return 0;
            case Constants.PRODUCT_YJDJ_TYPE:
                return 0;
            case Constants.PRODUCT_XYB_TYPE:
                return 0;
            case Constants.PRODUCT_YYP_TYPE:
                return 0;
            case Constants.PRODUCT_XSB:
                return 0;
            case Constants.PRODUCT_BID_TYPE:
                return 1;
            case Constants.PRODUCT_ZQ_TYPE:
                return 2;
            default:
                return -1;
        }
    }


}
