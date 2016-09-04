package com.ryan.oa.beans;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.stereotype.Service;
@Service
public class Meeting {
	private int meetingId = 99999999;
	private String meetingSub = "99999999";
	private String meetingDesc = "99999999";
	private String meetingMem = "99999999";
	private Timestamp meetingTime = new Timestamp(new Date().getTime());;
	private Timestamp endTime = new Timestamp(new Date().getTime());;
	private int meetingRoom = 99999999;
	private int ownerId = 99999999;
	
	public int getMeetingId() {
		return meetingId;
	}
	public String getMeetingMem() {
		return meetingMem;
	}
	public void setMeetingMem(String meetingMem) {
		this.meetingMem = meetingMem;
	}
	public void setMeetingId(int meetingId) {
		this.meetingId = meetingId;
	}
	public String getMeetingSub() {
		return meetingSub;
	}
	public void setMeetingSub(String meetingSub) {
		this.meetingSub = meetingSub;
	}
	public String getMeetingDesc() {
		return meetingDesc;
	}
	public void setMeetingDesc(String meetingDesc) {
		this.meetingDesc = meetingDesc;
	}
	public Timestamp getMeetingTime() {
		return meetingTime;
	}
	public void setMeetingTime(Timestamp meetingTime) {
		this.meetingTime = meetingTime;
	}
	public int getMeetingRoom() {
		return meetingRoom;
	}
	public void setMeetingRoom(int meetingRoom) {
		this.meetingRoom = meetingRoom;
	}
	public int getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}
	public Timestamp getEndTime() {
		return endTime;
	}
	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}
	
}
