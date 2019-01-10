package com.ecnu.testcourse.timeline.service;

import static org.junit.Assert.assertEquals;

import com.ecnu.testcourse.timeline.models.user.User;
import org.junit.Before;
import org.junit.Test;

public class SimpleJwtImplTest {

  private SimpleJwtImpl jwtService;

  private User user;

  @Before
  public void setUp() {
    jwtService = new SimpleJwtImpl();
    user = new User("xyy@xyy.x", "xyy", "123", "");
  }

  @Test
  public void should_token_return_correct() throws Exception {
    String token = jwtService.toToken(user);

    String value = jwtService.toValue(token);

    assertEquals(String.valueOf(user.getId()), value);

  }
}