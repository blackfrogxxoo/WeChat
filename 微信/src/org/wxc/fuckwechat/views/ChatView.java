package org.wxc.fuckwechat.views;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.wxc.fuckwechat.R;
import org.wxc.fuckwechat.network.BitmapWorkerTask;
import org.wxc.fuckwechat.vo.ChatItem;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 此为微信聊天界面的item的layout类，对应xml文件为chat_msg.xml
 * 
 * @author wxc
 * 
 */
public class ChatView extends RelativeLayout {
	//
	private int width;
	private int height;

	private Handler canvasHandler;
	private GestureDetector gestureDetector;

	Context context;
	// 包含的子View
	ImageView head_icon;
	TextView contact_name;
	TextView simple_msg;
	TextView msg_time;

	public ChatView(Context context) {
		super(context);
		initView(context);
	}

	public ChatView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	/*
	 * 初始化布局文件
	 */
	public void initView(Context context) {
		View.inflate(context, R.layout.chat_msg, ChatView.this);
		head_icon = (ImageView) findViewById(R.id.head_icon);
		contact_name = (TextView) findViewById(R.id.contact_name);
		simple_msg = (TextView) findViewById(R.id.simple_msg);
		msg_time = (TextView) findViewById(R.id.msg_time);
		head_icon.setImageResource(R.drawable.ic_2);
	}

	/*
	 * 从itemList中提取出head_icon的URL，contact_name,simple_msg及msg_time
	 */
	public void initItems(List<ChatItem> itemList) {
		for (ChatItem item : itemList) {
			String url = item.getUrl();
			String name = item.getContactName();
			String msg = item.getChatMsg();
			String date = item.getDate();

			loadBitmap(url, head_icon);
			contact_name.setText(name);
			simple_msg.setText(msg);
			msg_time.setText(date);
		}

	}

	/*
	 * 从ChatItem实例item中提取出head_icon的URL，contact_name,simple_msg及msg_time
	 */
	public void initItem(ChatItem item) {

		String url = item.getUrl();
		String name = item.getContactName();
		String msg = item.getChatMsg();
		String date = item.getDate();
		// 目前假设加载ChatView时，都是最近才更新过
		item.setUpdate(true);
		boolean update = item.getUpdate();

		if (update) {
			// 加入本地是否有头像存储的判断，如有则从本地存储中加载图片，如没有则从网络加载，并以一定的命名规则保存在本地
			if (getLocalUri() != null) // ToDo 这里实际上还要判断联系人头像是否已经更新
				loadBitmap(getLocalUri(), head_icon);
			else {
				loadBitmap(url, head_icon);
				setLocalUri();
			}
			contact_name.setText(name);
			simple_msg.setText(msg);
			msg_time.setText(date);
			item.setUpdate(false);

		} else {
			// ToDo 从本地存储中加载ListView
		}

	}

	private void setLocalUri() {
		// ToDo 将网络加载的图片保存在本地
	}

	private String getLocalUri() {
		// ToDo 根据联系人姓名读取本地的图片保存地址
		return null;
	}

	public void loadBitmap(String url, ImageView imageView) {
		BitmapWorkerTask task = new BitmapWorkerTask(imageView);
		task.execute(url);
	}

}
