package org.wxc.fuckwechat.activity;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.wxc.fuckwechat.R;
import org.wxc.fuckwechat.R.id;
import org.wxc.fuckwechat.R.layout;
import org.wxc.fuckwechat.R.menu;
import org.wxc.fuckwechat.fragment.ContactsFragment;
import org.wxc.fuckwechat.fragment.FoundFragment;
import org.wxc.fuckwechat.fragment.MineFragment;
import org.wxc.fuckwechat.fragment.WXTabFragment;
import org.wxc.fuckwechat.fragment.WeChatFragment;
import org.wxc.fuckwechat.utils.SharedPreferenceUtil;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ActionMode;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.Window;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	private ViewPager viewPager;
	private ScreenSlidePagerAdapter mPagerAdapter;

	private int scrollX = 0;
	private int pageID = 0;

	public int width = 0;
	public int height = 0;

	private FragmentManager fm;
	private android.support.v4.app.FragmentTransaction ft;

	private static WXTabFragment tabFragment;

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Bundle data = msg.getData();
			String val = data.getString("value");
			Log.i("mylog", "请求结果为-->" + val);
			// TODO
			// UI界面的更新等相关操作
		}
	};

	public ViewPager getViewPager() {
		return this.viewPager;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		viewPager = (ViewPager) findViewById(R.id.viewPager);

		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		width = metric.widthPixels; // 屏幕宽度（像素）
		height = metric.heightPixels; // 屏幕高度（像素）
		tabFragment = new WXTabFragment();

		fm = this.getSupportFragmentManager();
		ft = fm.beginTransaction();
		ft.add(R.id.tabContainer, tabFragment);
		ft.commit();

		mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
		viewPager.setAdapter(mPagerAdapter);

		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			int position = MainActivity.this.getPageID();

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {

				/**
				 * 根据page位置和偏移量设置指定tabItem的颜色渐变
				 */
				tabFragment = (WXTabFragment) fm
						.findFragmentById(R.id.tabContainer);
				/*
				 * position其实是滑动倾向的两个页面的第一个页面ID 比如：
				 * 从item1向左滑时，position=0，向右滑时，position=1
				 */
				if (position < 3)
					tabFragment
							.setAlpha(position, position + 1, positionOffset);
				// ToDo 为何第一次从0向1滑动时，1没有渐变效果？

				// System.out.println("positionOffset:" + positionOffset);

			}

			@Override
			public void onPageSelected(int position) {
				MainActivity.this.setPageID(position);
				tabFragment = (WXTabFragment) fm
						.findFragmentById(R.id.tabContainer);
				tabFragment.initItems();
			}

		});

	}

	/*
	 * 手势识别
	 */
	private GestureDetector detector;
	private float mStartX;
	private float distance;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);

		detector.onTouchEvent(event);

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mStartX = event.getX();
		case MotionEvent.ACTION_MOVE:
			distance = mStartX - event.getX();
			// System.out.println("distance:" + distance);
		}

		return true;
	}

	public class ScreenSlidePagerAdapter extends FragmentPagerAdapter {

		public ScreenSlidePagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case 0:
				setPageID(0);
				return new WeChatFragment();
			case 1:
				setPageID(1);
				return new ContactsFragment();
			case 2:
				setPageID(2);
				return new FoundFragment();
			case 3:
				setPageID(3);
				return new MineFragment();
			default:
				try {
					throw new Exception("没有找到任何Fragment");
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
		}

		@Override
		public int getCount() {
			return 4;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
			if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
				try {
					Method m = menu.getClass().getDeclaredMethod(
							"setOptionalIconsVisible", Boolean.TYPE);
					m.setAccessible(true);
					m.invoke(menu, true);
				} catch (Exception e) {
				}
			}
		}
		return super.onMenuOpened(featureId, menu);
	}

	private void setOverflowShowingAlways() {
		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			menuKeyField.setAccessible(true);
			menuKeyField.setBoolean(config, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setScrollX(int offset) {
		this.scrollX = offset;
		// System.out.println("scrollX = " + this.scrollX);
	}

	public int getScrollX() {
		// TODO Auto-generated method stub
		return this.scrollX;
	}

	public int getPageID() {
		return this.pageID;
	}

	public void setPageID(int pageID) {
		this.pageID = pageID;
	}

	/*
	 * private long exitTime = 0;
	 * 
	 * public boolean onKeyDown(int keyCode, KeyEvent event) { if(keyCode ==
	 * KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
	 * if((System.currentTimeMillis()-exitTime) > 2000){
	 * Toast.makeText(getApplicationContext(), "再按一次后退键退出程序",
	 * Toast.LENGTH_SHORT).show(); exitTime = System.currentTimeMillis(); } else
	 * { //退出代码 //finish(); System.exit(0); } return true; } return
	 * super.onKeyDown(keyCode, event); }
	 */

}
