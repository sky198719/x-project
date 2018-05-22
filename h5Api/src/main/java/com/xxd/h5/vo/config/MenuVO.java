package com.xxd.h5.vo.config;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * 用户菜单.
 * @author zhangshengwen
 */
@Data
public class MenuVO {

    private String version;

    /**
     *  所有菜单栏
     */
    private List<Buttons> quickHelp = Lists.newArrayList();

    @Data
    public static class Buttons {

        /**
         * 菜单栏名称(我的快捷菜单, 我的资产, 我的账户)
         */
        private String title;

        /**
         * 0-我的快捷菜单, 1-我的资产, 2-我的账户
         */
        private int type;

        /**
         * 按钮
         */
        private List<Button> items;
    }

    @Data
    public static class Button {

        private long id;

        /**
         * 0-我的快捷菜单, 1-我的资产, 2-我的账户
         */
        private int type;

        /**
         * 排序号
         */
        private int sortNo;

        /**
         * 按钮图片
         */
        private String imageUrl;

        /**
         * 按钮跳转链接
         */
        private String url;

        /**
         * 菜单code
         */
        private String code;
    }

}
