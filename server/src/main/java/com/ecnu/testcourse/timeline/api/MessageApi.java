package com.ecnu.testcourse.timeline.api;

import static com.ecnu.testcourse.timeline.utils.JsonHelper.object;

import com.ecnu.testcourse.timeline.api.request.NewMessageParam;
import com.ecnu.testcourse.timeline.api.response.MessageData;
import com.ecnu.testcourse.timeline.models.message.Message;
import com.ecnu.testcourse.timeline.models.message.MessageRepository;
import com.ecnu.testcourse.timeline.models.user.User;
import com.ecnu.testcourse.timeline.models.user.UserRepository;
import com.ecnu.testcourse.timeline.service.Auth;
import com.ecnu.testcourse.timeline.utils.ValidationHandler;
import java.util.List;
import javax.validation.Valid;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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

  @Autowired
  public MessageApi(MessageRepository messageRepository, UserRepository userRepository, Auth auth) {
    this.messageRepository = messageRepository;
    this.userRepository = userRepository;
    this.auth = auth;
  }

  @RequestMapping(method = RequestMethod.GET)
  public ResponseEntity listMessages(
      @RequestParam(value = "limit", defaultValue = "10") int limit,
      @RequestParam(value = "from", defaultValue = "") String from) {

    List<Message> messageList;
    if (!from.equals("")) {
      try {
        DateTime dt = DateTime.parse(from, ISODateTimeFormat.dateTimeParser());
        messageList = messageRepository.findTopMessagesSince(limit, dt.toString());

      } catch (Exception e) {
        return ResponseEntity.status(422)
            .body(ValidationHandler.wrapErrorRoot(object("time", "wrong format")));
      }
    } else {
      messageList = messageRepository.findTopMessages(limit);
    }

    return ResponseEntity.ok(object("messages",
        messageList.stream().map((message ->
            new MessageData(
                userRepository.findById(message.getUserId()).get(),
                message).getData())
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
      Message message = new Message(newMessageParam.getBody(), newMessageParam.getImageUrl(),
          user.getId());
      messageRepository.save(message);
      return ResponseEntity.ok(new MessageData(user, message).getWrappedData());
    } catch (Exception e) {
      return ResponseEntity.status(401)
          .body(ValidationHandler.wrapErrorRoot(object("token", "unauthorized")));
    }
  }
}

