package org.wxc.fuckwechat.network.protocol;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.wxc.fuckwechat.constant.ConstantValue;
import org.wxc.fuckwechat.utils.DES;
import org.xmlpull.v1.XmlSerializer;

import android.util.Xml;

/**
 * 消息体结点封装
 * 
 * @author Shawn Wang
 * 
 */
public class Body {
	private List<Element> elements = new ArrayList<Element>();

	/****************** 处理服务器回复 ******************/
	private String serviceBodyInsideDESInfo;
	private OElement oElement = new OElement();

	public OElement getOElement() {
		return oElement;
	}

	public String getServiceBodyInsideDESInfo() {
		return serviceBodyInsideDESInfo;
	}

	public void setServiceBodyInsideDESInfo(String serviceBodyInsideDESInfo) {
		this.serviceBodyInsideDESInfo = serviceBodyInsideDESInfo;
	}

	/****************** 处理服务器回复 ******************/

	/**
	 * 序列化请求
	 */
	public void serializeBody(XmlSerializer serializer) {
		try {
			serializer.startTag(null, "body");
			serializer.startTag(null, "elements");

			for (Element item : getElements()) {
				item.serializeElement(serializer);
			}
			serializer.endTag(null, "elements");
			serializer.endTag(null, "body");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获致到完整的body
	 * 
	 * @return
	 */
	public String getWholeBody() {
		StringWriter writer = new StringWriter();

		XmlSerializer temp = Xml.newSerializer();
		try {
			temp.setOutput(writer);
			this.serializeBody(temp);
			// Output will be flushed
			temp.flush();
			return writer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取body里面的DES加密数据
	 * 
	 * @return
	 */
	public String getBodyInsideDESInfo() {
		// 加密数据
		String wholeBody = getWholeBody();
		String oriDesInfo = StringUtils.substringBetween(wholeBody, "<body>",
				"</body>");

		// 加密
		// 加密调试——2天
		// 1、加密算法实现不同
		// 2、加密的原始数据不同
		DES des = new DES();
		return des.authcode(oriDesInfo, "DECODE", ConstantValue.DES_PASSWORD);

	}

	public List<Element> getElements() {
		return elements;
	}

}
