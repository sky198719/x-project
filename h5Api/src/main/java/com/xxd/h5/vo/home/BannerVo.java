package com.xxd.h5.vo.home;

import lombok.Data;

/**
 * Created by zhangshengwen on 2017/12/8.
 */
@Data
public class BannerVo {


    private String name;
    private String backgroundColor;
    private String imageUrl;
    private String url;
    private String shareTopic;
    private String shareDesc;
    private String shareImageUrl;
    private String shareUrl;

    public BannerVo() {

    }

    public BannerVo(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
