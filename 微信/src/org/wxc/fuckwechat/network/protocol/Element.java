package org.wxc.fuckwechat.network.protocol;

import org.xmlpull.v1.XmlSerializer;

/**
 * 封装服务器回复结果
 * @author Shawn Wang
 *
 */
public abstract class Element {
	// 不会将所有的请求用到的叶子放到Element中
	// Element将作为请求的代表
	// Element所有请求的公共部分
	// 1、 每个请求都需要序列化自己

	/*
	 * 序列化自己
	 */
	public abstract void serializeElement(XmlSerializer serializer);
	
	// 2、 每个请求都有自己的标识
	/*
	 * 自己的标识
	 * @return
	 */
	public abstract String getTransactionType();
}
