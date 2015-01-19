package org.wxc.fuckwechat.fragment;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.wxc.fuckwechat.R;
import org.wxc.fuckwechat.activity.MainActivity;
import org.wxc.fuckwechat.network.BitmapWorkerTask;
import org.wxc.fuckwechat.network.NewMsgTask;
import org.wxc.fuckwechat.utils.SharedPreferenceUtil;
import org.wxc.fuckwechat.utils.Stream2String;
import org.wxc.fuckwechat.views.WXTabItem;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.preference.Preference;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class WXTabFragment extends Fragment implements OnClickListener {

	// 当前Page的ID
	private int pageID = 0;

	private WXTabItem wechat;
	private WXTabItem contacts;
	private WXTabItem found;
	private WXTabItem mine;

	private MainActivity activity;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tab_fragment, null);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		activity = (MainActivity) getActivity();

		wechat = (WXTabItem) activity.findViewById(R.id.wechat);
		contacts = (WXTabItem) activity.findViewById(R.id.contacts);
		found = (WXTabItem) activity.findViewById(R.id.found);
		mine = (WXTabItem) activity.findViewById(R.id.mine);

		initItems();

		contacts.initTv_tabicon("通讯录");
		found.initTv_tabicon("发现");
		mine.initTv_tabicon("我");

		wechat.setOnClickListener(this);
		contacts.setOnClickListener(this);
		found.setOnClickListener(this);
		mine.setOnClickListener(this);

	}

	/**
	 * 初始化Item
	 */
	public void initItems() {
		pageID = activity.getPageID();
		wechat.initIv_tabicon(R.drawable.tab_weixin_normal);
		contacts.initIv_tabicon(R.drawable.tab_address_normal);
		found.initIv_tabicon(R.drawable.tab_find_frd_normal);
		mine.initIv_tabicon(R.drawable.tab_settings_normal);
		wechat.initIv_tabiconPressed(R.drawable.tab_weixin_pressed, 0);
		contacts.initIv_tabiconPressed(R.drawable.tab_address_pressed, 0);
		found.initIv_tabiconPressed(R.drawable.tab_find_frd_pressed, 0);
		mine.initIv_tabiconPressed(R.drawable.tab_settings_pressed, 0);

		wechat.initIv_notify_num(R.drawable.tab_notify_num);
		// 初始化时假定没有未读信息
		wechat.setNewMsgOff();

		NewMsgTask task = new NewMsgTask(wechat);
		task.execute("http://192.168.1.195/WeChatServer/RefreshMsgsServlet?userId="
				+ new SharedPreferenceUtil(getActivity()).readString(
						"USER_LOGIN", "userId"));

		found.initIv_notify(R.drawable.tab_notify);
		switch (pageID) {
		case 0:
			wechat.initAlpha(1);
			break;
		case 1:
			contacts.initAlpha(1);
			break;
		case 2:
			found.initAlpha(1);
			break;
		case 3:
			mine.initAlpha(1);
			break;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.wechat:
			changeToFragment(0);
			initItems();
			break;
		case R.id.contacts:
			changeToFragment(1);
			initItems();
			break;
		case R.id.found:
			changeToFragment(2);
			initItems();
			break;
		case R.id.mine:
			changeToFragment(3);
			initItems();
			break;
		}
	}

	/**
	 * 页面跳转到ID为i的Fragment
	 * 
	 * @param i
	 */
	private void changeToFragment(int to) {
		Animation anim = new AlphaAnimation(0, 1);

		anim.setDuration(500);

		activity.getViewPager().setAnimation(anim);

		activity.getViewPager().setCurrentItem(to, false);

		activity.setPageID(to);
		/*
		 * 此时用scrollTo或scrollBy不行，因为ViewPager默认只有前中后三个View存在，
		 * 直接跳转两个item就无法创建Fragment对象了
		 */

		System.out.println("pageID = " + pageID);
		pageID = to;
	}

	@SuppressLint("NewApi")
	public void setAlpha(int from, int to, float positionOffset) {

		// getItem方法取得指定Item的WXTabItem实例
		WXTabItem fromItem = getItem(from);
		WXTabItem toItem = getItem(to);

		// initAlpha为WXTabItem实例中的ImageView设置透明度
		toItem.initAlpha(positionOffset);
		fromItem.initAlpha(1 - positionOffset);

	}

	public WXTabItem getItem(int i) {
		switch (i) {
		case 0:
			return wechat;
		case 1:
			return contacts;
		case 2:
			return found;
		case 3:
			return mine;
		default:
			return null;
		}
	}

}
