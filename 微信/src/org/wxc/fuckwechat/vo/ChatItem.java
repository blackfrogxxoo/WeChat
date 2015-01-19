package org.wxc.fuckwechat.vo;

import java.util.Date;

public class ChatItem {
	private String url;
	private String date;
	private String contactName;
	private String chatMsg;

	// 联系人资料是否更新
	private boolean contactUpdate;
	// 聊天信息是否更新
	private boolean update;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getChatMsg() {
		return chatMsg;
	}

	public void setChatMsg(String chatMsg) {
		this.chatMsg = chatMsg;
	}

	public boolean getUpdate() {
		return update;
	}

	public void setUpdate(boolean update) {
		this.update = update;
	}

	public boolean isContactUpdate() {
		return contactUpdate;
	}

	public void setContactUpdate(boolean contactUpdate) {
		this.contactUpdate = contactUpdate;
	}

}
