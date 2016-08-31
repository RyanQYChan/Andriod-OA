package com.ryan.oa.beans;

import java.sql.Date;
import java.util.Map;

public class Project {
	private int projectId = 99999999;
	private String projectSub = "99999999";
	private String projectDesc = "99999999";
	private Map<?, ?> projectDiv = null;
	private Date openDate = new Date(new java.util.Date().getTime());
	private Date targetDate = new Date(new java.util.Date().getTime());
	private int projectStatus = 0;
	private int ownerId = 99999999;
	/*	public Project(){
		
	}

	public Project(int projectId, String projectSub, String projectDesc,
			String projectMem, Date openDate, Date targetDate,
			int projectStatus, int ownerId) {
		super();
		this.projectId = projectId;
		this.projectSub = projectSub;
		this.projectDesc = projectDesc;
		this.projectMem = projectMem;
		this.openDate = openDate;
		this.targetDate = targetDate;
		this.projectStatus = projectStatus;
		this.ownerId = ownerId;
	}*/

	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	public String getProjectSub() {
		return projectSub;
	}
	public void setProjectSub(String projectSub) {
		this.projectSub = projectSub;
	}
	public String getProjectDesc() {
		return projectDesc;
	}
	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	public void setProjectDesc(String projectDesc) {
		this.projectDesc = projectDesc;
	}
	public Map getProjectDiv() {
		return projectDiv;
	}

	public void setProjectDiv(Map projectDiv) {
		this.projectDiv = projectDiv;
	}
	public Date getOpenDate() {
		return openDate;
	}
	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}
	public Date getTargetDate() {
		return targetDate;
	}
	public void setTargetDate(Date targetDate) {
		this.targetDate = targetDate;
	}
	public int getProjectStatus() {
		return projectStatus;
	}
	public void setProjectStatus(int projectStatus) {
		this.projectStatus = projectStatus;
	}
	
}
