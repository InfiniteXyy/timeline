package com.ecnu.testcourse.timeline.api;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.ecnu.testcourse.timeline.models.message.Message;
import com.ecnu.testcourse.timeline.models.message.MessageRepository;
import com.ecnu.testcourse.timeline.models.user.User;
import com.ecnu.testcourse.timeline.models.user.UserRepository;
import com.ecnu.testcourse.timeline.service.Auth;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(MessageApi.class)
@RunWith(SpringRunner.class)
public class MessageApiTest {

  @MockBean
  private MessageRepository messageRepository;
  @MockBean
  private UserRepository userRepository;
  @MockBean
  private Auth auth;

  private User demoUser;


  @Autowired
  private MockMvc mvc;

  @Before
  public void setUp() throws Exception {
    RestAssuredMockMvc.mockMvc(mvc);
    demoUser = new User("x@x.x", "x", "x", "");
  }

  @Test
  public void should_get_message_list_success() throws Exception {
    when(messageRepository.findTopMessages(anyInt())).thenReturn(Collections.singletonList(
        new Message("haha", "", demoUser.getId())
    ));
    when(userRepository.findByIdIn(Collections.singletonList(demoUser.getId()))).thenReturn(
        Collections.singletonList(demoUser));

    RestAssuredMockMvc.when()
        .get("/api/messages")
        .prettyPeek()
        .then()
        .statusCode(200)
        .body("messages[0].author.username", equalTo(demoUser.getUsername()))
        .body("messages[0].body", equalTo("haha"));
  }

  @Test
  public void should_get_message_list_with_limit() throws Exception {
    int limit = 10;
    when(messageRepository.findTopMessages(limit)).thenReturn(Collections.nCopies(limit,
        new Message("haha", "", demoUser.getId())
    ));
    when(userRepository.findByIdIn(anyList())).thenReturn(
        Collections.singletonList(demoUser));
    RestAssuredMockMvc.when()
        .get("/api/messages?limit={limit}", limit)
        .prettyPeek()
        .then()
        .statusCode(200)
        .body("messages.size()", is(limit));
  }

  @Test
  public void should_get_message_list_success_with_offset() throws Exception {
    String offset = "2018-12-18T15:17:16.232+08:00";
    when(messageRepository.findTopMessagesSince(anyInt(), anyString()))
        .thenReturn(Collections.singletonList(
            new Message("haha", "", demoUser.getId())
        ));
    when(userRepository.findByIdIn(Collections.singletonList(demoUser.getId()))).thenReturn(
        Collections.singletonList(demoUser));
    RestAssuredMockMvc.when()
        .get("/api/messages?from={offset}", offset)
        .prettyPeek()
        .then()
        .statusCode(200);
  }

  @Test
  public void should_get_message_list_failure_with_offset() throws Exception {
    String offset = "xxxx-12-18T15:17:16.232+08:00";
    RestAssuredMockMvc.when()
        .get("/api/messages?from={offset}", offset)
        .prettyPeek()
        .then()
        .statusCode(422)
        .body("errors.time", equalTo("wrong format"));
  }

  @Test
  public void should_add_new_message_success() throws Exception {
    when(auth.authorize("bearer token")).thenReturn(demoUser);

    Map<String, Object> requestParam = new HashMap<String, Object>() {{
      put("message", new HashMap<String, Object>() {{
        put("body", "123");
      }});
    }};
    RestAssuredMockMvc.given()
        .header("Authorization", "bearer token")
        .contentType("application/json; charset=UTF-8")
        .body(requestParam)
        .post("/api/messages")
        .prettyPeek()
        .then()
        .statusCode(200)
        .body("message.author.username", equalTo(demoUser.getUsername()))
        .body("message.body", equalTo("123"));
  }

  @Test
  public void should_add_new_message_failure_with_wrong_token() throws Exception {
    Map<String, Object> requestParam = new HashMap<String, Object>() {{
      put("message", new HashMap<String, Object>() {{
        put("body", "123");
      }});
    }};
    RestAssuredMockMvc.given()
        .header("Authorization", "bearer token")
        .contentType("application/json; charset=UTF-8")
        .body(requestParam)
        .post("/api/messages")
        .prettyPeek()
        .then()
        .statusCode(401)
        .body("errors.token", equalTo("unauthorized"));
  }

  @Test
  public void should_add_new_message_failure_with_empty_body() throws Exception {
    Map<String, Object> requestParam = new HashMap<String, Object>() {{
      put("message", new HashMap<String, Object>() {{
        put("body", "");
      }});
    }};
    RestAssuredMockMvc.given()
        .header("Authorization", "bearer token")
        .contentType("application/json; charset=UTF-8")
        .body(requestParam)
        .post("/api/messages")
        .prettyPeek()
        .then()
        .statusCode(422)
        .body("errors.body", equalTo("can't be empty"));
  }
}