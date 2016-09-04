package com.ryan.oa.beans;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Service;
@Service
public class Chat {
	private int chatId = 99999999;
	private int ownerId = 99999999 ;
	private Map<String, String> chatMem = null;
	private String latestMsg = "99999999";
	private Timestamp latestTime = new Timestamp(new Date().getTime());
    
	public int getChatId() {
		return chatId;
	}

	public void setChatId(int chatId) {
		this.chatId = chatId;
	}

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	
	public Map<String, String> getChatMem() {
		return chatMem;
	}

	public void setChatMem(Map<String, String> chatMem) {
		this.chatMem = chatMem;
	}
	public String getLatestMsg() {
		return latestMsg;
	}

	public void setLatestMsg(String latestMsg) {
		this.latestMsg = latestMsg;
	}

	public Timestamp getLatestTime() {
		return latestTime;
	}

	public void setLatestTime(Timestamp latestTime) {
		this.latestTime = latestTime;
	}
}
