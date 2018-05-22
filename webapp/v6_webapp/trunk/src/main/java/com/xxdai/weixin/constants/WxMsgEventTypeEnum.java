/**
 * 
 */
package com.xxdai.weixin.constants;


public enum WxMsgEventTypeEnum {
	SUBSCRIBE(WxMsgEventType.SUBSCRIBE),
	UNSUBSCRIBE(WxMsgEventType.UNSUBSCRIBE),
	SCAN(WxMsgEventType.SCAN),
	LOCATION(WxMsgEventType.LOCATION),
	CLICK(WxMsgEventType.CLICK),
	VIEW(WxMsgEventType.VIEW);
	/**
	 * @param text
	 */
	WxMsgEventTypeEnum(final String text) {
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
	
	public static WxMsgEventTypeEnum inst(String strVal) {
		for (WxMsgEventTypeEnum type : WxMsgEventTypeEnum.values()) {
			if (type.toString().equalsIgnoreCase(strVal)) {
				return type;
			}
		}
		return null;
	}
}
