package org.wxc.fuckwechat.network;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.wxc.fuckwechat.R;
import org.wxc.fuckwechat.utils.Dom4jUtils;
import org.wxc.fuckwechat.utils.Stream2String;
import org.wxc.fuckwechat.views.WXTabItem;

import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class NewMsgTask extends AsyncTask<String, Void, List> {
	WeakReference<WXTabItem> itemReference;

	public NewMsgTask(WXTabItem item) {
		itemReference = new WeakReference<WXTabItem>(item);
	}

	@Override
	protected void onPostExecute(List result) {
		/*
		 * 如果有未读消息，则设置红点透明度为1
		 */
		if (result != null)
			if (itemReference != null && result.size() > 0) {
				final WXTabItem wechat = itemReference.get();

				if (wechat != null) {
					wechat.setNewMsgOn();
				}
			}
		/*
		 * 如果没有未读消息，则设置红点透明度为0
		 */
		if (result != null)
			if (itemReference != null && result.size() == 0) {
				final WXTabItem wechat = itemReference.get();

				if (wechat != null) {
					wechat.setNewMsgOff();
				}
			}
	}

	@Override
	protected List doInBackground(String... params) {
		if (params.length == 0)
			return null;
		String url = (String) params[0];

		InputStream is;
		try {
			is = new URL(url).openStream();
			List result = Dom4jUtils.xml2MsgList(is);
			System.out.println(result);
			return result;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
