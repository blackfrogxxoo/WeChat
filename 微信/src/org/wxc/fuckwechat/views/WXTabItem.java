package org.wxc.fuckwechat.views;

import org.wxc.fuckwechat.R;
import org.wxc.fuckwechat.R.id;
import org.wxc.fuckwechat.R.layout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class WXTabItem extends RelativeLayout {

	TextView tv_tabicon;
	TextView tv_tabicon_pressed;
	ImageView iv_tabicon;
	ImageView iv_notify_num;
	ImageView iv_notify;
	ImageView iv_tabicon_pressed;

	public WXTabItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public WXTabItem(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initView(context);
	}

	public WXTabItem(Context context) {
		super(context);
		initView(context);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	public void initView(Context context) {
		inflate(context, R.layout.tabicon_layout, WXTabItem.this);
		tv_tabicon = (TextView) findViewById(R.id.tv_tabicon);
		tv_tabicon_pressed = (TextView) findViewById(R.id.tv_tabicon_pressed);
		iv_tabicon = (ImageView) findViewById(R.id.iv_tabicon);
		iv_tabicon_pressed = (ImageView) findViewById(R.id.iv_tabicon_pressed);
		iv_notify_num = (ImageView) findViewById(R.id.iv_notify_num);
		iv_notify = (ImageView) findViewById(R.id.iv_notify);
	}

	public void initTv_tabicon(String text) {
		this.tv_tabicon.setText(text);
		this.tv_tabicon_pressed.setText(text);
	}

	public void initIv_tabicon(int resId) {
		this.iv_tabicon.setImageResource(resId);
	}

	public void initIv_tabiconPressed(int resId, float alpha) {
		this.initIv_tabiconPressed(resId);
		this.initAlpha(alpha);
	}

	public void initIv_tabiconPressed(int resId) {
		this.iv_tabicon_pressed.setImageResource(resId);

	}

	@SuppressLint("NewApi")
	public void initAlpha(float alpha) {
		this.iv_tabicon_pressed.setAlpha(alpha);
		this.tv_tabicon_pressed.setAlpha(alpha);
		this.tv_tabicon.setAlpha(1 - alpha);
	}

	public void initIv_notify_num(int resId) {
		this.iv_notify_num.setImageResource(resId);
	}

	public void initIv_notify(int resId) {
		this.iv_notify.setImageResource(resId);
	}

	public void setNewMsgOn() {
		this.iv_notify_num.setAlpha(1f);
	}

	public void setNewMsgOff() {
		this.iv_notify_num.setAlpha(0f);
	}

}
