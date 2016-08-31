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
		ChatMsg ct = gs.fromJson(result, ChatMsg.class);
		System.out.println(ct.getMsgContent());
		return ChatUtils.sendMessage(ct);
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
		Chat chat = new Chat();

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
		Chat chat = new Chat();

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
		Chat chat = new Chat();

		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
				.create();
		chat = gson.fromJson(result, Chat.class);
		return ChatUtils.deleteChat(chat);
	}
	@RequestMapping(value = "/retrieveChatMsgList", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public static String retrieveChatMsgList(HttpServletRequest request,
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
		Chat ct = gs.fromJson(result, Chat.class);
		ArrayList<ChatMsg> al = new ArrayList<ChatMsg>();
		al = ChatUtils.retrieveChatMsgList(ct);
		result = gs.toJson(al, new TypeToken<ArrayList<ChatMsg>>() {
		}.getType());
		return result;
	}
	
}
