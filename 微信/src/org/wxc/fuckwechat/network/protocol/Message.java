package org.wxc.fuckwechat.network.protocol;

import java.io.StringWriter;

import org.wxc.fuckwechat.constant.ConstantValue;
import org.xmlpull.v1.XmlSerializer;

import android.util.Xml;

/**
 * 协议封装
 * 
 * @author Shawn Wang
 * 
 */
public class Message {
	private Header header = new Header();
	private Body body = new Body();

	public Header getHeader() {
		return header;
	}

	public Body getBody() {
		return body;
	}

	/*
	 * 序列化协议
	 */
	public void serializeMessage(XmlSerializer serializer) {
		try{
			// <message version="1.0">
			serializer.startTag(null, "message");
			// Must follow a call to startTag() immediately
			serializer.attribute(null, "version", "1.0");
			
			header.serializeHeader(serializer, body.getWholeBody()); //获取完整的body
			serializer.startTag(null, "body");
			serializer.text(body.getBodyInsideDESInfo());
			serializer.endTag(null, "body");
			
			serializer.endTag(null, "message");
		} catch (Exception e ){
			e.printStackTrace();
		}
	}

	/**
	 * 获取请求的xml文件
	 * 
	 * @return
	 */
	public String getXml(Element element) {
		if (element == null) {
			throw new IllegalArgumentException("element is null");
		}
		// 请求标示需要设置，请求内容需要设置
		header.getTransactiontype().setTagValue(element.getTransactionType());
		body.getElements().add(element);

		// 序列化
		XmlSerializer serializer = Xml.newSerializer();
		StringWriter writer = new StringWriter();
		// This method can only be called just after setOutput
		try {
			serializer.setOutput(writer);
			serializer.startDocument(ConstantValue.ENCONDING, null);
			this.serializeMessage(serializer);
			serializer.endDocument();

			return writer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
