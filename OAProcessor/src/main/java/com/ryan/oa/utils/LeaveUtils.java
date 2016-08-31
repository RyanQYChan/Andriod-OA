package com.ryan.oa.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import com.ryan.oa.beans.Leave;

public class LeaveUtils {

	private static Connection conn = JdbcUtils.DBConnection();

	public static ArrayList<Leave> retrieveMyLeaveList(String loginId) {
		ResultSet rs = null;
		String sql = null;
		ArrayList<Leave> al = new ArrayList<Leave>();
		sql = "select * from leaves where ownerId =? order by leaveId desc";
		try {
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, Integer.parseInt(loginId));
			rs = JdbcUtils.SQLexecute(pst);
			// System.out.println("rs"+rs.next());
			if(!rs.next()){
				//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Leave leave = new Leave();
				al.add(leave);
			}
			rs.beforeFirst();
			while (rs.next()) {
				Leave lv = new Leave();
				lv.setLeaveId(rs.getInt(1));
				lv.setLeaveDesc(rs.getString(2));
				lv.setFromTime(rs.getDate(3));
				lv.setToTime(rs.getDate(4));
				lv.setOwnerId(rs.getInt(5));
				lv.setApproverId(rs.getInt(6));
				lv.setStatus(rs.getInt(7));
				al.add(lv);
			}
		} catch (SQLException e) {
			System.out
					.println("LeaveUtils-retrieveMyLeaveList()- retrieveLeaveList failed");
		}
		return al;
	}

	public static ArrayList<Leave> retrieveApprLeaveList(String loginId) {
		ResultSet rs = null;
		String sql = null;
		ArrayList<Leave> al = new ArrayList<Leave>();
		sql = "select * from leaves where approverId =? order by leaveId desc";
		try {
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, Integer.parseInt(loginId));
			rs = JdbcUtils.SQLexecute(pst);
			// System.out.println("rs"+rs.next());
			if(!rs.next()){
				//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Leave leave = new Leave();
				al.add(leave);
			}
			rs.beforeFirst();
			while (rs.next()) {
				Leave lv = new Leave();
				lv.setLeaveId(rs.getInt(1));
				lv.setLeaveDesc(rs.getString(2));
				lv.setFromTime(rs.getDate(3));
				lv.setToTime(rs.getDate(4));
				lv.setOwnerId(rs.getInt(5));
				lv.setApproverId(rs.getInt(6));
				lv.setStatus(rs.getInt(7));
				al.add(lv);
			}
		} catch (Exception e) {
			System.out
					.println("LeaveUtils-retrieveApprLeaveList()- retrieveApprLeaveList failed");
		}
		return al;
	}
	public static Leave retrieveLeaveDetail(String loginId) {
		ResultSet rs = null;
		String sql = null;
		Leave lv = null;
		sql = "select * from leaves where leaveId = ?";
		try {
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, Integer.parseInt(loginId));
			rs = JdbcUtils.SQLexecute(pst);
			// System.out.println("rs"+rs.next());
			while (rs.next()) {
				lv = new Leave();
				lv.setLeaveId(rs.getInt(1));
				lv.setLeaveDesc(rs.getString(2));
				lv.setFromTime(rs.getDate(3));
				lv.setToTime(rs.getDate(4));
				lv.setOwnerId(rs.getInt(5));
				lv.setApproverId(rs.getInt(6));
				lv.setStatus(rs.getInt(7));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out
					.println("LeaveUtils-retrieveLeaveDetail()- retrieveLeaveDetail failed");
		}

		return lv;
	}
	public static boolean addLeave(Leave lv) {
		boolean bl = false;
		String sql = null;
		sql = "insert into leaves values(?,?,?,?,?,?,?)";
		try {
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setNull(1, Types.INTEGER);
			pst.setString(2, lv.getLeaveDesc());
			pst.setDate(3, lv.getFromTime());
			pst.setDate(4, lv.getToTime());
			pst.setInt(5, lv.getOwnerId());
			pst.setInt(6,lv.getApproverId());
			pst.setInt(7, lv.getStatus());
			int count =0;
			count = JdbcUtils.SQLupdate(pst);
			if(count != 0){
				bl = true;
			}
			// System.out.println("rs"+rs.next());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out
					.println("LeaveUtils-addLeave()- addLeave failed");
		}

		return bl;
	}
	public static boolean deleteLeave(String leaveId) {
		boolean bl = false;
		String sql = null;
		sql = "delete from leaves where leaveId = ?";
		try {
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, Integer.parseInt(leaveId));
			int count =0;
			count = JdbcUtils.SQLupdate(pst);
			if(count != 0){
				bl = true;
			}
			// System.out.println("rs"+rs.next());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out
					.println("LeaveUtils-deleteLeave()- deleteLeave failed");
		}

		return bl;
	}
	public static boolean updateLeave(Leave lv) {
		boolean bl = false;
		String sql = null;
		sql = "update leaves set leaveDesc =? , fromTime = ?, toTime = ?  where leaveId = ?";
		try {
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, lv.getLeaveDesc());
			pst.setDate(2, lv.getFromTime());
			pst.setDate(3, lv.getToTime());
			pst.setInt(4, lv.getLeaveId());
			int count =0;
			count = JdbcUtils.SQLupdate(pst);
			if(count != 0){
				bl = true;
			}
			// System.out.println("rs"+rs.next());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out
					.println("LeaveUtils-updateLeave()- updateLeave failed");
		}
		return bl;
	}
	
	public static boolean approveLeave(String leaveId,int status) {
		boolean bl = false;
		String sql = null;
		sql = "update leaves set status = ? where leaveId = ?";
		try {
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1,status);
			pst.setInt(2, Integer.parseInt(leaveId));
			int count =0;
			count = JdbcUtils.SQLupdate(pst);
			if(count != 0){
				bl = true;
			}
			// System.out.println("rs"+rs.next());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out
					.println("LeaveUtils-approveLeave()- approveLeave failed");
		}
		return bl;
	}
/*	public static boolean isOwnerOfLeave(String leaveId, String loginId){
		boolean bl = false;
		ResultSet rs = null;
		String sql = null;
		sql = "select * from leave where leaveId =? and ownerId =? ";
		
		try {
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, Integer.parseInt(leaveId));
			pst.setInt(2, Integer.parseInt(loginId));
			rs = JdbcUtils.SQLexecute(pst);
			int count =0;
			if(rs.next()){
				count = rs.getInt(1);
			}
			if(count!=0){
				bl = true;
			}
			// System.out.println("rs"+rs.next());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out
					.println("LeaveUtils-updateLeave()- updateLeave failed");
		}
		return bl;
	}*/
}
