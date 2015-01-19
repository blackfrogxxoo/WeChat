package org.wxc.fuckwechat.database;

public class ContactsInfoDatabase {
	/*
	 * 联系人信息：数据库名
	 */
	public static final String CONTACTS_INFO＿DATABASE = "CONTACTS_INFO＿DATABASE";
	/*
	 * 联系人信息： 数据表名
	 */
	public static final String CONTACTS_INFO_TABLE = "CONTACTS_INFO_TABLE";

	/*
	 * Column名
	 */
	// ID 用于ContentProvider Integer primary key auto increment
	public static final String CONTACT_ID = "_id";
	// 昵称 Text
	public static final String CONTACT_NAME = "CONTACT_NAME";
	// 微信号 Text
	public static final String CONTACT_NUMBER = "CONTACT_NUMBER";
	// 性别 男/女/保密 Integer
	public static final String CONTACT_SEX = "CONTACT_SEX";
	// 地区 Integer
	public static final String CONTACT_FROM = "CONTACT_FROM";
	// 个性签名 Text
	public static final String CONTACT_SIGN = "CONTACT_SIGH";
	// 附近的人、摇一摇等 Integer
	public static final String CONTACT_MEET = "CONTACT_MEET";

}
