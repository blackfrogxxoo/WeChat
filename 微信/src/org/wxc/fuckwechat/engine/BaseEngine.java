package org.wxc.fuckwechat.engine;

import java.io.InputStream;

import org.apache.commons.codec.digest.DigestUtils;
import org.wxc.fuckwechat.constant.ConstantValue;
import org.wxc.fuckwechat.network.HttpClientUtil;
import org.wxc.fuckwechat.network.protocol.Message;
import org.wxc.fuckwechat.utils.DES;
import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;

/**
 * 所有engine实现类的父类
 * 
 * @author Shawn Wang
 * 
 */
public abstract class BaseEngine {
	public Message getResult(String xml) {
		HttpClientUtil util = new HttpClientUtil();
		InputStream is = util.sendXml(ConstantValue.LOTTERY_URI, xml);
		// 判断输入流非空
		if (is != null) {
			Message result = new Message();

			// timestamp + digest + body
			XmlPullParser parser = Xml.newPullParser();
			try {
				parser.setInput(is, ConstantValue.ENCONDING);

				int eventType = parser.getEventType();
				String name;

				while (eventType != XmlPullParser.END_DOCUMENT) {
					switch (eventType) {
					case XmlPullParser.START_TAG:
						name = parser.getName();
						if ("timestamp".equals(name)) {
							result.getHeader().getTimestamp()
									.setTagValue(parser.nextText());
						}
						if ("digest".equals(name)) {
							result.getHeader().getDigest()
									.setTagValue(parser.nextText());
						}
						if ("body".equals(name)) {
							result.getBody().setServiceBodyInsideDESInfo(
									parser.nextText());
						}
						break;
					}
					eventType = parser.next();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			// 原始数据还原：时间戳（解析） + 密码 （常量） + body明文（解析 + 解密DES）
			// body明文（解析+解密DES）
			DES des = new DES();
			String body = "<body>"
					+ des.authcode(result.getBody()
							.getServiceBodyInsideDESInfo(), "ENCODE",
							ConstantValue.DES_PASSWORD) + "</body>";
			String oriInfo = result.getHeader().getTimestamp().getTagValue()
					+ body;

			// 利用工具生成手机端的MD5
			String md5Hex = DigestUtils.md5Hex(oriInfo);
			// 将手机端与服务器的进行比对
			if (md5Hex.equalsIgnoreCase(result.getHeader().getDigest()
					.getTagValue())) {
				// 比对通过
				return result;
			}
		}

		return null;
	}
}
