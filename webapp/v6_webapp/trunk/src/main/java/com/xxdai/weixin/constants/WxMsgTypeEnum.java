package com.xxdai.weixin.constants;


public enum WxMsgTypeEnum {
	
	
	TEXT(WxMsgType.TEXT), IMAGE(WxMsgType.IMAGE), LOCATION(WxMsgType.LOCATION), 
	LINK(WxMsgType.LINK), EVENT(WxMsgType.EVENT), VIDEO(WxMsgType.VIDEO), VOICE(WxMsgType.VOICE);
	/**
	 * @param text
	 */
	private WxMsgTypeEnum(final String text) {
		this._text = text;
	}

	private final String _text;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return _text;
	}
	
	public static WxMsgTypeEnum inst(String strVal) {
		for (WxMsgTypeEnum type : WxMsgTypeEnum.values()) {
			if (type.toString().equalsIgnoreCase(strVal)) {
				return type;
			}
		}
		return null;
	}
	
}
