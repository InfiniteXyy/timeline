package com.ecnu.testcourse.timeline.api;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.ecnu.testcourse.timeline.models.user.EncryptService;
import com.ecnu.testcourse.timeline.models.user.User;
import com.ecnu.testcourse.timeline.models.user.UserRepository;
import com.ecnu.testcourse.timeline.service.Auth;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
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

@WebMvcTest(CurrentUserApi.class)
@RunWith(SpringRunner.class)
public class CurrentUserApiTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private EncryptService encryptService;

  @MockBean
  private UserRepository userRepository;

  @MockBean
  private Auth auth;

  private User user;

  private Map<String, Object> createUpdateParam(String username, String email, String image,
      String password) {
    return new HashMap<String, Object>() {{
      put("user", new HashMap<String, Object>() {{
        put("username", username);
        put("email", email);
        put("image", image);
        put("password", password);
      }});
    }};
  }

  @Before
  public void setUp() throws Exception {
    RestAssuredMockMvc.mockMvc(mvc);
    user = new User("x@x.c", "x", "x", "");
  }

  @Test
  public void should_get_current_user_success() throws Exception {
    when(auth.authorize(anyString())).thenReturn(user);

    RestAssuredMockMvc.given()
        .header("Authorization", "bearer tokensample")
        .get("/api/user")
        .prettyPeek()
        .then()
        .statusCode(200)
        .body("user.token", equalTo("tokensample"))
        .body("user.username", equalTo("x"));

  }

  @Test
  public void should_get_current_user_failure() throws Exception {
    RestAssuredMockMvc.given()
        .header("Authorization", "bearer tokensample")
        .get("/api/user")
        .prettyPeek()
        .then()
        .statusCode(401)
        .body("errors.token", equalTo("unauthorized"));
  }

  @Test
  public void should_update_current_user_info_success() throws Exception {
    when(auth.authorize(anyString())).thenReturn(user);
    when(encryptService.encrypt(any())).thenReturn("password");

    RestAssuredMockMvc.given()
        .header("Authorization", "bearer tokensample")
        .contentType("application/json; charset=UTF-8")
        .body(createUpdateParam("xy", "x@x.c", "https://x.x", "123"))
        .put("/api/user")
        .prettyPeek()
        .then()
        .statusCode(200)
        .body("user.image", equalTo("https://x.x"));

    assertThat(user.getEmail(), equalTo("x@x.c"));
    assertThat(user.getImage(), equalTo("https://x.x"));
  }

  @Test
  public void should_update_current_user_email_failure_with_wrong_token() throws Exception {
    RestAssuredMockMvc.given()
        .header("Authorization", "bearer tokensample")
        .contentType("application/json; charset=UTF-8")
        .body(createUpdateParam("xy", "x@x.x", "", "123"))
        .put("/api/user")
        .prettyPeek()
        .then()
        .statusCode(401)
        .body("errors.token", equalTo("unauthorized"));
  }

  @Test
  public void should_update_current_user_email_failure_with_wrong_email() throws Exception {
    RestAssuredMockMvc.given()
        .header("Authorization", "bearer tokensample")
        .contentType("application/json; charset=UTF-8")
        .body(createUpdateParam("xy", "wrong email", "", "123"))
        .put("/api/user")
        .prettyPeek()
        .then()
        .statusCode(422)
        .body("errors.email", equalTo("should be an email"));
  }
}