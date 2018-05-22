package com.xxdai.seo.model;

import java.util.List;

/**
 * 标签对应的相关文章
 *
 * @author chenjiqin
 */
public class TagInfo {
    private String tag;
    private List<SeoInfo> infos;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public List<SeoInfo> getInfos() {
        return infos;
    }

    public void setInfos(List<SeoInfo> infos) {
        this.infos = infos;
    }


}