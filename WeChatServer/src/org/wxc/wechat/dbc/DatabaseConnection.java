package org.wxc.wechat.dbc;

import java.sql.Connection;
import java.sql.DriverManager;

import org.wxc.wechat.vo.WeChatUserDB;

public class DatabaseConnection {
	private static final String DBDRIVER = "org.gjt.mm.mysql.Driver";
	private static final String DBURL = "jdbc:mysql://localhost:3306/"+WeChatUserDB.DB_NAME;
	private static final String DBUSER = "root" ;
	private static final String DBPASS = "wxc147258";
	Connection conn = null ;
	public DatabaseConnection()throws Exception{
		try{
			Class.forName(DBDRIVER);
			this.conn = DriverManager.getConnection(DBURL,DBUSER,DBPASS);
		}catch(Exception e){
			throw e ;
		}
	}
	public Connection getConnection(){
		return this.conn;
	}
	public void close() throws Exception{
		if(this.conn!=null){
			try{
				this.conn.close();
			}catch(Exception e){
				throw e ;
			}
		}
	}
}
