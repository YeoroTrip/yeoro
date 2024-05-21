package com.yeoro.domain.chat.controller;

import com.yeoro.domain.chat.model.dto.ChatDto;
import com.yeoro.domain.chat.model.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ChatController {
    private final ChatRepository chatRepository;
    private final SimpMessageSendingOperations template;

    @MessageMapping("enterUser")
    public void enterUser(@Payload ChatDto chatDto, SimpMessageHeaderAccessor headerAccessor){
        chatRepository.plusUserCount(chatDto.getRoomId());
        String userUUID = chatRepository.addUser(chatDto.getRoomId(), chatDto.getSender());

        headerAccessor.getSessionAttributes().put("userUUID", userUUID);
        headerAccessor.getSessionAttributes().put("roomID", chatDto.getRoomId());

        chatDto.setMessage((chatDto.getSender() + "님 입장!!"));
        template.convertAndSend("/sub/chat/room/" + chatDto.getRoomId(), chatDto);
    }

    @MessageMapping("sendMessage")
    public void sendMessage(@Payload ChatDto chatDto){
        chatDto.setMessage(chatDto.getMessage());
        template.convertAndSend("/sub/chat/room/" + chatDto.getRoomId(), chatDto);
    }
}