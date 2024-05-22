package com.yeoro.domain.chat.controller;

import com.yeoro.domain.chat.entity.Chat;
import com.yeoro.domain.chat.model.dto.ChatDto;
import com.yeoro.domain.chat.model.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "채팅 API", description = "여행지 관련 API")
public class ChatController {

    private final static String CHAT_EXCHANGE_NAME = "chat.exchange";
    private final static String CHAT_QUEUE_NAME = "chat.queue";

    private final RabbitTemplate rabbitTemplate;
    private final ChatService chatService;

    @Operation(summary = "채팅 입장 - 사용 안함", description = "채팅에 입장 멘트를 호출할 수 있습니다. 현재 사용하지 않습니다.")
    @MessageMapping("chat.enter.{chatRoomId}")
    public void enterUser(@Payload ChatDto chatDto, @DestinationVariable String chatRoomId){
        chatDto.setTime(LocalDateTime.now());
        chatDto.setMessage(chatDto.getSender() + "님 입장!!");
        rabbitTemplate.convertAndSend(CHAT_EXCHANGE_NAME, "room."+chatRoomId, chatDto);
    }

    @Operation(summary = "채팅 전송", description = "채팅 전송")
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