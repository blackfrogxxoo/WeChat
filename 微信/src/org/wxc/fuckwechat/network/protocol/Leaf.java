package org.wxc.fuckwechat.network.protocol;

import org.xmlpull.v1.XmlSerializer;

/**
 * 简单叶子
 * @author Shawn Wang
 *
 */
public class Leaf {
	// <userid>admin</userid>
	// 处理的思路
	// 1、 包含的内容
	// 2、 序列化xml
	private String tagName;
	private String tagValue;
	
	// 每个叶子需要指定标签名称
	public Leaf(String tagName) {
		super();
		this.tagName = tagName;
	}
	
	// 处理常量
	public Leaf(String tagName, String tagValue){
		super();
		this.tagName = tagName;
		this.setTagValue(tagValue);
	}

	public String getTagValue() {
		return tagValue;
	}

	public void setTagValue(String tagValue) {
		this.tagValue = tagValue;
	}
	
	/*
	 * 序列化叶子
	 */
	public void serializeLeaf(XmlSerializer serializer) {
		try {
			serializer.startTag(null, tagName);
			if (tagValue == null) {
				tagValue = "";
			}
			serializer.text(tagValue);
			serializer.endTag(null, tagName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
