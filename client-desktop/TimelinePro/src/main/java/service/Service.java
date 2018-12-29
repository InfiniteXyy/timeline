package service;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import entity.Message;
import entity.User;
import http.util.HttpUtil;


public class Service {
	
	private static String apiUrl = "http://timeline.infinitex.cn";
	
	public static List<Message> getAllMessages() throws JSONException {
		String result = HttpUtil.sendGet(apiUrl + "/api/messages?limit=20");
		JSONObject jsonObj = new JSONObject(result);
		JSONArray messages = new JSONArray(jsonObj.getString("messages"));
		List <Message> messageList = new ArrayList<Message>();
		
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
			messageObj.put("imageUrl", message.getImageUrl());
			param.put("message", messageObj);
			headers.put("Authorization", "Bearer " + user.getToken());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
		JSONObject result = HttpUtil.sendPostWithHeader(apiUrl + "/api/messages", param, headers);
		if(result == null) {
			return false;
		} else {
			return true;
		}
	}

	public static String uploadImage(File file) throws IOException {
		String result = null;
		if (!file.exists() || !file.isFile()) {
			throw new IOException("文件不存在");
		}
		URL urlObj = new URL("http://cdn.infinitex.cn/api/upload");
		HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
		con.setRequestMethod("POST"); // 设置关键值,以Post方式提交表单，默认get方式
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false); // post方式不能使用缓存
		// 设置请求头信息
		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestProperty("Charset", "UTF-8");
		con.setRequestProperty("Authorization", "Bearer ILoveInfinitex.cn");
		// 设置边界
		String BOUNDARY = "----------" + System.currentTimeMillis();
		con.setRequestProperty("Content-Type", "multipart/form-data; boundary="+ BOUNDARY);
		// 请求正文信息
		// 第一部分：
		StringBuilder sb = new StringBuilder();
		sb.append("--"); // 必须多两道线
		sb.append(BOUNDARY);
		sb.append("\r\n");
		sb.append("Content-Disposition: form-data;name=\"file\";filename=\""+ file.getName() + "\"\r\n");
		sb.append("Content-Type:application/octet-stream\r\n\r\n");
		byte[] head = sb.toString().getBytes("utf-8");
		// 获得输出流
		OutputStream out = new DataOutputStream(con.getOutputStream());
		// 输出表头
		out.write(head);
		// 文件正文部分
		// 把文件已流文件的方式 推入到url中
		DataInputStream in = new DataInputStream(new FileInputStream(file));
		int bytes = 0;
		byte[] bufferOut = new byte[1024];
		while ((bytes = in.read(bufferOut)) != -1) {
			out.write(bufferOut, 0, bytes);
		}
		in.close();
		// 结尾部分
		byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
		out.write(foot);
		out.flush();
		out.close();
		StringBuffer buffer = new StringBuffer();
		BufferedReader reader = null;
		try {
			// 定义BufferedReader输入流来读取URL的响应
			reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			if (result == null) {
				result = buffer.toString();
			}
		} catch (IOException e) {
			System.out.println("发送POST请求出现异常！" + e);
			e.printStackTrace();
			throw new IOException("数据读取异常");
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		JSONObject param = null;
		JSONObject data = null;
		String path = "";
		try {
			param = new JSONObject(result);
			data = param.getJSONObject("data");
			path = data.getString("path");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return "http://timeline.infinitex.cn/img" + path;
	}
}
