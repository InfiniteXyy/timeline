package com.ecnu.testcourse.Timeline.api;

import com.ecnu.testcourse.Timeline.api.exception.InvalidRequestException;
import com.ecnu.testcourse.Timeline.models.message.Message;
import com.ecnu.testcourse.Timeline.models.message.MessageRepository;
import com.fasterxml.jackson.annotation.JsonRootName;
import java.util.HashMap;
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

    return ResponseEntity.ok(new HashMap<String, Object>() {{
      put("messages", messageRepository.findAll());
    }});
  }

  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity createMessage(@Valid @RequestBody NewMessageParam newMessageParam,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      throw new InvalidRequestException(bindingResult);
    }
    Message message = new Message(newMessageParam.getBody());
    messageRepository.save(message);
    return ResponseEntity.ok(new HashMap<String, Object>() {{
      put("message", message);
    }});
  }
}

@JsonRootName("message")
class NewMessageParam {

  @NotBlank(message = "message body can't be empty")
  private String body;

  public String getBody() {
    return body;
  }
}
