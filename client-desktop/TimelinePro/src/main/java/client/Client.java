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

import control.MainControl;

import org.joda.time.DateTime;
import entity.Message;
import entity.User;

import org.json.JSONArray;
import org.json.JSONException;

public class Client {
	public static void main(String[] args) throws JSONException, ParseException, InterruptedException {
		User user = new User();
		user.setEmail("zxl@zxl.cn");
		user.setPassword("zxl1");
		//MainControl.user = Service.login(user);
		
		Message msg = new Message();

		/*
		for(int i = 1; i <= 5; i++) {
			//Service.login(user);
			//Service.getAllMessages();
			//Service.createMessage(MainControl.user, msg);
			HttpUtil.sendPost("http://www.baidu.com", new JSONObject());
			System.out.println("第" + i + "次登录");
		}
		*/
	}
}
