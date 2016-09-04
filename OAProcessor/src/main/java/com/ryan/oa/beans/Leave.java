package com.ryan.oa.beans;

import java.sql.Date;

import org.springframework.stereotype.Service;
@Service
public class Leave {

	private int leaveId = 99999999;
	private String leaveDesc = "99999999";
	private Date fromTime = new Date(1000, 10, 10);
	private Date toTime = new Date(1000,10,10);
	private int ownerId = 99999999;
	private int approverId = 99999999;
	private int status = 0;
	public int getLeaveId() {
		return leaveId;
	}
	public void setLeaveId(int leaveId) {
		this.leaveId = leaveId;
	}
	public String getLeaveDesc() {
		return leaveDesc;
	}
	public void setLeaveDesc(String leaveDesc) {
		this.leaveDesc = leaveDesc;
	}
	public Date getFromTime() {
		return fromTime;
	}
	public void setFromTime(Date fromTime) {
		this.fromTime = fromTime;
	}
	public Date getToTime() {
		return toTime;
	}
	public void setToTime(Date toTime) {
		this.toTime = toTime;
	}
	public int getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}
	public int getApproverId() {
		return approverId;
	}
	public void setApproverId(int approverId) {
		this.approverId = approverId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
