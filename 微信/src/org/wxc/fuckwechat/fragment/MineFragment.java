package org.wxc.fuckwechat.fragment;

import org.wxc.fuckwechat.R;
import org.wxc.fuckwechat.activity.ShowMyInfoActivity;
import org.wxc.fuckwechat.utils.SharedPreferenceUtil;
import org.wxc.fuckwechat.views.MyInfoItem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class MineFragment extends Fragment implements OnClickListener{
	TextView my_id;
	MyInfoItem myInfoItem;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.mine_fragment, null);
		my_id = (TextView) view.findViewById(R.id.my_id);
		myInfoItem = (MyInfoItem) view.findViewById(R.id.myInfoItem);
		
		myInfoItem.setOnClickListener(this);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		my_id.setText(new SharedPreferenceUtil(getActivity()).readString(
				"USER_LOGIN", "userId"));
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.myInfoItem:
			showMyInfo();
			break;

		default:
			break;
		}
	}

	private void showMyInfo() {
		Intent it = new Intent(getActivity(),ShowMyInfoActivity.class);
		startActivity(it);
	}

}
