package com.ryan.oa.controller;

import java.io.BufferedReader;
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
import com.ryan.oa.beans.Leave;
import com.ryan.oa.beans.Meeting;
import com.ryan.oa.utils.LeaveUtils;

@Controller
public class LeaveController {
	@Autowired
	private Leave lv;
	@RequestMapping(value = "/retrieveMyLeaveList", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String retrieveLeaveist(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		String ownerId = request.getParameter("loginId");
		ArrayList<Leave> al = new ArrayList<Leave>();
		al = LeaveUtils.retrieveMyLeaveList(ownerId);
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		String reponse = gson.toJson(al, new TypeToken<ArrayList<Leave>>() {
		}.getType());
		return reponse;
	}

	@RequestMapping(value = "/retrieveApprLeaveList", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String retrieveApprLeaveList(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		String approverId = request.getParameter("loginId");
		ArrayList<Leave> al = new ArrayList<Leave>();
		al = LeaveUtils.retrieveApprLeaveList(approverId);
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		String reponse = gson.toJson(al, new TypeToken<ArrayList<Leave>>() {
		}.getType());
		return reponse;
	}

	@RequestMapping(value = "/retrieveLeaveDetail", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public Leave retrieveLeaveDetail(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		String leaveId = request.getParameter("leaveId");
		lv = LeaveUtils.retrieveLeaveDetail(leaveId);
		return lv;
	}

	@RequestMapping(value = "/addLeave", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public boolean addLeave(HttpServletRequest request,
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
		Gson gs = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		lv = gs.fromJson(result, Leave.class);
		System.out.println("addLeave" + result);
		return LeaveUtils.addLeave(lv);
	}

	// deleteLeave
	@RequestMapping(value = "/deleteLeave", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public boolean deleteLeave(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		String leaveId = request.getParameter("leaveId");
		return LeaveUtils.deleteLeave(leaveId);
	}

	@RequestMapping(value = "/updateLeave", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public boolean updateLeave(HttpServletRequest request,
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
		Gson gs = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		lv = gs.fromJson(result, Leave.class);
		return LeaveUtils.updateLeave(lv);
	}

	@RequestMapping(value = "/approveLeave", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public boolean approveLeave(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws IOException {
		String leaveId = request.getParameter("leaveId");
		return LeaveUtils.approveLeave(leaveId, 2);
	}

	@RequestMapping(value = "/rejectLeave", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public boolean rejectLeave(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		String leaveId = request.getParameter("leaveId");
		return LeaveUtils.approveLeave(leaveId, 1);
	}
}
