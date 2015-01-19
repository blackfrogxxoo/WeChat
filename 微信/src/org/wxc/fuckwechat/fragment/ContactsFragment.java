package org.wxc.fuckwechat.fragment;

import org.wxc.fuckwechat.R;
import org.wxc.fuckwechat.activity.MainActivity;
import org.wxc.fuckwechat.network.BitmapWorkerTask;
import org.wxc.fuckwechat.views.WXContactItem;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class ContactsFragment extends Fragment {

	WXContactItem newFriend;
	WXContactItem chats;
	WXContactItem tags;
	WXContactItem publics;

	MainActivity activity;

	Button button;
	ImageView iv_loli;
	Bitmap loli;
	byte data[];

	String str = "http://192.168.1.195/test/ajaxdemo/46.jpg";
	String str1 = "http://img0.bdstatic.com/img/image/shouye/gxbxsj1-9432399697.jpg";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.contacts_fragment, null);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		activity = (MainActivity) getActivity();
		newFriend = (WXContactItem) activity.findViewById(R.id.ci_newFriend);
		chats = (WXContactItem) activity.findViewById(R.id.ci_chats);
		tags = (WXContactItem) activity.findViewById(R.id.ci_tags);
		publics = (WXContactItem) activity.findViewById(R.id.ci_publics);

		iv_loli = (ImageView) activity.findViewById(R.id.iv_loli);
		iv_loli.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				loadBitmap(str,iv_loli);
			}
		});

		initView();

	}

	public void initView() {
		newFriend.setFriendName("新朋友");
		chats.setFriendName("群聊");
		tags.setFriendName("标签");
		publics.setFriendName("公众号");

		newFriend.setFriendIcon(R.drawable.ic_1);
		chats.setFriendIcon(R.drawable.ic_2);
		tags.setFriendIcon(R.drawable.ic_3);
		publics.setFriendIcon(R.drawable.ic_4);
	}

	public void loadBitmap(String url, ImageView imageView) {
		BitmapWorkerTask task = new BitmapWorkerTask(imageView);
		task.execute(url);
	}

}
