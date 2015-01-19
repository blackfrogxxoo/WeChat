package org.wxc.fuckwechat.activity;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.wxc.fuckwechat.R;
import org.wxc.fuckwechat.utils.SharedPreferenceUtil;
import org.wxc.fuckwechat.utils.Stream2String;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class WelcomeActivity extends Activity {
	EditText user;
	EditText pass;
	Button register;
	Button login;
	RegisterActivity activity;

	protected static final String TAG = "WelcomeActivity";

	protected static final int SHOW_UPDATE_DIALOG = 0;
	protected static final int ENTER_HOME = 1;
	protected static final int URL_ERROR = 2;
	protected static final int NETWORK_ERROR = 3;
	protected static final int JSON_ERROR = 4;
	protected static final int NOT_LOGIN = 5;

	SharedPreferences sp;

	List<Map> newMsgs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome_layout);
		// showTime();
		sp = getSharedPreferences("USER_LOGIN", RegisterActivity.MODE_PRIVATE);
		isLogin = sp.getBoolean("isLogin", false);

		Message mes = Message.obtain();
		if (isLogin)
			mes.what = ENTER_HOME;
		else
			mes.what = NOT_LOGIN;

		handler.sendMessage(mes);

	}

	/*
	 * 获取时间，以检验和聊天ServerSocket服务器是否相连
	 */
	private void showTime() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					HttpURLConnection conn = (HttpURLConnection) new URL(
							"http://192.168.1.195/WeChatServer/TimeServlet?userId="
									+ new SharedPreferenceUtil(getApplication())
											.readString("USER_LOGIN", "userId"))
							.openConnection();
					if (conn.getResponseCode() == 200) {
						System.out.println("连接TimeServlet");
						connectChatServer();

					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	/*
	 * 连接到ServerSocket服务器
	 */
	private void connectChatServer() {
		try {

			Socket socket = new Socket("192.168.1.195", 9980);

			String time = Stream2String.stream2String(socket.getInputStream());

			System.out.println(time);

			/*
			 * ObjectInputStream ois = new ObjectInputStream(
			 * socket.getInputStream());
			 * 
			 * 
			 * newMsgs = (List<Map>) ois.readObject(); Iterator iter =
			 * newMsgs.iterator(); HashMap<String, String> map =
			 * (HashMap<String, String>) iter.next(); String sendId =
			 * map.get("sendId"); String receiveId = map.get("receiveId");
			 * String msg = map.get("msg"); String time = map.get("timestamp");
			 * System.out.println("map的条目数为：" + map.size() + "sendId: " + sendId
			 * + ", receiveId: " + receiveId + ", msg: " + msg + ", time: " +
			 * time);
			 */
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	boolean waitFlag = false;
	boolean isLogin = false;

	@Override
	protected void onStart() {
		isLogin = getSharedPreferences("USER_LOGIN",
				RegisterActivity.MODE_PRIVATE).getBoolean("isLogin", false);
		super.onStart();
	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case SHOW_UPDATE_DIALOG:// 显示升级的对话框
				Log.i(TAG, "显示升级的对话框");
				showUpdateDialog();
				break;
			case ENTER_HOME:// 进入主页面
				handler.postDelayed(new Runnable() {
					@Override
					public void run() {
						// 进入主页面
						enterHome();
					}
				}, 2000);
				break;

			case URL_ERROR:// URL错误
				enterHome();
				Toast.makeText(getApplicationContext(), "URL错误", 0).show();

				break;

			case NETWORK_ERROR:// 网络异常
				enterHome();
				Toast.makeText(WelcomeActivity.this, "网络异常", 0).show();
				break;

			case JSON_ERROR:// JSON解析出错
				enterHome();
				Toast.makeText(WelcomeActivity.this, "JSON解析出错", 0).show();
				break;

			case NOT_LOGIN:
				handler.postDelayed(new Runnable() {
					@Override
					public void run() {
						// 进入注册
						enterRegister();
					}
				}, 2000);
				Toast.makeText(WelcomeActivity.this, "请注册或登录", 0).show();
			default:
				break;
			}
		}

	};

	protected void showUpdateDialog() {
		// TODO Auto-generated method stub

	}

	protected void enterRegister() {
		startActivity(new Intent(WelcomeActivity.this, RegisterActivity.class));
		finish();

	}

	protected void enterHome() {
		startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
		finish();

	}

	protected void checkUpdate() {

	}
}
