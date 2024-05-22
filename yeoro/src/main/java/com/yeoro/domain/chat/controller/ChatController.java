package com.yeoro.domain.chat.controller;

import com.yeoro.domain.chat.entity.Chat;
import com.yeoro.domain.chat.model.dto.ChatDto;
import com.yeoro.domain.chat.model.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ChatController {

    private final static String CHAT_EXCHANGE_NAME = "chat.exchange";
    private final static String CHAT_QUEUE_NAME = "chat.queue";

    private final RabbitTemplate rabbitTemplate;
    private final ChatService chatService;

    @MessageMapping("chat.enter.{chatRoomId}")
    public void enterUser(@Payload ChatDto chatDto, @DestinationVariable String chatRoomId){
        chatDto.setTime(LocalDateTime.now());
        chatDto.setMessage(chatDto.getSender() + "님 입장!!");
        rabbitTemplate.convertAndSend(CHAT_EXCHANGE_NAME, "room."+chatRoomId, chatDto);
    }

    @MessageMapping("chat.message.{chatRoomId}")
    public void sendMessage(@Payload ChatDto chatDto, @DestinationVariable String chatRoomId){
        chatDto.setTime(LocalDateTime.now());
        chatDto.setMessage(chatDto.getMessage());
        rabbitTemplate.convertAndSend(CHAT_EXCHANGE_NAME, "room."+chatRoomId, chatDto);
    }

    @RabbitListener(queues=CHAT_QUEUE_NAME)
    public void receive(ChatDto chatDto){
        log.info("received: " + chatDto.getMessage() + "\tsender :" + chatDto.getSender());
        Chat chat = chatDto.toEntity();

        chatService.createChat(chat);
    }
}