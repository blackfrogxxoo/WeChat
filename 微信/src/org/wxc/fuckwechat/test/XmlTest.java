package org.wxc.fuckwechat.test;

import org.apache.commons.codec.digest.DigestUtils;
import org.wxc.fuckwechat.bean.User;
import org.wxc.fuckwechat.constant.ConstantValue;
import org.wxc.fuckwechat.engine.UserEngine;
import org.wxc.fuckwechat.network.protocol.Message;
import org.wxc.fuckwechat.network.protocol.element.UserLoginElement;
import org.wxc.fuckwechat.utils.BeanFactory;
import org.wxc.fuckwechat.utils.DES;

import android.test.AndroidTestCase;
import android.util.Log;

public class XmlTest extends AndroidTestCase {
	private static final String TAG = "XmlTest";

	public void createXML() {
		Message message = new Message();
		UserLoginElement element = new UserLoginElement();
		element.getUserid().setTagValue("blackfrogxxoo");
		element.getPassword().setTagValue("wxc147258");
		String xml = message.getXml(element);
		Log.i(TAG, xml);
	}

	public String decodeDES() {
		String desStr = "cgYNf1rUkTnotW53ZfSZs5SawAMeGn2m/SZMtiRW2Bb6gKXBE/QT1ElnburWncsHhiZoprLXnlvjSD4m6nnewFF1ivRzPyaq0f5EUsFZIME7azLAVYgQTw4S8p5XqRVHdFxQBzWp5bA=";
		DES des = new DES();
		System.out.println(des.authcode(desStr, "ENCODE",
				ConstantValue.DES_PASSWORD));

		return des.authcode(desStr, "ENCODE", ConstantValue.DES_PASSWORD);
	}

	public void testMD5() {
		String str = "20150119203441" + "<body>" + decodeDES() + "</body>";
		System.out.println(DigestUtils.md5Hex(str));
	}

	public void testUserLogin() {
		// UserEngineImpl impl=new UserEngineImpl();
		// UserEngineImpl1
		// User user=new User();
		// user.setUsername("13200000000");
		// user.setPassword("0000000");
		// Message login = impl.login(user);
		// Log.i(TAG, login.getBody().getOelement().getErrorcode());

		UserEngine engine = BeanFactory.getImpl(UserEngine.class);

		User user = new User();
		user.setUserId("admin");
		user.setPassword("admin");
		Message login = engine.login(user);
		System.out.println(login.getBody().getOElement().getErrorcode());
	}
}
