package com.ecnu.testcourse.timeline.api.response;

import com.ecnu.testcourse.timeline.models.message.Message;
import com.ecnu.testcourse.timeline.models.user.User;
import java.util.HashMap;
import java.util.Map;

public class MessageData {

  private User author;
  private Message message;

  public MessageData(User author, Message message) {
    this.author = author;
    this.message = message;
  }

  public Map<String, Object> getData() {
    return new HashMap<String, Object>() {{
      put("id", message.getId());
      put("body", message.getBody());
      put("createdAt", message.getCreatedAt());
      put("updatedAt", message.getUpdatedAt());
      put("imageUrl", message.getImageUrl());
      put("author", new HashMap<String, Object>() {{
        put("username", author.getUsername());
        put("image", author.getImage());
      }});
    }};
  }

  public Map<String, Object> getWrappedData() {
    return new HashMap<String, Object>() {{
      put("message", getData());
    }};
  }
}
