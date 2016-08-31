package com.ryan.oa.beans;

public class ProjectStatus {

	private int statusId;
	private String statusDesc;
	public ProjectStatus(){
		
	}
	public int getStatusId() {
		return statusId;
	}
	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
	public String getStatusDesc() {
		return statusDesc;
	}
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}
	public ProjectStatus(int statusId, String statusDesc) {
		super();
		this.statusId = statusId;
		this.statusDesc = statusDesc;
	}
}
