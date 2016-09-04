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
import com.ryan.oa.beans.Project;
import com.ryan.oa.utils.ProjectUtils;

@Controller
public class ProjectController {
	@Autowired
	private Project pj;
	@RequestMapping(value="/retrieveProjectList",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public ArrayList<Project> retrieveProjectList(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		String loginId = request.getParameter("loginId");
		ArrayList<Project> al = new ArrayList<Project>();
		al = ProjectUtils.retrieveProjectList(loginId);
		return al;
	}

	@RequestMapping(value="/retrieveProjectDetail",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public Project retrieveProjectDetail(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		String projectId = request.getParameter("projectId");
		pj = ProjectUtils.retrieveProjectDetail(projectId);
		return pj;
	}

	@RequestMapping(value="/addProject",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public boolean addProject(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws IOException {
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
		Gson gs = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		pj = gs.fromJson(result, Project.class);
		return ProjectUtils.addProject(pj);
	}

	@RequestMapping(value="/deleteProject",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public boolean deleteProject(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws IOException {
		String projectId = request.getParameter("projectId");
		return ProjectUtils.deleteProject(projectId);
	}
	
	@RequestMapping(value="/updateProject",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public boolean updateProject(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws IOException {
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
		Gson gs = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		pj = gs.fromJson(result, Project.class);
		return ProjectUtils.updateProject(pj);
	}
}
