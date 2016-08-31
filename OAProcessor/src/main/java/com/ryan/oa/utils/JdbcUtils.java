package com.ryan.oa.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class JdbcUtils {

	public static Connection DBConnection() {
		Properties prop = new Properties();
		InputStream in = JdbcUtils.class.getClassLoader().getResourceAsStream("db.properties");
		try {
			prop.load(in);
		} catch (IOException e1) {
			System.out.println("can not find db properties file");
		}
		try {
			Class.forName(prop.getProperty("driverName").toString());
		} catch (ClassNotFoundException e) {
			System.out.println("can not find mysql driver");
		}
		String dbUrl = prop.getProperty("dbUrl").toString();
		String userName = prop.getProperty("userName").toString();
		String uerPwd = prop.getProperty("userPwd").toString();
		Connection conn = null;
		try {
			//DriverManager.setLoginTimeout(1000000000);
			conn = DriverManager.getConnection(dbUrl, userName, uerPwd);
		} catch (SQLException e) {
			System.out.println("can not connect to DB " + e.toString() );
		}
		return conn;
	}
	
	public static ResultSet SQLexecute(PreparedStatement pst){
		
		ResultSet rs = null;
		try {
			rs = pst.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	public static int SQLupdate(PreparedStatement pst){
		
		int rs = 0;
		try {
			rs = pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
}
