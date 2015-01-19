package org.wxc.fuckwechat.network;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.wxc.fuckwechat.vo.ChatItem;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {
	private final WeakReference<ImageView> imageViewReference;
	private final ChatItem chatItem;
	private String url;
	private byte[] data;

	public BitmapWorkerTask(ImageView imageView) {
		// The WeakReference to the ImageView ensures that the AsyncTask
		// does not prevent the ImageView and anything it references from
		// being garbage collected.
		imageViewReference = new WeakReference<ImageView>(imageView);
		chatItem = null;
	}

	public BitmapWorkerTask(ChatItem chatItem) {
		this.chatItem = chatItem;
		imageViewReference = null;
	}

	/*
	 * public byte[] getUrlData(String str) throws Exception {
	 * ByteArrayOutputStream bos = null; try {
	 * 
	 * URL url = new URL(str);
	 * 
	 * URLConnection connection; connection = url.openConnection();
	 * HttpURLConnection httpConnection = (HttpURLConnection) connection;
	 * 
	 * InputStream in = httpConnection.getInputStream(); byte[] data = new
	 * byte[1024]; bos = new ByteArrayOutputStream(); int len = 0; while ((len =
	 * in.read(data)) != -1) bos.write(data, 0, len); return bos.toByteArray();
	 * } catch (Exception e) { e.printStackTrace(); } finally { if (bos != null)
	 * bos.close(); } return null; }
	 */
	// Decode image in background.
	@Override
	protected Bitmap doInBackground(String... params) {
		if (params.length == 0)
			return null;
		url = (String) params[0];

		InputStream is;
		try {
			is = new URL(url).openStream();
			return BitmapFactory.decodeStream(is);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	// Once complete, see if ImageView is still around and set bitmap
	@Override
	protected void onPostExecute(Bitmap bitmap) {
		if (imageViewReference != null && bitmap != null) {
			final ImageView imageView = imageViewReference.get();
			if (imageView != null) {
				imageView.setImageBitmap(bitmap);
			}
		}

	}

}