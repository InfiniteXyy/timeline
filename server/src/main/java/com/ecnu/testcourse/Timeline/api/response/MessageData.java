package com.ecnu.testcourse.Timeline.api.response;

import com.ecnu.testcourse.Timeline.models.message.Message;
import com.ecnu.testcourse.Timeline.models.user.User;
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
      put("author", new HashMap<String, Object>() {{
        put("username", author.getUsername());
        put("image", author.getImage());
      }});
    }};
  }
}
