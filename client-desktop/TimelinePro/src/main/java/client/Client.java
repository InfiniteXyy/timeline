package client;

import http.util.HttpUtil;
import service.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.json.JSONObject;
import org.joda.time.DateTime;
import entity.Message;
import entity.User;

import org.json.JSONArray;
import org.json.JSONException;

public class Client {
	public static void main(String[] args) throws JSONException, ParseException {
		
		List <Message> messageList = new ArrayList<>();
		messageList = Service.getAllMessages();
		for(Message message : messageList) {
			System.out.println(message.getImageUrl());
			System.out.println("1");
		}
		
		/*
		String result = HttpUtil.sendGet("http://timeline.infinitex.cn/api/messages");
		//System.out.println(result);
		JSONObject jsonObj = new JSONObject(result);
		JSONArray messages = new JSONArray(jsonObj.getString("messages"));
		//System.out.println(messages.length());
		List <Message> messageList = new ArrayList<>();
		
		for(int i = 0; i < messages.length(); i++) {
			JSONObject message = messages.getJSONObject(i);
			String createdAt = message.getString("createdAt");
			JSONObject authorObj = message.getJSONObject("author");
			int id = message.getInt("id");
			String body = message.getString("body");
			String updatedAt = message.getString("updatedAt");
			messageList.add(new Message(createdAt, id, body, updatedAt, authorObj.getString("image"), authorObj.getString("username")));
		}
		
		for(int i = 0; i < messageList.size(); i++) {
			System.out.println(messageList.get(i).getBody());
		}
		*/
		//JSONArray jsonArr = new JSONArray(result);
		//System.out.println(jsonArr.length());
		/*
		JSONObject params = new JSONObject();
		JSONObject user = new JSONObject();
		user.put("email", "zxl@zxl.cn");
		user.put("password", "zxl");
		params.put("user", user);
		*/
		/*
		User user = new User();
		user.setEmail("zxl123@zxl.cn");
		user.setUsername("zxl");
		user.setPassword("zxl");
		
		Message msg = new Message();
		msg.setBody("from java");
		*/
		//User returnUser = Service.login(user);
		//JSONObject result = Service.register(user);
		//System.out.println(result.toString());
		
		//System.out.println(result.toString());
	}
}
