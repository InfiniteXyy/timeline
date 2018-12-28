package service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import control.MainControl;
import entity.Message;
import jdbc.DBAccess;

public class DBService {
	private static DBAccess db = new DBAccess();
	private static ResultSet rs = null;
	
	public static boolean isLogined() {
		boolean b = false;
		if(MainControl.user == null) {
			return false;
		}
		if(db.createConn()) {
			db.query("select * from user where email = " + MainControl.user.getEmail() + " and password = " + MainControl.user.getToken());
			if(db.next()) {
				b = true;
			}
		}
		return false;
	}
	
	public static List<Message> getAllMessages() {
		List <Message> messageList = new ArrayList();
		if(db.createConn()) {
			db.query("select * from message join user on (message.user_id = user.id) order by created_at desc limit 10");
			rs = db.getRs();
			try {
				while(rs.next()) {
					String createdAt = rs.getString("created_at");
					int id = rs.getInt("id");
					String body = rs.getString("body");
					String updatedAt = rs.getString("updated_at");
					String imageUrl = rs.getString("image_url");
					String image = rs.getString("image");
					String username = rs.getString("username");
					messageList.add(new Message(createdAt, id, body, updatedAt, image, username, imageUrl));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return messageList;
	}
	
	public static List<Message> getMessagesFrom(int limit, String timeFrom) {
		List <Message> messageList = new ArrayList();
		if(db.createConn()) {
			db.query("select * from message join user on (message.user_id = user.id) where created_at <= " + timeFrom + " order by created_at desc limit " + limit);
			rs = db.getRs();
			try {
				while(rs.next()) {
					String createdAt = rs.getString("created_at");
					int id = rs.getInt("id");
					String body = rs.getString("body");
					String updatedAt = rs.getString("updated_at");
					String imageUrl = rs.getString("image_url");
					String image = rs.getString("image");
					String username = rs.getString("username");
					messageList.add(new Message(createdAt, id, body, updatedAt, image, username, imageUrl));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return messageList;
	}
}
