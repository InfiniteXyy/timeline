package service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import control.MainControl;
import entity.Message;
import entity.User;
import http.util.HttpUtil;

public class Service {
	
	private static String apiUrl = "http://timeline.infinitex.cn";
	
	public static List<Message> getAllMessages() throws JSONException {
		String result = HttpUtil.sendGet(apiUrl + "/api/messages?limit=20");
		JSONObject jsonObj = new JSONObject(result);
		JSONArray messages = new JSONArray(jsonObj.getString("messages"));
		List <Message> messageList = new ArrayList();
		
		for(int i = 0; i < messages.length(); i++) {
			JSONObject message = messages.getJSONObject(i);
			String createdAt = message.getString("createdAt");
			JSONObject authorObj = message.getJSONObject("author");
			int id = message.getInt("id");
			String body = message.getString("body");
			String updatedAt = message.getString("updatedAt");
			String imageUrl = message.getString("imageUrl");
			messageList.add(new Message(createdAt, id, body, updatedAt, authorObj.getString("image"), authorObj.getString("username"), imageUrl));
		}
		Collections.sort(messageList);
		return messageList;
	}
	
	public static User login(User user) {
		JSONObject param = new JSONObject();
		JSONObject userObj = new JSONObject();
		try {
			userObj.put("email", user.getEmail());
			userObj.put("password", user.getPassword());
			param.put("user", userObj);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
		JSONObject result = HttpUtil.sendPost(apiUrl + "/api/users/login", param);
		if(result == null) {
			return null;
		}
		JSONObject tmpUser = new JSONObject();
		try {
			tmpUser = result.getJSONObject("user");
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		User returnUser = new User();
		
		try {
			returnUser.setImage(tmpUser.getString("image"));
			returnUser.setToken(tmpUser.getString("token"));
			returnUser.setEmail(tmpUser.getString("email"));
			returnUser.setUsername(tmpUser.getString("username"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return returnUser;
	}
	
	public static boolean register(User user) {
		JSONObject param = new JSONObject();
		JSONObject userObj = new JSONObject();
		try {
			userObj.put("email", user.getEmail());
			userObj.put("username", user.getUsername());
			userObj.put("password", user.getPassword());
			param.put("user", userObj);
		} catch (JSONException e) {

			e.printStackTrace();
		}
		
		JSONObject result = HttpUtil.sendPost(apiUrl + "/api/users", param);
		return result == null ? false : true;
	}
	
	public static boolean createMessage(User user, Message message) {
		JSONObject param = new JSONObject();
		JSONObject messageObj = new JSONObject();
		Map<String, String> headers = new HashMap();
		
		try {
			messageObj.put("body", message.getBody());
			param.put("message", messageObj);
			headers.put("Authorization", "Bearer " + user.getToken());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		JSONObject result = HttpUtil.sendPostWithHeader(apiUrl + "/api/messages", param, headers);
		if(result == null) {
			return false;
		} else {
			return true;
		}
	}
}
