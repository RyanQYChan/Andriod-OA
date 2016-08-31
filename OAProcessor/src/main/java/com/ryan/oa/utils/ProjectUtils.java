package com.ryan.oa.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.ryan.oa.beans.Meeting;
import com.ryan.oa.beans.Project;

public class ProjectUtils {
	private static Connection conn = JdbcUtils.DBConnection();

	public static ArrayList<Project> retrieveProjectList(String loginId) {
		ResultSet rs = null;
		String sql = null;
		ArrayList<Project> al = new ArrayList<Project>();
		sql = "select * from project where find_in_set(?,projectMem) order by projectId desc";
		try {
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, loginId);
			rs = JdbcUtils.SQLexecute(pst);
			// System.out.println("rs"+rs.next());
			if(!rs.next()){
				//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Project pj = new Project();
				al.add(pj);
			}
			rs.beforeFirst();
			while (rs.next()) {
				Project pj = new Project();
				pj.setProjectId(rs.getInt(1));
				pj.setProjectSub(rs.getString(2));
				pj.setProjectDesc(rs.getString(3));
				pj.setProjectDiv(initProjectDiv(rs.getString(4),rs.getString(5)));
				pj.setOpenDate(rs.getDate(6));
				pj.setTargetDate(rs.getDate(7));
				pj.setProjectStatus(rs.getInt(8));
				pj.setOwnerId(rs.getInt(9));
				al.add(pj);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out
					.println("ProjectUtis-retrieveProjectList()- retrieveProjectList failed");
		}

		return al;
	}

	public static Project retrieveProjectDetail(String projectId) {
		ResultSet rs = null;
		String sql = null;
		Project pj = null;
		sql = "select * from project where projectId = ?";
		try {
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, Integer.parseInt(projectId));
			rs = JdbcUtils.SQLexecute(pst);
			// System.out.println("rs"+rs.next());
			while (rs.next()) {
			    pj = new Project();
				pj.setProjectId(rs.getInt(1));
				pj.setProjectSub(rs.getString(2));
				pj.setProjectDesc(rs.getString(3));
				pj.setProjectDiv(initProjectDiv(rs.getString(4), rs.getString(5)));
				pj.setOpenDate(rs.getDate(6));
				pj.setTargetDate(rs.getDate(7));
				pj.setProjectStatus(rs.getInt(8));
				pj.setOwnerId(rs.getInt(9));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out
					.println("ProjectUtis-retrieveProjectDetail()- retrieveProjectDetail failed");
		}
		return pj;

	}

	public static boolean addProject(Project pj) {
		String sql = null;
		boolean bl = false;
		sql = "insert into project values(?,?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setNull(1,Types.INTEGER);
			pst.setString(2, pj.getProjectSub());
			pst.setString(3, pj.getProjectDesc());
			String key = formatProjectMem(pj.getProjectDiv().keySet().toString());
			String value = formatProjectDiv(pj.getProjectDiv().values().toString());
			pst.setString(4, key);
			pst.setString(5, value);
			pst.setDate(6, pj.getOpenDate());
			pst.setDate(7, pj.getTargetDate());
			pst.setInt(8, pj.getProjectStatus());
			pst.setInt(9, pj.getOwnerId());
			int count =0;
			count = JdbcUtils.SQLupdate(pst);
			// System.out.println("rs"+rs.next());
			if(count != 0){
				bl = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out
					.println("ProjectUtis-addProject()- addProject failed");
		}
		return bl;
	}
	public static boolean deleteProject(String projectId) {
		String sql = null;
		boolean bl = false;
		sql = "delete from project where projectId = ?";
		try {
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, Integer.parseInt(projectId));
			int count =0;
			count = JdbcUtils.SQLupdate(pst);
			// System.out.println("rs"+rs.next());
			if(count != 0){
				bl = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out
					.println("ProjectUtis-addProject()- addProject failed");
		}
		return bl;
	}
	public static boolean updateProject(Project pj) {
		String sql = null;
		boolean bl = false;
		sql = "update project set projectSub = ?,projectDesc = ?,projectMem = ?,projectDiv = ?, targetDate =?,projectStatus = ? where projectId =? ";
		try {
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, pj.getProjectSub());
			pst.setString(2, pj.getProjectDesc());
			String key = formatProjectMem(pj.getProjectDiv().keySet().toString());
			String value = formatProjectDiv(pj.getProjectDiv().values().toString());
			pst.setString(3, key);
			pst.setString(4, value);
			pst.setDate(5, pj.getTargetDate());
			pst.setInt(6, pj.getProjectStatus());
			pst.setInt(7, pj.getProjectId());
			int count =0;
			count = JdbcUtils.SQLupdate(pst);
			// System.out.println("rs"+rs.next());
			if(count != 0){
				bl = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out
					.println("ProjectUtis-updateProject()- updateProject failed");
		}
		return bl;
	}
	
	//从数据库获取出staff id以及其分工组成map
	public static Map initProjectDiv(String key, String value){
		Map<String,String> div = new HashMap<String, String>();
		key = formatProjectMem(key);
		value = formatProjectDiv(value);
		String[] sKey = key.split(",");
		String[] sValue = value.split(",");
		for(int i=0;i<sKey.length;i++){
			div.put(sKey[i], sValue[i]);
		}
		return div;
	}
	
	public static String formatProjectMem(String key){
		String skey = key.replace("[",""); 
		skey = skey.replace("]", "");
		skey = skey.replace(" ", "");
	    return skey;	
	}
	public static String formatProjectDiv(String value){
		String sValue = value.replace("[", "");
		sValue = sValue.replace("]", "");
		sValue = sValue.replace(" ", "");
		return sValue;
	}
}