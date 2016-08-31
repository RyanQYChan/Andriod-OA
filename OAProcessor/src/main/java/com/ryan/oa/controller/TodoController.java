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
import com.ryan.oa.beans.Todo;
import com.ryan.oa.utils.TodoUtils;
@Controller
public class TodoController {
	@RequestMapping(value="/retrieveTodoList",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public ArrayList<Todo> retrieveTodoist(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		String ownerId = request.getParameter("ownerId");
		ArrayList<Todo> al = new ArrayList<Todo>();
		al = TodoUtils.retrieveTodoList(ownerId);
		return al;
	}
	@RequestMapping(value="/retrieveTodoDetail",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public Todo retrieveTodoDetail(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		String todoId = request.getParameter("todoId");
		Todo td = TodoUtils.retrieveTodoDetail(todoId);
		return td;
	}
	@RequestMapping(value="/addTodo",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public boolean addTodo(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException{
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
		Todo td = gs.fromJson(result, Todo.class);
		System.out.println("addTodo"+result);
		return TodoUtils.addTodo(td);
	}
	//deleteTodo
	@RequestMapping(value="/deleteTodo",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public boolean deleteTodo(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		String todoId = request.getParameter("todoId");
		return TodoUtils.deleteTodo(todoId);
	}
	@RequestMapping(value="/updateTodo",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public boolean updateTodo(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException{
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
		Todo td = gs.fromJson(result, Todo.class);
		return TodoUtils.updateTodo(td);
	}
}
