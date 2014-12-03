package org.wxc.wechat.dao.proxy;

import java.util.List;




import org.wxc.wechat.dao.IUserDAO;
import org.wxc.wechat.dbc.DatabaseConnection;
import org.wxc.wechat.dao.impl.*;
import org.wxc.wechat.vo.UserInfo;

public class UserInfoDAOProxy implements IUserDAO{
	private DatabaseConnection dbc = null ;
	private IUserDAO dao = null ;
	public UserInfoDAOProxy() throws Exception{
		this.dbc = new DatabaseConnection();
		this.dao = new UserInfoDAOImpl(this.dbc.getConnection());
	}
	public boolean doInsert(String id,String password,String name) throws Exception {
		boolean flag = false ;
		try{
			this.dao.doInsert(id,password,name);
		}catch(Exception e){
			throw e;
		}finally{
			this.dbc.close();
		}
		return flag;
	}

	public boolean doDelete(List<UserInfo> infoList) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean doUpdate(UserInfo info) throws Exception {
		boolean flag = false ;
		try{
			this.dao.doUpdate(info);
		}catch(Exception e){
			throw e;
		}finally{
			this.dbc.close();
		}
		return flag;
	}

	public List<UserInfo> findInfoList(String keyWord) throws Exception {
		List<UserInfo> recList = null ;
		try{
			recList = this.dao.findInfoList(keyWord);
		}catch(Exception e){
			throw e;
		}finally{
			this.dbc.close();
		}
		return recList;
	}

}
