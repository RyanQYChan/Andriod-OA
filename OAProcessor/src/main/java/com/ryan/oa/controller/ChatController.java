package com.ryan.oa.controller;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.ryan.oa.beans.Chat;
import com.ryan.oa.beans.ChatMsg;
import com.ryan.oa.beans.Meeting;
import com.ryan.oa.utils.ChatUtils;
import com.ryan.oa.utils.MeetingUtils;

@Controller
public class ChatController {
	@Autowired
	private Chat chat;
	@Autowired
	private ChatMsg chatMsg;
	
	@RequestMapping(value = "/sendMessage", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public boolean sendMessage(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws IOException {

		InputStream is = null;
		is = request.getInputStream();
		InputStreamReader isr = new InputStreamReader(is, "utf-8");
		BufferedReader br = new BufferedReader(isr);
		String line = "";
		String result = "";
		while (null != (line = br.readLine())) {
			result += line;
		}

		/*
		 * int len = -1; byte[] buf = new byte[4096]; ByteArrayOutputStream baos
		 * = new ByteArrayOutputStream(); while ((len = is.read(buf)) != -1) {
		 * baos.write(buf, 0, len); } byte[] lens = baos.toByteArray();
		 */
		// String result = new String(lens);
		System.out.println(result);
		Gson gs = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
				.create();
		chatMsg = gs.fromJson(result, ChatMsg.class);
		System.out.println(chatMsg.getMsgContent());
		return ChatUtils.sendMessage(chatMsg);
	}

	@RequestMapping(value = "/retrieveChatList", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String retrieveChatList(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		String loginId = request.getParameter("loginId");
		ArrayList<Chat> al = new ArrayList<Chat>();
		al = ChatUtils.retrieveChatList(loginId);
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd  HH:mm:ss")
				.create();
		String reponse = gson.toJson(al, new TypeToken<ArrayList<Chat>>() {
		}.getType());
		return reponse;
	}

	@RequestMapping(value = "/udateChatMem", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public boolean udateChatMem(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws IOException {
		InputStream is = null;
		is = request.getInputStream();
		InputStreamReader isr = new InputStreamReader(is, "utf-8");
		BufferedReader br = new BufferedReader(isr);
		String line = "";
		String result = "";
		while (null != (line = br.readLine())) {
			result += line;
		}
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
				.create();
		chat = gson.fromJson(result, Chat.class);
		return ChatUtils.udateChatMem(chat);
	}
	@RequestMapping(value = "/addChat", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public boolean addChat(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws IOException {
		InputStream is = null;
		is = request.getInputStream();
		InputStreamReader isr = new InputStreamReader(is, "utf-8");
		BufferedReader br = new BufferedReader(isr);
		String line = "";
		String result = "";
		while (null != (line = br.readLine())) {
			result += line;
		}
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
				.create();
		chat = gson.fromJson(result, Chat.class);
		return ChatUtils.addChat(chat);
	}

	@RequestMapping(value = "/deleteChat", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public boolean deleteChat(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws IOException {
		InputStream is = null;
		is = request.getInputStream();
		InputStreamReader isr = new InputStreamReader(is, "utf-8");
		BufferedReader br = new BufferedReader(isr);
		String line = "";
		String result = "";
		while (null != (line = br.readLine())) {
			result += line;
		}
		System.out.println(result);
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
				.create();
		chat = gson.fromJson(result, Chat.class);
		return ChatUtils.deleteChat(chat);
	}
	@RequestMapping(value = "/retrieveChatMsgList", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public  String retrieveChatMsgList(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws IOException {
		InputStream is = null;
		is = request.getInputStream();
		InputStreamReader isr = new InputStreamReader(is, "utf-8");
		BufferedReader br = new BufferedReader(isr);
		String line = "";
		String result = "";
		while (null != (line = br.readLine())) {
			result += line;
		}
		Gson gs = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
				.create();
		chat = gs.fromJson(result, Chat.class);
		ArrayList<ChatMsg> al = new ArrayList<ChatMsg>();
		al = ChatUtils.retrieveChatMsgList(chat);
		result = gs.toJson(al, new TypeToken<ArrayList<ChatMsg>>() {
		}.getType());
		return result;
	}
	
}
