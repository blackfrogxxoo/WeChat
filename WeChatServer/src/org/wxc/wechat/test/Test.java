package org.wxc.wechat.test;

import org.wxc.wechat.factory.UserInfoDAOFactory;
import org.wxc.wechat.vo.UserInfo;

public class Test {
	public static final String id = "460929850" ;
	public static final String password = "wxc147258" ;
	public static final String name = "鄙人不善奔跑" ;
	public static void main(String args[]) throws Exception{
		UserInfo ubi = new UserInfo() ;
		ubi.setId("460929850");
		ubi.setName(name);
		ubi.setSex("男");
		ubi.setFrom("四川 大竹");
		ubi.setSign("这是我的第一个账号！");
		//UserInfoDAOFactory.getIUserDAOInstance().doInsert(id, password, name) ;
		UserInfoDAOFactory.getIUserDAOInstance().doUpdate(ubi);
		//UserInfoDAOFactory.getIUserDAOInstance().findInfoList("USER_ID='460929850'");
	}
}
