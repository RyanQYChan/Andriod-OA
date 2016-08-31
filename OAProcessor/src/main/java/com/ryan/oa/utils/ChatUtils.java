package com.ryan.oa.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ryan.oa.beans.Chat;
import com.ryan.oa.beans.ChatMsg;
import com.ryan.oa.beans.Meeting;

public class ChatUtils {
	private static Connection conn = JdbcUtils.DBConnection();

	public static boolean sendMessage(ChatMsg chatMsg) {
		boolean bl = false;
		String sql = null;
		sql = "insert into chatmsg values(?,?,?,?,?,?,?)";
		int count = 0;
		try {
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setNull(1, Types.INTEGER);
			pst.setTimestamp(2, chatMsg.getMsgTime());
			pst.setInt(3, chatMsg.getSenderId());
			pst.setString(4, chatMsg.getReceiverId());
			pst.setString(5, chatMsg.getMsgContent());
			pst.setInt(6, chatMsg.getMsgStatus());
			pst.setInt(7, chatMsg.getChatId());
			Chat chat = new Chat();
			chat.setLatestMsg(chatMsg.getMsgContent());
			chat.setLatestTime(chatMsg.getMsgTime());
			chat.setChatId(chatMsg.getChatId());
			if (updateChat(chat)) {
				count = JdbcUtils.SQLupdate(pst);
			}
			if (count != 0) {
				bl = true;
			}
		} catch (SQLException e) {
			bl = false;
		}

		return bl;
	}

	public static boolean deleteChat(Chat chat) {
		boolean bl = false;
		String sql = "delete from chat where chatId = ?";
		PreparedStatement pst = null;
		int count = 0;
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, chat.getChatId());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (deletChatMsg(chat)) {
			count = JdbcUtils.SQLupdate(pst);
		}
		if (count != 0) {
			bl = true;
		}
		return bl;
	}

	public static boolean deletChatMsg(Chat chat) {
		boolean bl = false;
		String sql = "delete from chatmsg WHERE chatId = ?";
		PreparedStatement pst = null;
		int count = 0;
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, chat.getChatId());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		count = JdbcUtils.SQLupdate(pst);
		if (count != 0) {
			bl = true;
		}
		return bl;
	}

	public static boolean updateChat(Chat chat) {
		boolean bl = false;
		String sql = "update chat set latestMsg= ?, latestTime = ? where chatId = ?";
		PreparedStatement pst = null;
		int count = 0;
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, chat.getLatestMsg());
			pst.setTimestamp(2, chat.getLatestTime());
			pst.setInt(3, chat.getChatId());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		count = JdbcUtils.SQLupdate(pst);
		if (count != 0) {
			bl = true;
		}
		return bl;
	}

	public static boolean udateChatMem(Chat chat) {
		boolean bl = false;
		String sql = "update chat set chatMemId= ?, chatMemName = ? where chatId = ?";
		PreparedStatement pst = null;
		int count = 0;
		try {
			pst = conn.prepareStatement(sql);
			String key = formatChatMemId(chat.getChatMem().keySet().toString());
			String value = formatChatMemName(chat.getChatMem().values()
					.toString());
			pst.setString(1, key);
			pst.setString(2, value);
			pst.setInt(3, chat.getChatId());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		count = JdbcUtils.SQLupdate(pst);
		if (count != 0) {
			bl = true;
		}
		return bl;
	}

	// 向数据库插入一个新的聊天
	public static boolean addChat(Chat chat) {
		boolean bl = false;
		String sql = "insert into chat values(?,?,?,?,?,?)";
		PreparedStatement pst = null;
		int count = 0;
		try {
			System.out.println(isChatExisted(chat) + "");
			if (isChatExisted(chat)) {// 判断要新建的聊天是否已存在
				bl = false;
			} else {
				pst = conn.prepareStatement(sql);
				pst.setNull(1, Types.INTEGER);
				pst.setInt(2, chat.getOwnerId());
				String key = formatChatMemId(chat.getChatMem().keySet()
						.toString());
				String value = formatChatMemName(chat.getChatMem().values()
						.toString());
				pst.setString(3, key);
				pst.setString(4, value);
				pst.setString(5, chat.getLatestMsg());
				pst.setTimestamp(6, chat.getLatestTime());
				count = JdbcUtils.SQLupdate(pst);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			bl = false;
		}
		if (count != 0) {
			bl = true;
		}
		return bl;
	}

	private static boolean isChatExisted(Chat chat) {
		String sql = "select chatMemId from chat where find_in_set(?,chatMemId)";
		ResultSet rs = null;
		PreparedStatement pst = null;
		boolean bl = false;
		String chatMemId = null;
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, chat.getOwnerId());
			rs = JdbcUtils.SQLexecute(pst);
			while (rs.next()) {
				chatMemId = rs.getString(1);
				bl = isIncludeSameChar(chatMemId, formatChatMemId(chat
						.getChatMem().keySet().toString()));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(chat.getChatMem().keySet().toString() + " "
				+ chatMemId + bl);
		return bl;
	}

	public static void getChat(Chat chat) {
		String sql = null;
		sql = "select * from chat where ownerId ";
	}

	// 从数据库获取聊天列表
	public static ArrayList<Chat> retrieveChatList(String loginId) {
		ResultSet rs = null;
		String sql = null;
		ArrayList<Chat> al = new ArrayList<Chat>();
		sql = "select * from chat where find_in_set(?,chatMemId) order by chatId desc";
		try {
			PreparedStatement pst = conn.prepareStatement(sql);
			int id = Integer.parseInt(loginId);
			pst.setInt(1, id);
			rs = JdbcUtils.SQLexecute(pst);
			if (!rs.next()) {
				// SimpleDateFormat sdf = new
				// SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				// 如果没有聊天的话，返回一个自定义的假聊天
				Chat chat = new Chat();
				al.add(chat);
			}
			rs.beforeFirst();// 重置ResultSet的指针为最前
			while (rs.next()) {
				Gson gs = new GsonBuilder()
						.setDateFormat("yyyy-MM-dd HH:mm:ss").create();
				Chat chat = new Chat();
				chat.setChatId(rs.getInt(1));
				chat.setOwnerId(rs.getInt(2));
				chat.setChatMem(initChatMem(rs.getString(3), rs.getString(4)));
				chat.setLatestMsg(rs.getString(5));
				chat.setLatestTime(rs.getTimestamp(6));
				al.add(chat);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out
					.println("ChatUtils-retrieveChatList()- retrieveChatList failed");
		}
		return al;
	}

	// 从数据库获取某个聊天的所有消息
	public static ArrayList<ChatMsg> retrieveChatMsgList(Chat chat) {
		ResultSet rs = null;
		String sql = null;
		ArrayList<ChatMsg> al = new ArrayList<ChatMsg>();
		sql = "select * from chatmsg WHERE chatId = ? order by msgId";
		try {
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, chat.getChatId());
			rs = JdbcUtils.SQLexecute(pst);
			if (!rs.next()) {
				// SimpleDateFormat sdf = new
				// SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				// 如果没有聊天的话，返回一个自定义的假聊天
				ChatMsg chatMsg = new ChatMsg();
				al.add(chatMsg);
			}
			rs.beforeFirst();// 重置ResultSet的指针为最前
			while (rs.next()) {
				Gson gs = new GsonBuilder()
						.setDateFormat("yyyy-MM-dd HH:mm:ss").create();
				ChatMsg chatMsg = new ChatMsg();
				chatMsg.setMsgId(rs.getInt(1));
				chatMsg.setMsgTime(rs.getTimestamp(2));
				chatMsg.setSenderId(rs.getInt(3));
				chatMsg.setReceiverId(rs.getString(4));
				chatMsg.setMsgContent(rs.getString(5));
				chatMsg.setMsgStatus(rs.getInt(6));
				chatMsg.setChatId(rs.getInt(7));
				al.add(chatMsg);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out
					.println("ChatUtils-retrieveChatMsgList()- retrieveChatMsgList failed");
		}
		return al;
	}

	// 从数据库获取出聊天的staff的id以及姓名组成map
	public static Map initChatMem(String key, String value) {
		Map<String, String> div = new HashMap<String, String>();
		key = formatChatMemId(key);
		value = formatChatMemName(value);
		String[] sKey = key.split(",");
		String[] sValue = value.split(",");
		for (int i = 0; i < sKey.length; i++) {
			div.put(sKey[i], sValue[i]);
		}
		return div;
	}

	public static String formatChatMemId(String key) {
		String skey = key.replace("[", "");
		skey = skey.replace("]", "");
		skey = skey.replace(" ", "");
		return skey;
	}

	public static String formatChatMemName(String value) {
		String sValue = value.replace("[", "");
		sValue = sValue.replace("]", "");
		sValue = sValue.replace(" ", "");
		return sValue;
	}

	// 判断两个字符串是否内容一样，但顺序不一样
	public static boolean isIncludeSameChar(String str1, String str2) {
		boolean flag = true;

		if (str1.length() != str2.length()) {
			flag = false;
		} else {
			char[] str1Arr = str1.toCharArray();
			Arrays.sort(str1Arr);
			char[] str2Arr = str2.toCharArray();
			Arrays.sort(str2Arr);
			for (int i = 0; i < str2Arr.length; i++) {
				if (str2Arr[i] == str1Arr[i]) {
					continue;
				} else {
					flag = false;
				}
			}
		}
		return flag;
	}

	// 判断新建的两个字符串内容是否一样
	public static boolean isSameMem(String str1, String str2) {
		boolean flag = false;
		String[] memStr1 = str1.split(",");
		String[] memStr2 = str2.split(",");
		flag = Arrays.equals(memStr1, memStr2);
		return flag;
	}

}
