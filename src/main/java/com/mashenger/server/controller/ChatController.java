package com.mashenger.server.controller;

import com.mashenger.server.entity.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping(value="/chat.send")
    @SendTo(value="/topic/public")
    public Message send(
            @Payload Message message) {
        return message;
    }

    @MessageMapping(value="/chat.join")
    @SendTo(value="/topic/public")
    public Message join(
            @Payload Message message, SimpMessageHeaderAccessor simpMessageHeaderAccessor
    ) {
        simpMessageHeaderAccessor.getSessionAttributes().put("username", message.getUsername());
        message.setContent(message.getUsername() + " 접속");
        return message;
    }
}
