package org.wxc.fuckwechat.constant;

import org.wxc.fuckwechat.utils.SharedPreferenceUtil;

import android.content.Context;

/**
 * Servlet地址管理
 * 
 * @author Shawn Wang
 * 
 */
public class ServletConstants {
	public static String refreshMsgUrl;
	public static String cleanMsgUrl;
	public static String chatUrl;
	public static final String BASEURL = "http://192.168.1.195/WeChatServer/";
	public static final String REGISTERURL = BASEURL + "RegisterServlet";
	public static final String LOGINURL = BASEURL + "LoginInServlet";

	public static void init(Context context) {
		String userId = new SharedPreferenceUtil(context).readString(
				"USER_LOGIN", "userId");

		refreshMsgUrl = BASEURL + "/RefreshMsgsServlet?userId=" + userId;
		cleanMsgUrl = BASEURL + "/CleanUnreadMsgsServlet?userId=" + userId;
		chatUrl = BASEURL + "/ChatServlet";
	}

}
