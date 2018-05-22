package com.xxd.h5.vo.finance;

import com.xxd.h5.common.enums.ProductStatusEnum;
import com.xxd.h5.vo.home.BannerVo;
import com.xxd.h5.vo.home.HomeDataVo;
import lombok.Data;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 标的散标公用DTO.
 * @author zhangshengwen
 */
@Data
public class BidCreditorDTO {

    /**
     * 是否有可投产品.
     */
    private int isHaveAvailableProduct;

    private List<BannerVo> ads;

    private List<Item> products;

    @Data
    public static class Item {

        private String id;

        private String name;

        private String label;

        private double plannedAnnualRate;

        private String leastPeriod;

        private String leastPeriodUnit;

        private String status;

        private String bidAmount;

        private String leftAmount;

        /**
         * 转让价-债券独有.
         */
        private String transferPrice;
    }

    public BidCreditorDTO transform() {
       if (CollectionUtils.isNotEmpty(products)) {
           if (StringUtils.equalsIgnoreCase(products.get(0).getStatus(), ProductStatusEnum.OPEN.getStatus())) {
               isHaveAvailableProduct = 1;
               return this;
           }
       }
       this.isHaveAvailableProduct = 0;
       return this;
    }

    public BidCreditorDTO(List<BannerVo> ads, List<Item> products) {
        this.ads = ads;
        this.products = products;
    }

    public BidCreditorDTO() {
    }
}
