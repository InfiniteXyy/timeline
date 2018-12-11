package com.ecnu.testcourse.Timeline.api;


import static com.ecnu.testcourse.Timeline.utils.JsonHelper.object;

import com.ecnu.testcourse.Timeline.models.user.EncryptService;
import com.ecnu.testcourse.Timeline.models.user.JwtService;
import com.ecnu.testcourse.Timeline.models.user.User;
import com.ecnu.testcourse.Timeline.models.user.UserRepository;
import com.ecnu.testcourse.Timeline.utils.ValidationHandler;
import com.fasterxml.jackson.annotation.JsonRootName;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/users")
public class UsersApi {

  private UserRepository userRepository;
  private EncryptService encryptService;
  private JwtService jwtService;

  @Autowired
  public UsersApi(
      UserRepository userRepository,
      EncryptService encryptService,
      JwtService jwtService) {
    this.userRepository = userRepository;
    this.encryptService = encryptService;
    this.jwtService = jwtService;
  }

  private Map<String, Object> renderAuthorizedUserObject(User user) {
    return new HashMap<String, Object>() {{
      put("user", new HashMap<String, Object>() {{
        put("email", user.getEmail());
        put("token", jwtService.toToken(user));
        put("username", user.getUsername());
        put("image", user.getImage());
      }});
    }};
  }

  @RequestMapping(path = "/", method = RequestMethod.POST)
  public ResponseEntity userRegister(@Valid @RequestBody RegisterParam registerParam,
      BindingResult bindingResult) {

    // 若格式错误则直接返回403
    if (bindingResult.hasErrors()) {
      return ResponseEntity.status(403).body(ValidationHandler.serialize(bindingResult));
    }

    Optional<User> optionalUser = userRepository.findByEmail(registerParam.getEmail());
    if (!optionalUser.isPresent()) {
      // 若没有该邮箱，则注册成功
      User user = new User(registerParam.getEmail(), registerParam.getUsername(),
          registerParam.getPassword(), "");
      userRepository.save(user);
      return ResponseEntity.ok(renderAuthorizedUserObject(user));
    } else {
      return ResponseEntity.status(401)
          .body(ValidationHandler.wrapRoot(object("email", "email has been used")));
    }
  }

  @RequestMapping(path = "/login", method = RequestMethod.POST)
  public ResponseEntity userLogin(@Valid @RequestBody LoginParam loginParam,
      BindingResult bindingResult) {
    // 若格式错误则直接返回403
    if (bindingResult.hasErrors()) {
      return ResponseEntity.status(403).body(ValidationHandler.serialize(bindingResult));
    }

    // 查看数据库中是否有该用户邮箱
    Optional<User> optionalUser = userRepository.findByEmail(loginParam.getEmail());
    if (optionalUser.isPresent() && encryptService
        .check(loginParam.getPassword(), optionalUser.get().getPassword())) {
      User user = optionalUser.get();
      // 得到该用户
      return ResponseEntity.ok(user);
    } else {
      // 密码或用户名错误
      return ResponseEntity.status(401)
          .body(ValidationHandler.wrapRoot(object("validation", "wrong username or password")));
    }
  }
}

@JsonRootName("user")
class LoginParam {

  @NotBlank(message = "can't be empty")
  @Email(message = "should be an email")
  private String email;
  @NotBlank(message = "can't be empty")
  private String password;

  public LoginParam() {
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }
}

@JsonRootName("user")
class RegisterParam {

  @NotBlank(message = "can't be empty")
  @Email(message = "should be an email")
  private String email;
  @NotBlank(message = "can't be empty")
  private String username;
  @NotBlank(message = "can't be empty")
  private String password;

  public RegisterParam() {
  }

  public String getEmail() {
    return email;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }
}