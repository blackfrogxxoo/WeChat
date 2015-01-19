package org.wxc.fuckwechat.network.protocol;

import java.io.StringWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.wxc.fuckwechat.constant.ConstantValue;
import org.wxc.fuckwechat.utils.DES;
import org.xmlpull.v1.XmlSerializer;

import android.util.Xml;

/**
 * 头结点封装
 * 
 * @author Shawn Wang
 * 
 */
public class Header {

	// <source>ivr</source>
	private Leaf source = new Leaf("source", ConstantValue.SOURCE);
	// <compress>DES</compress>
	private Leaf compress = new Leaf("compress", ConstantValue.COMPRESS);

	// <messageid>20131013101533000001</messageid>
	private Leaf messageid = new Leaf("messageid");
	// <timestamp>20131013101533</timestamp>
	private Leaf timestamp = new Leaf("timestamp");
	// 改一下：时间戳+完整Body就行了，没有代理密码
	private Leaf digest = new Leaf("digest");
	//
	// <transactiontype>2001</transactiontype>
	private Leaf transactiontype = new Leaf("transactiontype");

	/**
	 * 序列化请求
	 */
	public void serializeHeader(XmlSerializer serializer, String body) {
		// 将timestamp、messageid、digest设置数据
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String time = format.format(new Date());
		timestamp.setTagValue(time);
		// messageid: 时间戳+六位随机数
		Random random = new Random();
		int num = random.nextInt(999999) + 1;
		DecimalFormat decimalFormat = new DecimalFormat("000000");
		messageid.setTagValue(time + decimalFormat.format(num));

		// digest:时间戳+用户密码+完整body(明文)
		String oriInfo = time + body;
		String md5Hex = DigestUtils.md5Hex(oriInfo);
		digest.setTagValue(md5Hex);

		try {
			serializer.startTag(null, "header");
			source.serializeLeaf(serializer);
			compress.serializeLeaf(serializer);

			messageid.serializeLeaf(serializer);
			timestamp.serializeLeaf(serializer);
			digest.serializeLeaf(serializer);

			transactiontype.serializeLeaf(serializer);

			serializer.endTag(null, "header");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Leaf getTransactiontype() {
		return transactiontype;
	}

	/****************** 处理服务器回复 ******************/

	public Leaf getTimestamp() {
		return timestamp;
	}

	public Leaf getDigest() {
		return digest;
	}

	/****************** 处理服务器回复 ******************/
}
