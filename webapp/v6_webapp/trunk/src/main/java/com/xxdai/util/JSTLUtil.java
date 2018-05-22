package com.xxdai.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;


/**
 * 时间戳转换成日期格式的jstl标签
 * @author user
 *
 */
@SuppressWarnings("serial")
public class JSTLUtil extends TagSupport {
	private String value;
	private String parttern;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getParttern() {
		return parttern;
	}

	public void setParttern(String parttern) {
		this.parttern = parttern;
	}

	@Override
	public int doStartTag() throws JspException {
		if (value == null || value.equals("")) {
			try {
				pageContext.getOut().write("");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return super.doStartTag();
		} else {
			String vv = String.valueOf(value);
			long time = 0;
			if (vv.length() < 11) {
				time = Long.valueOf(vv) * 1000;
			} else {
				time = Long.valueOf(vv);
			}

			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(time);
			SimpleDateFormat dateformat = new SimpleDateFormat(parttern);
			String s = dateformat.format(c.getTime());
			try {
				pageContext.getOut().write(s);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return super.doStartTag();
		}
	}
}