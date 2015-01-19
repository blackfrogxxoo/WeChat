package org.wxc.fuckwechat.engine.impl;

import java.io.InputStream;
import java.io.StringReader;

import org.apache.commons.codec.digest.DigestUtils;
import org.wxc.fuckwechat.bean.User;
import org.wxc.fuckwechat.constant.ConstantValue;
import org.wxc.fuckwechat.engine.BaseEngine;
import org.wxc.fuckwechat.engine.UserEngine;
import org.wxc.fuckwechat.network.HttpClientUtil;
import org.wxc.fuckwechat.network.protocol.Message;
import org.wxc.fuckwechat.network.protocol.element.UserLoginElement;
import org.wxc.fuckwechat.utils.DES;
import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;

/**
 * 用户登录
 * 
 * @author Shawn Wang
 * 
 */
public class UserEngineImpl extends BaseEngine implements UserEngine {
	/**
	 * 用户登录
	 * 
	 * @param user
	 */
	public Message login(User user) {
		// 第一步：获取到登录用的xml
		// 创建登录用Element
		UserLoginElement element = new UserLoginElement();
		// 设置用户数据
		element.getUserid().setTagValue(user.getUserId());
		element.getPassword().setTagValue(user.getPassword());
		// Message.getXml(element)
		Message message = new Message();
		String xml = message.getXml(element);

		// 如果第三步比对通过result，否则返回空
		Message result = getResult(xml);

		if (result != null) {

			// 第四步：请求结果的数据处理
			// body部分的第二次解析，解析的是明文内容

			XmlPullParser parser = Xml.newPullParser();
			try {

				DES des = new DES();
				String body = "<body>"
						+ des.authcode(result.getBody()
								.getServiceBodyInsideDESInfo(), "ENCODE",
								ConstantValue.DES_PASSWORD) + "</body>";

				parser.setInput(new StringReader(body));

				int eventType = parser.getEventType();
				String name;

				while (eventType != XmlPullParser.END_DOCUMENT) {
					switch (eventType) {
					case XmlPullParser.START_TAG:
						name = parser.getName();
						if ("errorcode".equals(name)) {
							result.getBody().getOElement()
									.setErrorcode(parser.nextText());
						}
						if ("errormsg".equals(name)) {
							result.getBody().getOElement()
									.setErrormsg(parser.nextText());

						}
						break;
					}
					eventType = parser.next();
				}

				return result;

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return null;

	}

	/**
	 * 用户登录
	 * 
	 * @param user
	 */
	public Message login1(User user) {
		// 第一步：获取到登录用的xml
		// 创建登录用Element
		UserLoginElement element = new UserLoginElement();
		// 设置用户数据
		element.getUserid().setTagValue(user.getUserId());
		element.getPassword().setTagValue(user.getPassword());
		// Message.getXml(element)
		Message message = new Message();
		String xml = message.getXml(element);

		// 第二步(代码不变)：发送xml到服务器端，等待回复
		// HttpClientUtil.sendXml
		// 在这行代码前，没有判断网络类型？
		HttpClientUtil util = new HttpClientUtil();
		InputStream is = util.sendXml(ConstantValue.LOTTERY_URI, xml);
		// 判断输入流非空
		if (is != null) {
			Message result = new Message();

			// 第三步(代码不变)：数据的校验（MD5数据校验）
			// timestamp+digest+body
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

			// 原始数据还原：时间戳（解析）+密码（常量）+body明文（解析+解密DES）
			// body明文（解析+解密DES）
			DES des = new DES();
			String body = "<body>"
					+ des.authcode(result.getBody()
							.getServiceBodyInsideDESInfo(), "ENCODE",
							ConstantValue.DES_PASSWORD) + "</body>";

			String orgInfo = result.getHeader().getTimestamp().getTagValue()
					+ ConstantValue.AGENTER_PASSWORD + body;

			// 利用工具生成手机端的MD5
			String md5Hex = DigestUtils.md5Hex(orgInfo);
			// 将手机端与服务器的进行比对
			if (md5Hex.equals(result.getHeader().getDigest().getTagValue())) {
				// 第四步：请求结果的数据处理
				// body部分的第二次解析，解析的是明文内容

				parser = Xml.newPullParser();
				try {
					parser.setInput(new StringReader(body));

					int eventType = parser.getEventType();
					String name;

					while (eventType != XmlPullParser.END_DOCUMENT) {
						switch (eventType) {
						case XmlPullParser.START_TAG:
							name = parser.getName();
							if ("errorcode".equals(name)) {
								result.getBody().getOElement()
										.setErrorcode(parser.nextText());
							}
							if ("errormsg".equals(name)) {
								result.getBody().getOElement()
										.setErrormsg(parser.nextText());

							}
							break;
						}
						eventType = parser.next();
					}

					return result;

				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}
		return null;
	}

}
