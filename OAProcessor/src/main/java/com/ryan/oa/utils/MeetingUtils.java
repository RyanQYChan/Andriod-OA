package com.ryan.oa.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ryan.oa.beans.Meeting;
import com.ryan.oa.beans.Todo;

public class MeetingUtils {

	private static Connection conn = JdbcUtils.DBConnection();

	public static ArrayList<Meeting> retrieveMeetingList(String loginId) {
		ResultSet rs = null;
		String sql = null;
		ArrayList<Meeting> al = new ArrayList<Meeting>();
		sql = "select * from meeting where find_in_set(?,meetingMem) order by meetingId desc";
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, loginId);
			rs = JdbcUtils.SQLexecute(pst);
			// System.out.println("rs"+rs.next());
			if (!rs.next()) {
				// SimpleDateFormat sdf = new
				// SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Meeting meeting = new Meeting();
				al.add(meeting);
			}
			rs.beforeFirst();
			while (rs.next()) {
				Meeting mt = new Meeting();
				mt.setMeetingId(rs.getInt(1));
				mt.setMeetingSub(rs.getString(2));
				mt.setMeetingDesc(rs.getString(3));
				mt.setMeetingMem(rs.getString(4));
				mt.setMeetingTime(rs.getTimestamp(5));
				mt.setEndTime(rs.getTimestamp(6));
				mt.setMeetingRoom(rs.getInt(7));
				mt.setOwnerId(rs.getInt(8));
				al.add(mt);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out
					.println("MeetingUtils-retrieveMeetingList()- retrieveMeetingList failed");

		}

		return al;
	}

	public static Meeting retrieveMeetingDetail(String loginId) {
		ResultSet rs = null;
		String sql = null;
		Meeting mt = null;
		sql = "select * from Meeting where meetingId = ?";
		try {
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, Integer.parseInt(loginId));
			rs = JdbcUtils.SQLexecute(pst);
			// System.out.println("rs"+rs.next());
			while (rs.next()) {
				mt.setMeetingId(rs.getInt(1));
				mt.setMeetingSub(rs.getString(2));
				mt.setMeetingDesc(rs.getString(3));
				mt.setMeetingMem(rs.getString(4));
				mt.setMeetingTime(rs.getTimestamp(5));
				mt.setEndTime(rs.getTimestamp(6));
				mt.setMeetingRoom(rs.getInt(7));
				mt.setOwnerId(rs.getInt(8));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out
					.println("MeetingUtils-retrieveMeetingDetail()- retrieveMeetingDetail failed");
		}

		return mt;
	}

	public static boolean addMeeting(Meeting mt) {
		boolean bl = false;
		String sql = null;
		sql = "insert into meeting values(?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setNull(1, Types.INTEGER);
			pst.setString(2, mt.getMeetingSub());
			pst.setString(3, mt.getMeetingDesc());
			pst.setString(4, mt.getMeetingMem());
			pst.setTimestamp(5, mt.getMeetingTime());
			pst.setTimestamp(6, mt.getEndTime());
			pst.setInt(7, mt.getMeetingRoom());
			pst.setInt(8, mt.getOwnerId());
			int count = 0;
			count = JdbcUtils.SQLupdate(pst);
			if (count != 0) {
				bl = true;
			}
			// System.out.println("rs"+rs.next());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("MeetingUtils-addMeeting()- addMeeting failed");
		}

		return bl;
	}

	public static boolean deleteMeeting(String meetingId) {
		boolean bl = false;
		String sql = null;
		sql = "delete from meeting where meetingId = ?";
		try {
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, Integer.parseInt(meetingId));
			int count = 0;
			count = JdbcUtils.SQLupdate(pst);
			if (count != 0) {
				bl = true;
			}
			// System.out.println("rs"+rs.next());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out
					.println("MeetingUtils-deleteMeeting()- deleteMeeting failed");
		}

		return bl;
	}

	public static boolean updateMeeting(Meeting mt) {
		boolean bl = false;
		String sql = null;
		sql = "update meeting set meetingSub =? , meetingDesc = ?, meetingMem = ? , meetingTime = ?, endTime= ? ,meetingRoom = ? where meetingId = ?";
		try {
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, mt.getMeetingSub());
			pst.setString(2, mt.getMeetingDesc());
			pst.setString(3, mt.getMeetingMem());
			pst.setTimestamp(4, mt.getMeetingTime());
			pst.setTimestamp(5, mt.getEndTime());
			pst.setInt(6, mt.getMeetingRoom());
			pst.setInt(7, mt.getMeetingId());
			int count = 0;
			count = JdbcUtils.SQLupdate(pst);
			if (count != 0) {
				bl = true;
			}
			// System.out.println("rs"+rs.next());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out
					.println("MeetingUtils-updateMeeting()- updateMeeting failed");
		}
		return bl;
	}
	/*
	 * public static boolean isOwnerOfMeeting(String meetingId, String loginId){
	 * boolean bl = false; ResultSet rs = null; String sql = null; sql =
	 * "select * from meeting where meetingId =? and ownerId =? ";
	 * 
	 * try { PreparedStatement pst = conn.prepareStatement(sql); pst.setInt(1,
	 * Integer.parseInt(meetingId)); pst.setInt(2, Integer.parseInt(loginId));
	 * rs = JdbcUtils.SQLexecute(pst); int count =0; if(rs.next()){ count =
	 * rs.getInt(1); } if(count!=0){ bl = true; } //
	 * System.out.println("rs"+rs.next()); } catch (SQLException e) { // TODO
	 * Auto-generated catch block System.out
	 * .println("MeetingUtils-updateMeeting()- updateMeeting failed"); } return
	 * bl; }
	 */
}
