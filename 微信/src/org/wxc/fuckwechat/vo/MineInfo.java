package org.wxc.fuckwechat.vo;

public class MineInfo {
	// 本地头像地址
	private String iconLocalUri;
	// 网络头像地址
	private String iconRemoteUrl;
	// ID
	private String myId;
	// 姓名
	private String myName;
	// 地区
	private int myFrom;
	// 性别
	private int mySex;
	// 个性签名
	private String mySign;

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

	public String getMyName() {
		return myName;
	}

	public void setMyName(String myName) {
		this.myName = myName;
	}

	public int getMyFrom() {
		return myFrom;
	}

	public void setMyFrom(int myFrom) {
		this.myFrom = myFrom;
	}

	public int getMySex() {
		return mySex;
	}

	public void setMySex(int mySex) {
		this.mySex = mySex;
	}

	public String getMySign() {
		return mySign;
	}

	public void setMySign(String mySign) {
		this.mySign = mySign;
	}

	public String getMyId() {
		return myId;
	}

	public void setMyId(String myId) {
		this.myId = myId;
	}

	

}
