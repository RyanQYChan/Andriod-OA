package com.ryan.oa.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ryan.oa.beans.Meeting;
import com.ryan.oa.beans.Room;

public class CommonUtils {
	private static Connection conn = JdbcUtils.DBConnection();
	public static ArrayList<Room> retrieveRoomList(Meeting mt){
		String sql = "SELECT * FROM room WHERE roomId " +
				"NOT IN ( SELECT meetingRoom FROM meeting " +
				"WHERE meetingTime <  ? AND endTime >  ?) or roomId IN (SELECT meetingRoom FROM meeting " +
				"WHERE meetingTime <= ? AND endTime >=  ? and meetingId = ?); ";
		ResultSet rs = null;
		PreparedStatement pst = null;
		ArrayList<Room> rmList = new ArrayList<Room>();
		try{
			pst = conn.prepareStatement(sql);
			pst.setTimestamp(1,mt.getEndTime());
			pst.setTimestamp(2, mt.getMeetingTime());
			pst.setTimestamp(3, mt.getMeetingTime());
			pst.setTimestamp(4, mt.getEndTime());
			pst.setInt(5, mt.getMeetingId());
			rs = JdbcUtils.SQLexecute(pst);
			if(!rs.next()){
				Room rm = new Room();
				rmList.add(rm);
			}
			rs.beforeFirst();
			while(rs.next()){
				Room rm = new Room();
				rm.setRoomId(rs.getInt(1));
				rm.setRoomDesc(rs.getString(2));
				rmList.add(rm);
			}
		}catch(SQLException e){
			
		}
		System.out.println(sql +" "+mt.getMeetingId()+" "+mt.getMeetingTime()+" " +mt.getEndTime());
		return rmList;
	}
	//获取最新的插入数据库的记录的id
	public static int getLatestInsertId(){
		int id = 0;
		String sql = "select max(chatId) from chat";
		ResultSet rs = null;
		PreparedStatement pst = null;
		try{
			pst = conn.prepareStatement(sql);
			rs = JdbcUtils.SQLexecute(pst);
			while(rs.next()){
				id = rs.getInt(1);
			}
		}catch(SQLException e){
			id = 0;
		}
		return id;
	} 
}
