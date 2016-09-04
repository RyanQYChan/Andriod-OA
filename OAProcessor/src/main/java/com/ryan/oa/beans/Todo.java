package com.ryan.oa.beans;

import java.sql.Date;
import org.springframework.stereotype.Service;

@Service
public class Todo {
	private int todoId;
	private String todoSub;
	private String todoDesc;
	private Date targetDate;
	private int ownerId;
	public Todo(){
		
	};
	public Todo(int todoId, String todoSub, String todoDesc, Date targetDate,
			int ownerId) {
		super();
		this.todoId = todoId;
		this.todoSub = todoSub;
		this.todoDesc = todoDesc;
		this.targetDate = targetDate;
		this.ownerId = ownerId;
	}
	public int getTodoId() {
		return todoId;
	}
	public void setTodoId(int todoId) {
		this.todoId = todoId;
	}
	public String getTodoSub() {
		return todoSub;
	}
	public void setTodoSub(String todoSub) {
		this.todoSub = todoSub;
	}
	public String getTodoDesc() {
		return todoDesc;
	}
	public void setTodoDesc(String todoDesc) {
		this.todoDesc = todoDesc;
	}
	public Date getTargetDate() {
		return targetDate;
	}
	public void setTargetDate(Date targetDate) {
		this.targetDate = targetDate;
	}
	public int getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}
	
}
