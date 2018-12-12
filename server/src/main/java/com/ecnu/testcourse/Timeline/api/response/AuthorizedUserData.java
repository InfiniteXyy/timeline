package com.ecnu.testcourse.Timeline.api.response;

import com.ecnu.testcourse.Timeline.models.user.User;
import java.util.HashMap;
import java.util.Map;

public class AuthorizedUserData {

  private User user;
  private String token;

  public AuthorizedUserData(User user, String token) {
    this.user = user;
    this.token = token;
  }

  public Map<String, Object> getUserData() {
    return new HashMap<String, Object>() {{
      put("user", new HashMap<String, Object>() {{
        put("email", user.getEmail());
        put("token", token);
        put("username", user.getUsername());
        put("image", user.getImage());
      }});
    }};
  }
}
