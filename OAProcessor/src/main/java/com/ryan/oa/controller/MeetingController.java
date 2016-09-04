package com.ryan.oa.controller;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
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
import com.ryan.oa.beans.Meeting;
import com.ryan.oa.beans.Room;
import com.ryan.oa.utils.CommonUtils;
import com.ryan.oa.utils.MeetingUtils;

@Controller
public class MeetingController {

	@Autowired
	private Meeting mt;
	@RequestMapping(value="/retrieveRoomList",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String retrieveRoomList(HttpServletRequest request,
			HttpServletResponse response, HttpSession session){
		InputStream is = null;
		String line = "";
        String result = "";
		try {
			is = request.getInputStream();
			InputStreamReader isr = new InputStreamReader(is, "utf-8");
			BufferedReader br = new BufferedReader(isr);
			
	        while(null != (line=br.readLine())){
	            result += line;
	        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(result);
		Gson gs = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		mt = gs.fromJson(result, Meeting.class);
		ArrayList<Room> al = new ArrayList<Room>();
		al = CommonUtils.retrieveRoomList(mt);
		
		String reponse = gs.toJson(al, new TypeToken<ArrayList<Room>>() { }.getType());
		return reponse;
	}
	
	@RequestMapping(value="/retrieveMeetingList",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String retrieveMeetingList(HttpServletRequest request,
			HttpServletResponse response, HttpSession session){
		String loginId = request.getParameter("loginId");
		ArrayList<Meeting> al = new ArrayList<Meeting>();
		al = MeetingUtils.retrieveMeetingList(loginId);
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd  HH:mm:ss").create();
		String reponse = gson.toJson(al, new TypeToken<ArrayList<Meeting>>() { }.getType());
		return reponse;
	}

	@RequestMapping(value="/retrieveMeetingDetail",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public Meeting retrieveMeetingDetail(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		String meetingId = request.getParameter("meetingId");
		mt = MeetingUtils.retrieveMeetingDetail(meetingId);
		return mt;
	}

	@RequestMapping(value="/addMeeting",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public boolean addMeeting(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws IOException {
		InputStream is = null;
		is = request.getInputStream();
		InputStreamReader isr = new InputStreamReader(is, "utf-8");
		BufferedReader br = new BufferedReader(isr);
		String line = "";
        String result = "";
        while(null != (line=br.readLine())){
            result += line;
        }
		System.out.println(result);
		Gson gs = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		mt = gs.fromJson(result, Meeting.class);
		return MeetingUtils.addMeeting(mt);
	}

	@RequestMapping(value="/deleteMeeting",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public boolean deleteMeeting(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws IOException {
		String meetingId = request.getParameter("meetingId");
		return MeetingUtils.deleteMeeting(meetingId);
	}
	@RequestMapping(value="/updateMeeting",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public boolean updateMeeting(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws IOException {
		InputStream is = null;
		is = request.getInputStream();
		InputStreamReader isr = new InputStreamReader(is, "utf-8");
		BufferedReader br = new BufferedReader(isr);
		String line = "";
        String result = "";
        while(null != (line=br.readLine())){
            result += line;
        }
		System.out.println(result);
		Gson gs = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		mt = gs.fromJson(result, Meeting.class);
		return MeetingUtils.updateMeeting(mt);
	}
}
