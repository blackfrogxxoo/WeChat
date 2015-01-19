package org.wxc.fuckwechat.vo;

public class ContactInfo {
	// 本地头像地址
	private String iconLocalUri;
	// 网络头像地址
	private String iconRemoteUrl;
	// 姓名
	private String contactName;
	// 地区
	private int contactFrom;
	// 性别
	private int contactSex;
	// 个性签名
	private String contacSign;

	public String getIconLocalUri() {
		return iconLocalUri;
	}

	public void setIconLocalUri(String iconLocalUri) {
		this.iconLocalUri = iconLocalUri;
	}

	public String getIconRemoteUrl() {
		return iconRemoteUrl;
	}

	public void setIconRemoteUrl(String iconRemoteUrl) {
		this.iconRemoteUrl = iconRemoteUrl;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public int getContactFrom() {
		return contactFrom;
	}

	public void setContactFrom(int contactFrom) {
		this.contactFrom = contactFrom;
	}

	public int getContactSex() {
		return contactSex;
	}

	public void setContactSex(int contactSex) {
		this.contactSex = contactSex;
	}

	public String getContacSign() {
		return contacSign;
	}

	public void setContacSign(String contacSign) {
		this.contacSign = contacSign;
	}

}
