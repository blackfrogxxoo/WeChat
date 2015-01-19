package org.wxc.fuckwechat.engine;

import org.wxc.fuckwechat.bean.User;
import org.wxc.fuckwechat.network.protocol.Message;

/**
 * 用户相关的业务操作的接口
 * @author Shawn Wang
 *
 */
public interface UserEngine {
	/**
	 * 用户登录
	 * @param user
	 * @return
	 */
	Message login(User user);
}
