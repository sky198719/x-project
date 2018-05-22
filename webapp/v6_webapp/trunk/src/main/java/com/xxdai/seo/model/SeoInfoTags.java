package com.xxdai.seo.model;

import java.util.ArrayList;
import java.util.List;

public class SeoInfoTags {
    Integer id;
    String title;
    String summary;
    String pinyin;
    String tags;
    List tagList = new ArrayList();
    
    public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	
    public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
	

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List getTagList() {
        return tagList;
    }

    public void setTagList(List tagList) {
        this.tagList = tagList;
    }


}
