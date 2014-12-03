package org.wxc.wechat.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.wxc.wechat.dao.IUserDAO;
import org.wxc.wechat.vo.UserInfo;
import org.wxc.wechat.vo.WeChatUserDB;

public class UserInfoDAOImpl implements IUserDAO {
	Connection conn = null;
	PreparedStatement pstmt = null;

	public UserInfoDAOImpl(Connection conn) {
		this.conn = conn;
	}

	public boolean doInsert(String id, String password, String name)
			throws Exception {
		boolean flag = false;

		String sql = "INSERT INTO TAB_USER_INFO (USER_ID,USER_PASSWORD,USER_NAME) VALUES ('"+id+"','"+password+"','"+name+"')";
		try {

			this.pstmt = this.conn.prepareStatement(sql);
			/*pstmt.setString(1, id);
			pstmt.setString(2, password);
			pstmt.setString(3, name);*/

			this.pstmt.executeUpdate(sql);

			flag = true;
		} catch (Exception e) {
			throw e;
		} finally {
			this.pstmt.close();
		}
		return flag;
	}

	public boolean doDelete(List<UserInfo> infoList) throws Exception {
		return false;
	}

	public boolean doUpdate(UserInfo info) throws Exception {
		boolean flag = false;
		String sql = "UPDATE TAB_USER_INFO SET " + "USER_NAME='"
				+ info.getName() + "',USER_SEX='" + info.getSex() + "',USER_FROM='"
				+ info.getFrom() + "',USER_SIGN='" + info.getSign()
				+ "' WHERE USER_ID=" + info.getId();
		try {
			this.pstmt = this.conn.prepareStatement(sql);

			pstmt.executeUpdate(sql);

			flag = true;
		} catch (Exception e) {
			throw e;
		} finally {
			this.pstmt.close();
		}
		return flag;
	}

	public List<UserInfo> findInfoList(String keyWord) throws Exception {
		List<UserInfo> infoList = null;
		UserInfo info = null;
		String sql = "SELECT USER_ID,USER_NAME,USER_SEX,USER_FROM,USER_SIGN FROM TAB_USER_INFO WHERE "
				+ keyWord;
		try {
			this.pstmt = this.conn.prepareStatement(sql);

			ResultSet rs = this.pstmt.executeQuery();
			infoList = new ArrayList<UserInfo>();
			while (rs.next()) {
				info = new UserInfo();
				info.setId(rs.getString(1));
				info.setName(rs.getString(2));
				info.setSex(rs.getString(3));
				info.setFrom(rs.getString(4));
				info.setSign(rs.getString(5));
				infoList.add(info);
				System.out.println(rs.toString());
			}
			;
		} catch (Exception e) {
			throw e;
		} finally {
			this.pstmt.close();
		}

		return infoList;
	}

}
