package com.ryan.oa.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ryan.oa.beans.Todo;
import com.ryan.oa.beans.User;

public class UserUtils {
	private static Connection conn = JdbcUtils.DBConnection();

	public static boolean userLogin(String sloginId, String userPwd) {
		ResultSet rs = null;
		String sql = null;
		boolean result = false;
		sql = "select * from user where loginId = ? and userPwd = ?";
		try {
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, Integer.parseInt(sloginId));
			pst.setString(2, userPwd);
			rs = JdbcUtils.SQLexecute(pst);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("login failed");
		}
		try {
			if (rs.next()) {
				result = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/*
	 * public static boolean userLogout(){ boolean bl = false; sql =
	 * "delect * from userSession where loginId =?"; return bl; }
	 */
	public static User getUserInfo(String sloginId) {
		ResultSet rs = null;
		String sql = null;
		int loginId = 0;
		String userName = null;
		String userPwd = null;
		String userRole = null;
		int managerId = 0;
		sql = "select * from user where loginId = ?";
		try {
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, Integer.parseInt(sloginId));
			rs = JdbcUtils.SQLexecute(pst);
			// System.out.println("rs"+rs.next());
			while (rs.next()) {
				loginId = rs.getInt(1);
				userName = rs.getString(2);
				userPwd = rs.getString(3);
				userRole = rs.getString(4);
				managerId = rs.getInt(5);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("get the user info failed");
		}
		User user = new User();
		user.setLoginId(loginId);
		user.setUserName(userName);
		user.setUserPwd(userPwd);
		user.setUserRole(userRole);
		user.setManagerId(managerId);
		System.out.println("userLogin" + managerId);
		return user;
	}

	public static ArrayList<User> getUserList(String sloginId) {
		ResultSet rs = null;
		String sql = null;
		int loginId = 0;
		ArrayList<User> al = new ArrayList<User>();
		try{
			loginId = Integer.parseInt(sloginId);
			sql = "select * from user where loginId like '%"+loginId+"%';";
		}
		catch(NumberFormatException e){
			sql = "select * from user where userName like '%"+sloginId+"%';";
		}
		try {
			
			/*PreparedStatement pst = conn.prepareStatement(sql);
			loginId = Integer.parseInt(sloginId);
			pst.setInt(1, loginId);*/
			rs = conn.createStatement().executeQuery(sql);
			//rs = JdbcUtils.SQLexecute(pst);
			if (!rs.next()) {
				User user = new User();
				al.add(user);

			}
			rs.beforeFirst();
			while (rs.next()) {
				User user = new User();
				user.setLoginId(rs.getInt(1));
				user.setUserName(rs.getString(2));
				user.setUserPwd(rs.getString(3));
				user.setUserRole(rs.getString(4));
				user.setManagerId(rs.getInt(5));
				al.add(user);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return al;
	}

	public static boolean changePwd(User user) {
		String sql = null;
		sql = "update user set userPwd = ? where loginId = ?";
		int count = 0;
		boolean bl = false;
		try {
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, user.getUserPwd());
			pst.setInt(2, user.getLoginId());
			count = JdbcUtils.SQLupdate(pst);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(count!=0){
			bl = true;
		}
		return bl;
	}
}
