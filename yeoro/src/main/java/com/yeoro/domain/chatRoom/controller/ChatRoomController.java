package com.yeoro.domain.chatRoom.controller;


import com.yeoro.domain.chat.model.dto.ChatDto;
import com.yeoro.domain.chat.model.service.ChatService;
import com.yeoro.domain.chatRoom.model.dto.ChatRoomDto;
import com.yeoro.domain.chatRoom.model.service.ChatRoomService;
import com.yeoro.global.result.ResultCode;
import com.yeoro.global.result.ResultResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/chatroom")
@Tag(name = "채팅 API", description = "여행지 관련 API")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final ChatService chatService;

    // 채팅 리스트 화면
    @GetMapping("/list")
    public ResponseEntity<ResultResponse> goChatRoom(){
        List<ChatRoomDto> chatRoomDtos = chatRoomService.getChatRoomList();
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_JOIN_ROOMS_SUCCESS, chatRoomDtos));
    }

    // 채팅방 생성
    @PostMapping("/room")
    public ResponseEntity<ResultResponse> createRoom(@RequestParam String googleId){
        ChatRoomDto chatRoomDto = chatRoomService.getChatRoom(googleId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.CREATE_POST_SUCCESS, chatRoomDto.getRoomId()));
    }

    // 채팅방 채팅내용 불러오기
    @GetMapping("/chatList")
    public ResponseEntity<ResultResponse> getChatList(String roomId){
        List<ChatDto> chats = chatService.getChatList(roomId);

        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_CHAT_MESSAGES_SUCCESS, chats));
    }
}
