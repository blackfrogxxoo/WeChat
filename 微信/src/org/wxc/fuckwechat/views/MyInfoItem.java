package org.wxc.fuckwechat.views;

import org.wxc.fuckwechat.R;
import org.wxc.fuckwechat.network.BitmapWorkerTask;
import org.wxc.fuckwechat.utils.SharedPreferenceUtil;
import org.wxc.fuckwechat.vo.ChatItem;
import org.wxc.fuckwechat.vo.MineInfo;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyInfoItem extends RelativeLayout {

	ImageView iv_my_info_icon;
	TextView tv_my_info_name;
	TextView tv_my_info_id;

	MineInfo mi;

	public MyInfoItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
		mi = new MineInfo();
		mi.setMyId(new SharedPreferenceUtil(context).readString("USER_LOGIN",
				"userId"));
		mi.setMyName("鄙人不善奔跑");
		mi.setIconRemoteUrl("http://img0.bdstatic.com/img/image/shouye/gxbxsj1-9432399697.jpg");
		initItem(mi);
	}

	private void initView(Context context) {
		inflate(context, R.layout.my_info_layout, MyInfoItem.this);
		iv_my_info_icon = (ImageView) findViewById(R.id.iv_my_info_icon);
		tv_my_info_name = (TextView) findViewById(R.id.tv_my_info_name);
		tv_my_info_id = (TextView) findViewById(R.id.tv_my_info_id);
	}

	/*
	 * 从ChatItem实例item中提取出head_icon的URL，contact_name,simple_msg及msg_time
	 */
	public void initItem(MineInfo item) {

		String url = item.getIconRemoteUrl();
		String name = item.getMyName();
		String id = item.getMyId();
		System.out.println(url + "," + name + "," + id);

		// 加入本地是否有头像存储的判断，如有则从本地存储中加载图片，如没有则从网络加载，并以一定的命名规则保存在本地
		if (getLocalUri() != null) // ToDo 这里实际上还要判断联系人头像是否已经更新
			loadBitmap(getLocalUri(), iv_my_info_icon);
		else {
			if (url != null)
				loadBitmap(url, iv_my_info_icon);
			else
				iv_my_info_icon.setImageResource(R.drawable.ic_launcher);
			setLocalUri();
		}
		if (name != null)
			tv_my_info_name.setText(name);
		if (id != null)
			tv_my_info_id.setText("微信号：" + id);

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

	/**
	 * 不光是点击事件
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			this.setBackgroundColor(Color.LTGRAY);
			break;
		case MotionEvent.ACTION_UP:
			this.setBackgroundColor(Color.WHITE);
			break;
		}

		return true;

	}

}
