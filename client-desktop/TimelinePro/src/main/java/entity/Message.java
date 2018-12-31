package entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;
public class Message {
	private String createdAt = null;
	private int id;
	private String body = null;
	private String updatedAt = null;
	private Author author = null;
	private String imageUrl = null;
	
	public Message() {
		
	}
	
	
	public Message(String createdAt, int id, String body, String updatedAt, String image, String username, String imageUrl) {
		this.createdAt = createdAt;
		this.id = id;
		this.body = body;
		this.updatedAt = updatedAt;
		this.imageUrl = imageUrl;
		this.author = new Author(image, username);
	}

	

	public String getImageUrl() {
		return imageUrl;
	}


	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}


	public String getCreatedAt() {
		return createdAt;
	}



	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getBody() {
		return body;
	}



	public void setBody(String body) {
		this.body = body;
	}



	public String getUpdatedAt() {
		return updatedAt;
	}



	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}



	public Author getAuthor() {
		return author;
	}



	public void setAuthor(Author author) {
		this.author = author;
	}
	
	public String getTimeString() {
		if(this.createdAt != null) {
			DateTime dt = DateTime.parse(this.createdAt);
			Date d = dt.toDate();
			long timestamp = d.getTime();
			long nowTimestamp = new Date().getTime();
			long offset = (nowTimestamp - timestamp) / 1000;
			if(offset <= 60) {
				return "1分钟前";
			}
			long minutes = offset / 60;
			if(minutes < 60) {
				return "" + minutes + "分钟前";
			}
			long hours = minutes / 60;
			if(hours < 24) {
				return "" + hours + "小时前";
			}
			long days = hours / 24;
			if(days < 3) {
				return "" + days + "天前";
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			return sdf.format(d);
		}
		return "";
	}



	public class Author {
		private String image = null;
		private String username = null;
		
		public Author(String image, String username) {
			this.image = image;
			this.username = username;
		}

		public String getImage() {
			return image;
		}

		public void setImage(String image) {
			this.image = image;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}
	}
}
