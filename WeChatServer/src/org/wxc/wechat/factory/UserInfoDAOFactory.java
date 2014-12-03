package org.wxc.wechat.factory;

import org.wxc.wechat.dao.IUserDAO;
import org.wxc.wechat.dao.proxy.UserInfoDAOProxy;

public class UserInfoDAOFactory {
	public static IUserDAO getIUserDAOInstance() throws Exception {
		return new UserInfoDAOProxy();
	}
}
