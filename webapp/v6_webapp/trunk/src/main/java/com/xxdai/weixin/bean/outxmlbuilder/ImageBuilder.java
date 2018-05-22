package com.xxdai.weixin.bean.outxmlbuilder;

import com.xxdai.weixin.bean.WxXmlOutImageMessage;

/**
 * 图片消息builder
 *
 */
public final class ImageBuilder extends BaseBuilder<ImageBuilder, WxXmlOutImageMessage> {

  private String mediaId;

  public ImageBuilder mediaId(String media_id) {
    this.mediaId = media_id;
    return this;
  }

  public WxXmlOutImageMessage build() {
    WxXmlOutImageMessage m = new WxXmlOutImageMessage();
    setCommon(m);
    m.setMediaId(this.mediaId);
    return m;
  }
  
}
