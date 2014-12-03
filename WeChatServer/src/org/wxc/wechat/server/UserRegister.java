package org.wxc.wechat.server;

import org.wxc.wechat.factory.UserInfoDAOFactory;

public class UserRegister {
	public UserRegister(String id,String password,String name) throws Exception{
		UserInfoDAOFactory.getIUserDAOInstance().doInsert(id, password, name);
	}
}
