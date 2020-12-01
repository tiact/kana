package com.tiact.websocket.controller;

import com.tiact.websocket.model.Chat;
import com.tiact.websocket.model.TiaResult;
import com.tiact.websocket.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Tia_ct
 */
@Controller
@CrossOrigin
public class ChatController {

    @Autowired
    ChatService service;

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public Chat sendMessage(@Payload Chat chat) {
        System.out.println(chat.toString());
        return chat;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public Chat addUser(@Payload Chat chat,
                        SimpMessageHeaderAccessor headerAccessor) {
        System.out.println(chat.toString());
        headerAccessor.getSessionAttributes().put("username", chat.getSender());
        return chat;
    }

    @GetMapping(value = "send")
    @ResponseBody
    public TiaResult<Chat> send(@Payload Chat chat) {
        chat = service.send(chat);
        return new TiaResult<>(200,"发送", chat);
    }

}
