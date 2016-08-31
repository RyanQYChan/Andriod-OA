package com.ryan.oa.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;

import com.ryan.oa.beans.Todo;

public class TodoUtils {
	private static Connection conn = JdbcUtils.DBConnection();

	public static ArrayList<Todo> retrieveTodoList(String loginId) {
		ResultSet rs = null;
		String sql = null;
		ArrayList<Todo> al = new ArrayList<Todo>();
		sql = "select * from todo where ownerId =? order by targetDate desc";
		try {
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, Integer.parseInt(loginId));
			rs = JdbcUtils.SQLexecute(pst);
			// System.out.println("rs"+rs.next());
			if(!rs.next()){
				Todo todo = new Todo(99999999, "null",
						"null",new java.sql.Date(1000,10,10),99999);
				al.add(todo);
			}
			rs.beforeFirst();
			while (rs.next()) {
				Todo todo = new Todo(rs.getInt(1), rs.getString(2),
						rs.getString(3), rs.getDate(4), rs.getInt(5));
				al.add(todo);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out
					.println("TodoUtis-retrieveTodoList()- retrieveTodoList failed");
		}

		return al;
	}

	public static Todo retrieveTodoDetail(String todoId) {
		ResultSet rs = null;
		String sql = null;
		Todo todo = null;
		sql = "select * from todo where todoId = ?";
		try {
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, Integer.parseInt(todoId));
			rs = JdbcUtils.SQLexecute(pst);
			// System.out.println("rs"+rs.next());
			while (rs.next()) {
				todo = new Todo(rs.getInt(1), rs.getString(2), rs.getString(3),
						rs.getDate(4), rs.getInt(5));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out
					.println("TodoUtis-retrieveTodoDetail()- retrieveTodoDetail failed");
		}
		return todo;
	}

	public static boolean addTodo(Todo td) {
		String sql = null;
		sql = "insert into todo values(?,?,?,?,?)";
		int count = 0;
		boolean bl = false;
		try {
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setNull(1, Types.INTEGER);
			pst.setString(2, td.getTodoSub());
			pst.setString(3, td.getTodoDesc());
			pst.setDate(4, td.getTargetDate());
			pst.setInt(5, td.getOwnerId());
			System.out.println("addtodo"+td.getTodoSub());
			count = JdbcUtils.SQLupdate(pst);
			if (count != 0) {
				bl = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("TodoUtis-addTodo()- addTodo failed");
		}
		return bl;
	}
	public static boolean deleteTodo(String todoId) {
		String sql = null;
		sql = "delete from todo where todoId = ?";
		int count = 0;
		boolean bl = false;
		try {
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, Integer.parseInt(todoId));
			count = JdbcUtils.SQLupdate(pst);
			if (count != 0) {
				bl = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("TodoUtis-addTodo()- deleteTodo failed");
		}
		return bl;
	}
	public static boolean updateTodo(Todo td) {
		String sql = null;
		sql = "update todo set todoSub = ?,todoDesc = ?,targetDate =? where todoId = ?";
		int count = 0;
		boolean bl = false;
		try {
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, td.getTodoSub());
			pst.setString(2, td.getTodoDesc());
			pst.setDate(3, td.getTargetDate());
			pst.setInt(4, td.getTodoId());
			count = JdbcUtils.SQLupdate(pst);
			if (count != 0) {
				bl = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("TodoUtis-updateTodo()- updateTodo failed");
		}
		return bl;
	}
}
