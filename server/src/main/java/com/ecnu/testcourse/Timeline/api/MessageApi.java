package com.ecnu.testcourse.Timeline.api;

import com.ecnu.testcourse.Timeline.models.message.Message;
import com.ecnu.testcourse.Timeline.models.message.MessageRepository;
import com.fasterxml.jackson.annotation.JsonRootName;
import java.util.HashMap;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
      put("data", messageRepository.findAll());
    }});
  }

  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity createMessage(@Valid @RequestBody NewMessageParam newMessageParam) {
    Message message = new Message(newMessageParam.getContent());
    messageRepository.save(message);
    return ResponseEntity.ok(new HashMap<String, Object>() {{
      put("message", message);
    }});
  }


}

@JsonRootName("message")
class NewMessageParam {

  @NotBlank(message = "message content can't be empty")
  private String content;

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
}
