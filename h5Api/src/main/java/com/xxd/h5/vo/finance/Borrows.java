package com.xxd.h5.vo.finance;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * 产品债券VO.
 * @author zhangshengwen
 */
@Data
public class Borrows {

    private long totalCount;

    private List<BorrowVO> items = Lists.newArrayList();

    @Data
    public static class BorrowVO {
        /**
         * // id
         */

        private String borrowId;
        /**
         *  // 借款期限
         */

        private String period;
        /**
         * // 期限单位
         */

        private String periodUnit;
        /**
         * // 借款金额
         */
        private String account;
        /**
         * // 借款利率
         */
        private String apr;
        /**
         * // name
         */

        private String name;

        /**
         * 散标债权合同url.
         */
        private String productContractH5url;
    }

}

