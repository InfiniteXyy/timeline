package com.ecnu.testcourse.Timeline.api;

import static com.ecnu.testcourse.Timeline.utils.JsonHelper.object;

import com.ecnu.testcourse.Timeline.api.response.MessageData;
import com.ecnu.testcourse.Timeline.models.message.Message;
import com.ecnu.testcourse.Timeline.models.message.MessageRepository;
import com.ecnu.testcourse.Timeline.models.user.User;
import com.ecnu.testcourse.Timeline.models.user.UserRepository;
import com.ecnu.testcourse.Timeline.service.Auth;
import com.ecnu.testcourse.Timeline.utils.ValidationHandler;
import com.fasterxml.jackson.annotation.JsonRootName;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/messages")
public class MessageApi {

  private MessageRepository messageRepository;
  private UserRepository userRepository;
  private Auth auth;

  private Message checkMessageEditable(User user, String id) throws IllegalArgumentException {
    Long messageId = Long.valueOf(id);
    Optional<Message> message = messageRepository.findById(messageId);
    if (!message.isPresent() || message.get().getUserId() != user.getId()) {
      throw new IllegalArgumentException();
    }
    return message.get();
  }

  @Autowired
  public MessageApi(MessageRepository messageRepository, UserRepository userRepository, Auth auth) {
    this.messageRepository = messageRepository;
    this.userRepository = userRepository;
    this.auth = auth;
  }


  @RequestMapping(method = RequestMethod.GET)
  public ResponseEntity listMessages(
      @RequestParam(value = "offset", defaultValue = "0") int offset,
      @RequestParam(value = "limit", defaultValue = "20") int limit) {
    return ResponseEntity.ok(object("messages", messageRepository.findAll().stream().map(
        (message -> new MessageData(userRepository.findById(message.getUserId()).get(), message)
            .getData())
    )));
  }

  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity createMessage(
      @RequestHeader(value = "Authorization") String authorization,
      @Valid @RequestBody NewMessageParam newMessageParam,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ResponseEntity.status(422).body(ValidationHandler.serialize(bindingResult));
    }
    try {
      User user = auth.authorize(authorization);
      Message message = new Message(newMessageParam.getBody(), user.getId());
      messageRepository.save(message);
      return ResponseEntity.ok(object("message", message));
    } catch (Exception e) {
      return ResponseEntity.status(401)
          .body(ValidationHandler.wrapErrorRoot(object("token", "unauthorized")));
    }
  }

  @RequestMapping(path = "{id}", method = RequestMethod.PUT)
  public ResponseEntity updateMessage(
      @RequestHeader(value = "Authorization") String authorization,
      @Valid @RequestBody NewMessageParam newMessageParam,
      @PathVariable("id") String id,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ResponseEntity.status(422).body(ValidationHandler.serialize(bindingResult));
    }
    try {
      User user = auth.authorize(authorization);
      Message message = checkMessageEditable(user, id);
      message.update(newMessageParam.getBody());
      messageRepository.save(message);
      return ResponseEntity.ok(new MessageData(user, message).getData());
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(403)
          .body(ValidationHandler
              .wrapErrorRoot(object("id", "no message or you are not authorized")));
    } catch (Exception e) {
      return ResponseEntity.status(401)
          .body(ValidationHandler.wrapErrorRoot(object("token", "unauthorized")));
    }
  }

  @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
  public ResponseEntity deleteMessage(
      @RequestHeader(value = "Authorization") String authorization,
      @PathVariable("id") String id) {
    try {
      User user = auth.authorize(authorization);
      Message message = checkMessageEditable(user, id);
      messageRepository.deleteById(message.getId());
      return ResponseEntity.ok(object("message", object("deleted", "true")));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(403)
          .body(ValidationHandler
              .wrapErrorRoot(object("id", "no message or you are not authorized")));
    } catch (Exception e) {
      return ResponseEntity.status(401)
          .body(ValidationHandler.wrapErrorRoot(object("token", "unauthorized")));
    }
  }
}

@JsonRootName("message")
class NewMessageParam {

  @NotBlank(message = "can't be empty")
  private String body;

  public String getBody() {
    return body;
  }
}
