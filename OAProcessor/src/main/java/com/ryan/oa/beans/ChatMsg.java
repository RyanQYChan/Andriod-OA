package com.ryan.oa.beans;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.stereotype.Service;
@Service
public class ChatMsg {

	private int msgId = 99999999;
	private Timestamp msgTime = new Timestamp(new Date().getTime());
	private int senderId = 999999999;
	private String receiverId = "999999999";
	private String msgContent = "999999999";
	private int msgStatus = 1;
	private int chatId = 99999999;
	
	public int getMsgId() {
		return msgId;
	}
	public void setMsgId(int msgId) {
		this.msgId = msgId;
	}
	public Timestamp getMsgTime() {
		return msgTime;
	}
	public void setMsgTime(Timestamp msgTime) {
		this.msgTime = msgTime;
	}
	public int getSenderId() {
		return senderId;
	}
	public void setSenderId(int sendId) {
		this.senderId = sendId;
	}
	public String getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}
	public String getMsgContent() {
		return msgContent;
	}
	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
	public int getMsgStatus() {
		return msgStatus;
	}
	public void setMsgStatus(int msgStatus) {
		this.msgStatus = msgStatus;
	}
	public int getChatId() {
		return chatId;
	}
	public void setChatId(int chatId) {
		this.chatId = chatId;
	}
}
