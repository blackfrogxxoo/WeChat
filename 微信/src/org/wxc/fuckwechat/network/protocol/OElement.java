package org.wxc.fuckwechat.network.protocol;

import org.xmlpull.v1.XmlSerializer;

/**
 * 封装服务器回复结果
 * 
 * @author Shawn Wang
 * 
 */
public class OElement {
	private String errorcode;
	private String errormsg;

	public String getErrorcode() {
		return errorcode;
	}

	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
	}

	public String getErrormsg() {
		return errormsg;
	}

	public void setErrormsg(String errormsg) {
		this.errormsg = errormsg;
	}

}
