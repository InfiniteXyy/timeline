package com.ecnu.testcourse.timeline.api;


import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.ecnu.testcourse.timeline.models.user.EncryptService;
import com.ecnu.testcourse.timeline.models.user.JwtService;
import com.ecnu.testcourse.timeline.models.user.User;
import com.ecnu.testcourse.timeline.models.user.UserRepository;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UsersApi.class)
@RunWith(SpringRunner.class)
public class UsersApiTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private UserRepository userRepository;

  @MockBean
  private EncryptService encryptService;

  @MockBean
  private JwtService jwtService;

  private User user;
  private final String email = "email@email.com";
  private final String password = "123";
  private final String username = "xyy";

  private Map<String, Object> renderRegisterParam(String username, String email) {
    return new HashMap<String, Object>() {{
      put("user", new HashMap<String, Object>() {{
        put("username", username);
        put("email", email);
        put("password", "123");
      }});
    }};
  }

  @Before
  public void setUp() throws Exception {
    user = new User(email, username, password, "");
    RestAssuredMockMvc.mockMvc(mvc);
  }

  @Test
  public void should_login_success() throws Exception {
    when(jwtService.toToken(any())).thenReturn("tokenhere");
    when(encryptService.check(anyString(), anyString())).thenReturn(true);
    when(userRepository.findByEmail(eq(email))).thenReturn(Optional.of(user));
    when(jwtService.toToken(any())).thenReturn("tokenhere");

    Map<String, Object> loginParam = new HashMap<String, Object>() {{
      put("user", new HashMap<String, Object>() {{
        put("email", email);
        put("password", password);
      }});
    }};

    given()
        .contentType("application/json; charset=UTF-8")
        .body(loginParam)
        .when()
        .post("/api/users/login")
        .prettyPeek()
        .then()
        .statusCode(200)
        .body("user.email", equalTo(email))
        .body("user.image", equalTo(""))
        .body("user.username", equalTo(username))
        .body("user.token", equalTo("tokenhere"));
  }

  @Test
  public void should_login_failure_when_wrong_password() throws Exception {
    when(encryptService.check(anyString(), anyString())).thenReturn(false);
    when(userRepository.findByEmail(eq(email))).thenReturn(Optional.of(user));
    Map<String, Object> loginParam = new HashMap<String, Object>() {{
      put("user", new HashMap<String, Object>() {{
        put("email", email);
        put("password", "321");
      }});
    }};
    given()
        .contentType("application/json; charset=UTF-8")
        .body(loginParam)
        .when()
        .post("/api/users/login")
        .prettyPeek()
        .then()
        .statusCode(401)
        .body("errors.validation", equalTo("wrong email or password"));
  }

  @Test
  public void should_login_failure_when_wrong_email() throws Exception {
    when(userRepository.findByEmail(eq(email))).thenReturn(Optional.empty());
    Map<String, Object> loginParam = new HashMap<String, Object>() {{
      put("user", new HashMap<String, Object>() {{
        put("email", "wrongemail@email.com");
        put("password", password);
      }});
    }};
    given()
        .contentType("application/json; charset=UTF-8")
        .body(loginParam)
        .when()
        .post("/api/users/login")
        .prettyPeek()
        .then()
        .statusCode(401)
        .body("errors.validation", equalTo("wrong email or password"));
  }

  @Test
  public void should_login_failure_when_password_empty() throws Exception {
    Map<String, Object> loginParam = new HashMap<String, Object>() {{
      put("user", new HashMap<String, Object>() {{
        put("email", "email@email.com");
        put("password", "");
      }});
    }};
    given()
        .contentType("application/json; charset=UTF-8")
        .body(loginParam)
        .when()
        .post("/api/users/login")
        .prettyPeek()
        .then()
        .statusCode(422)
        .body("errors.password", equalTo("can't be empty"));
  }

  @Test
  public void should_register_success() throws Exception {
    when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
    when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
    when(userRepository.findById(any())).thenReturn(Optional.of(user));
    when(jwtService.toToken(any())).thenReturn("tokenhere");

    given()
        .contentType("application/json; charset=UTF-8")
        .body(renderRegisterParam(username, email))
        .when()
        .post("/api/users/")
        .prettyPeek()
        .then()
        .statusCode(200)
        .body("user.email", equalTo(email))
        .body("user.username", equalTo(username))
        .body("user.image", equalTo(""))
        .body("user.token", equalTo("tokenhere"));
  }

  @Test
  public void should_register_failure_for_duplicate_username() throws Exception {
    when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
    when(userRepository.findByUsername(eq("xz"))).thenReturn(Optional.of(new User(
        "x@x.x", "xz", "123", ""
    )));
    given()
        .contentType("application/json; charset=UTF-8")
        .body(renderRegisterParam("xz", email))
        .when()
        .post("/api/users/")
        .prettyPeek()
        .then()
        .statusCode(401)
        .body("errors.username", equalTo("has been used"));
  }

  @Test
  public void should_register_failure_for_duplicate_email() throws Exception {
    when(userRepository.findByEmail(eq("x@x.x"))).thenReturn(Optional.of(new User(
        "x@x.x", "happy", "123", ""
    )));
    when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
    given()
        .contentType("application/json; charset=UTF-8")
        .body(renderRegisterParam("xz", "x@x.x"))
        .when()
        .post("/api/users/")
        .prettyPeek()
        .then()
        .statusCode(401)
        .body("errors.email", equalTo("has been used"));
  }

  @Test
  public void should_register_failure_for_invalid_email() throws Exception {
    given()
        .contentType("application/json; charset=UTF-8")
        .body(renderRegisterParam("xz", "x"))
        .when()
        .post("/api/users/")
        .prettyPeek()
        .then()
        .statusCode(422)
        .body("errors.email", equalTo("should be an email"));
  }

  @Test
  public void should_register_failure_for_blank_email() throws Exception {
    given()
        .contentType("application/json; charset=UTF-8")
        .body(renderRegisterParam("xz", ""))
        .when()
        .post("/api/users/")
        .prettyPeek()
        .then()
        .statusCode(422)
        .body("errors.email", equalTo("can't be empty"));
  }

  @Test
  public void should_register_failure_for_blank_username() throws Exception {
    given()
        .contentType("application/json; charset=UTF-8")
        .body(renderRegisterParam("", "x@xtt.c"))
        .when()
        .post("/api/users/")
        .prettyPeek()
        .then()
        .statusCode(422)
        .body("errors.username", equalTo("can't be empty"));
  }
}