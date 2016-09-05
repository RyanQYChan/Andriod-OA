package com.ryan.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;

public class ControllerTest {
	private String server_url = "http://localhost:8080/OAProcessor/";
	private HttpURLConnection conn;
	
	public String connectServer(String action,String param){
		String url = server_url + action + "?" + param;
		System.out.println(url);
		java.net.URL urlNet = null;
		HttpURLConnection conn = null;
		String result = "false";
		try {
			urlNet = new java.net.URL(url);
			conn = (HttpURLConnection) urlNet.openConnection();
			conn.setReadTimeout(5 * 2000);
			conn.setConnectTimeout(5 * 2000);
			conn.setRequestMethod("GET");
			byte[] byteParam = param.getBytes();
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");// 请求头, 必须设置
			conn.setRequestProperty("Content-Length", param.getBytes().length
					+ "");// 注意是字节长度,不是字符长度
			conn.setRequestProperty("Charset", "utf-8");
			conn.setDoOutput(true);// 准备写出
			conn.getOutputStream().write(byteParam);

			InputStream is = null;
			is = conn.getInputStream();
			InputStreamReader isr = new InputStreamReader(is, "utf-8");
			BufferedReader br = new BufferedReader(isr);
			String line = "";
			result = "";
			while (null != (line = br.readLine())) {
				result += line;
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			result = "ExceptionFalseWrong";
		}
		return result;
	}
	@Test
	public void testUserController() {
		System.out.println("Test UserController");
		System.out.println(connectServer("userLogin","loginId=10000&&userPwd=Ryan"));
	}

	@Test
	public void testChatController() {
		System.out.println("Test ChatController");
	}

	@Test
	public void testMeetingController() {
		System.out.println("Test MeetingController");
	}

	@Test
	public void testProjectController() {
		System.out.println("Test ProjectController");
	}
}
