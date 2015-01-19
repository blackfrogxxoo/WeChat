package org.wxc.fuckwechat.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.wxc.fuckwechat.R;
import org.wxc.fuckwechat.activity.ChatActivity;
import org.wxc.fuckwechat.activity.MainActivity;
import org.wxc.fuckwechat.views.ChatView;
import org.wxc.fuckwechat.vo.ChatItem;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

public class WeChatFragment extends Fragment {

	ListView chat_list;
	MainActivity activity;

	private List<ChatItem> itemList;
	String str1 = "http://img0.bdstatic.com/img/image/shouye/gxbxsj1-9432399697.jpg";
	String str = "http://192.168.23.1/test/ajaxdemo/46.jpg";
	String str2 = "http://192.168.23.1/NewTest/lufei.jpg";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// 此处如用container 则抛出“已经有父View”的异常
		View view = inflater.inflate(R.layout.wechat_fragment, null);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		activity = (MainActivity) getActivity();
		setItemList();
		chat_list = (ListView) activity.findViewById(R.id.chat_list);
		initItems();
		initView();
		chat_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent it = new Intent(activity, ChatActivity.class);
				startActivity(it);
			}
		});
	}

	// 制造ItemList假数据
	private void setItemList() {
		this.itemList = new ArrayList<ChatItem>();
		int i = 0;
		while (i < 10) {
			ChatItem ci = new ChatItem();
			ci.setUrl(str2);
			ci.setDate("1月7日");
			ci.setChatMsg("这是第" + (++i) + "次给你说话了");
			ci.setContactName("TestName" + i);
			itemList.add(ci);
		}
	}

	/*
	 * 初始化View
	 */
	private void initView() {
		// 在ChatFragment中添加ListView
		chat_list.setAdapter(new ChatListAdapter(activity));
	}

	public void initItems() {
		// ToDo 从本地获取当前保存的聊天信息items
	}

	/**
	 * 这个Adapter要实现： 动态添加ChatView实例到ListView中 ！！！这里还需要解决一个问题，进一步优化listView
	 * ！！！使其在加载了网络图片后，即使不可见，也不要销毁，除非内存不足
	 * 
	 * @author ASUS
	 * 
	 */
	public class ChatListAdapter extends BaseAdapter {

		// 得到一个LayoutInflater对象来导入布局
		private LayoutInflater mInflater;

		public ChatListAdapter(Context context) {
			this.mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return itemList.size();
		}

		@Override
		public Object getItem(int position) {

			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ChatView cv;
			/*
			 * 把用过的对象存储到缓存中 要用到convertView
			 */
			if (convertView != null && true) {// 判断缓存对象是否为null, 不为null时已经缓存对象
				// TODO 此时还需指定使用哪一个convertView, 对应于position

				cv = (ChatView) convertView;
			} else {
				cv = new ChatView(activity);
				cv.initItem(itemList.get(position));
			}
			return cv;
		}

	}

}
