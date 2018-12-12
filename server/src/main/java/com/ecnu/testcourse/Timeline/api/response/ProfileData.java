package com.ecnu.testcourse.Timeline.api.response;

import com.ecnu.testcourse.Timeline.models.user.User;
import java.util.HashMap;
import java.util.Map;

public class ProfileData {

  private User user;

  public ProfileData(User user) {
    this.user = user;
  }

  public Map<String, Object> getUserData() {
    return new HashMap<String, Object>() {{
      put("user", new HashMap<String, Object>() {{
        put("username", user.getUsername());
        put("image", user.getImage());
      }});
    }};
  }
}
