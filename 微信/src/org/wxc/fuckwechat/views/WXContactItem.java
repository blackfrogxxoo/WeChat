package org.wxc.fuckwechat.views;

import org.wxc.fuckwechat.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WXContactItem extends LinearLayout {

	ImageView friend_icon;
	TextView friend_name;

	@SuppressLint("NewApi") 
	public WXContactItem(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initView(context);
	}

	public WXContactItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public WXContactItem(Context context) {
		super(context);
		initView(context);
	}

	private void initView(Context context) {
		inflate(context, R.layout.contacts_item, this);
		friend_icon = (ImageView) findViewById(R.id.friend_icon);
		friend_name = (TextView) findViewById(R.id.friend_name);
	}

	public void setFriendIcon(int resId) {
		friend_icon.setImageResource(resId);
	}

	public void setFriendName(String name) {
		friend_name.setText(name);
	}
}
