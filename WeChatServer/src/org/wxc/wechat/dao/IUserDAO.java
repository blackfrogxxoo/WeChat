package org.wxc.wechat.dao;

import java.util.List;

import org.wxc.wechat.vo.*;

public interface IUserDAO {
	/**
	 * 验证成功，准备注册
	 */
	public boolean doInsert(String id,String password,String name)throws Exception;
	/**
	 * 删除操作
	 */
	public boolean doDelete(List<UserInfo> infoList)throws Exception;
	/**
	 * 更新个人资料
	 */
	public boolean doUpdate(UserInfo info)throws Exception;
	/**
	 * 查询操作
	 * 需实现根据任意条件查询出满足其条件的一组UserInfo并返回
	 */
	public List<UserInfo> findInfoList(String keyWord)throws Exception;
}
