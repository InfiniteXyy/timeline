package com.ecnu.testcourse.Timeline.api;

import static com.ecnu.testcourse.Timeline.utils.JsonHelper.object;

import com.ecnu.testcourse.Timeline.models.message.Message;
import com.ecnu.testcourse.Timeline.models.message.MessageRepository;
import com.ecnu.testcourse.Timeline.utils.ValidationHandler;
import com.fasterxml.jackson.annotation.JsonRootName;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/messages")
public class MessageApi {

  private MessageRepository messageRepository;


  @Autowired
  public MessageApi(MessageRepository messageRepository) {
    this.messageRepository = messageRepository;
  }


  @RequestMapping(method = RequestMethod.GET)
  public ResponseEntity getMessages() {
    return ResponseEntity.ok(object("messages", messageRepository.findAll()));
  }

  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity createMessage(@Valid @RequestBody NewMessageParam newMessageParam,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ResponseEntity.status(422).body(ValidationHandler.serialize(bindingResult));
    }
    Message message = new Message(newMessageParam.getBody());
    messageRepository.save(message);
    return ResponseEntity.ok(object("message", message));
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
