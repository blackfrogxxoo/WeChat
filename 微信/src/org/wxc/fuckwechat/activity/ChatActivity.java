package org.wxc.fuckwechat.activity;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import org.wxc.fuckwechat.R;
import org.wxc.fuckwechat.constant.ServletConstants;
import org.wxc.fuckwechat.utils.NetUtils;
import org.wxc.fuckwechat.utils.SharedPreferenceUtil;
import org.wxc.fuckwechat.utils.TimeUtils;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ChatActivity extends Activity implements OnClickListener {
	protected static final int SUCCESS = 1;

	Button btn_send_chat_msg;
	EditText et_chat_msg;

	LinearLayout ll_chat_list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat_activity_layout);

		cleanMsg();

		btn_send_chat_msg = (Button) findViewById(R.id.btn_send_chat_msg);
		et_chat_msg = (EditText) findViewById(R.id.et_chat_msg);

		ll_chat_list = (LinearLayout) findViewById(R.id.ll_chat_list);

		btn_send_chat_msg.setOnClickListener(this);
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == SUCCESS) { // SUCCESS为int常量，值为1
				// TODO 判断进度，完成时进度条消失
				Toast.makeText(ChatActivity.this, (String) msg.obj, 0).show();
				TextView tv = new TextView(ChatActivity.this);
				tv.setText((String) msg.obj + "  "
						+ TimeUtils.getFormatTime(new Date().getTime()));
				ll_chat_list.addView(tv);
				// TODO 将TextView换成ImageView + 含信息的View
			}
		}

	};

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_send_chat_msg)
			new Thread(new Runnable() {
				@Override
				public void run() {
					String send_msg = et_chat_msg.getText().toString();

					// TODO 将发送的消息以一定的格式显示在聊天列表中，旁边显示进度条
					String toId = null;
					if ("guest".equals(new SharedPreferenceUtil(
							getApplication())
							.readString("USER_LOGIN", "userId")))
						toId = "admin";
					else if ("admin".equals(new SharedPreferenceUtil(
							getApplication())
							.readString("USER_LOGIN", "userId")))
						toId = "guest";

					String result = postMsg(new SharedPreferenceUtil(
							getApplication())
							.readString("USER_LOGIN", "userId"), toId, send_msg); // TODO
																					// 从聊天List或联系人中取出
																					// toId.
					if (!result.equals("")) {
						Message msg = new Message();
						msg.what = SUCCESS; // SUCCESS为int常量，值为1
						msg.obj = result;

						handler.sendMessage(msg);
					}
				}
			}).start();
	}

	public void cleanMsg() {
		new Thread(new Runnable() {
			HttpURLConnection conn = null;

			@Override
			public void run() {
				try {

					URL url = new URL(ServletConstants.cleanMsgUrl);

					conn = (HttpURLConnection) url.openConnection();

					conn.setRequestMethod("GET");
					conn.setConnectTimeout(10000);
					conn.setReadTimeout(5000);

					int responseCode = conn.getResponseCode();
					if (responseCode == 200) {
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (conn != null) {
						conn.disconnect();
					}
				}
			}

		}).start();

	}

	public String postMsg(String fromId, String toId, String msg) {
		HttpURLConnection conn = null;
		try {
			URL url = new URL(ServletConstants.chatUrl);

			conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod("POST");
			conn.setConnectTimeout(10000);
			conn.setReadTimeout(5000);
			conn.setDoOutput(true);

			// post
			String data = "fromId=" + fromId + "&toId=" + toId + "&msg=" + msg;

			OutputStream out = conn.getOutputStream();
			out.write(data.getBytes());
			out.flush();
			out.close();

			int responseCode = conn.getResponseCode();
			if (responseCode == 200) {
				InputStream is = conn.getInputStream();
				String state = NetUtils.getStringFromInputStream(is);
				if (state != "")
					return fromId + " To " + toId + ":\n" + msg;
			} else {
				System.out.println("响应码： " + responseCode);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		return "";
	}
}
