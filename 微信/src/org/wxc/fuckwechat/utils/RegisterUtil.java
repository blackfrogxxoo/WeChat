package org.wxc.fuckwechat.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.impl.io.HttpResponseWriter;
import org.wxc.fuckwechat.activity.InitMyInfoActivity;
import org.wxc.fuckwechat.activity.MainActivity;
import org.wxc.fuckwechat.activity.RegisterActivity;
import org.wxc.fuckwechat.activity.WelcomeActivity;
import org.wxc.fuckwechat.constant.ServletConstants;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * 
 * @author Shawn Wang
 * 
 */
public class RegisterUtil extends AsyncTask<String, Void, String> {
	private Activity activity;

	public RegisterUtil(Activity activity) {
		this.activity = activity;
	}

	private String userId;

	@Override
	protected String doInBackground(String... params) {
		InputStream is;
		userId = params[0];
		try {
			String urlStr = ServletConstants.REGISTERURL + "?userId="
					+ params[0] + "&userPass=" + params[1];
			is = new URL(urlStr).openStream();
			String str = Stream2String.stream2String(is);

			if (str != null) {
				System.out.println("返回的结果是： " + str);

				return str;
			} else {
				System.out.println("没有得到服务器响应");
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(String str) {
		if (str.equals("true")) {
			Intent it = new Intent(activity, InitMyInfoActivity.class);
			SharedPreferenceUtil spu = new SharedPreferenceUtil(activity);
			// 初始化PreferenceContants
			spu.write("USER_LOGIN", "isLogin", true);
			System.out.println("userId:" + userId);
			spu.write("USER_LOGIN", "userId", userId);
			

			// 初始化ServletConstants
			ServletConstants.init(activity);

			activity.startActivity(it);
		} else if (str.equals("false")) {
			Toast.makeText(activity, "用户名或密码格式不对", Toast.LENGTH_SHORT).show();
			// activity.clearEditText();
		} else {
			Toast.makeText(activity, "用户名已经存在", Toast.LENGTH_SHORT).show();
			// activity.clearEditText();

		}

	}
}
