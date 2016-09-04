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
import com.ryan.oa.beans.Todo;
import com.ryan.oa.beans.User;
import com.ryan.oa.utils.TodoUtils;
import com.ryan.oa.utils.UserUtils;

@Controller
public class UserController {

	@Autowired
	private User user;
	@RequestMapping(value = "/userLogin", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public User userLogin(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		// Gson gs = new Gson();

		String sloginId = request.getParameter("loginId");
		String userPwd = request.getParameter("userPwd");
		System.out.println(sloginId + userPwd);
		
		//User user = null;
		if (UserUtils.userLogin(sloginId, userPwd)) {
			user = UserUtils.getUserInfo(sloginId);
		} /*else {
			user = new User();
		}*/
		// String str = gs.toJson(user);
		return user;
	}

	@RequestMapping(value = "/getUserInfo")
	@ResponseBody
	public User getUserInfo(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		// Gson gs = new Gson();
		String sloginId = request.getParameter("loginId");
		//User user = null;
		user = UserUtils.getUserInfo(sloginId);
		// String str = gs.toJson(user);
		return user;
	}
	
	@RequestMapping(value = "/getUserList")
	@ResponseBody
	public ArrayList<User> getUserList(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		// Gson gs = new Gson();
		ArrayList<User> userAL= new ArrayList<User>();
		String sloginId = request.getParameter("loginId");
		userAL = UserUtils.getUserList(sloginId);
		// String str = gs.toJson(user);
		return userAL;
	}
	
	@RequestMapping(value="/changePwd",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public boolean changePwd(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException{
		InputStream is = null;
		is = request.getInputStream();
		InputStreamReader isr = new InputStreamReader(is, "utf-8");
		BufferedReader br = new BufferedReader(isr);
		String line = "";
        String result = "";
        while(null != (line=br.readLine())){
            result += line;
        }
		Gson gs = new GsonBuilder()
		   .setDateFormat("yyyy-MM-dd").create();
		user = gs.fromJson(result, User.class);
		return UserUtils.changePwd(user);
	}
}
