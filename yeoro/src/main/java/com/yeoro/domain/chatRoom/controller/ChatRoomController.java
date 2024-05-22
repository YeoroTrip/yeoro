package com.yeoro.domain.chatRoom.controller;


import com.yeoro.domain.chatRoom.model.dto.ChatRoomDto;
import com.yeoro.domain.chatRoom.model.service.ChatRoomService;
import com.yeoro.global.result.ResultCode;
import com.yeoro.global.result.ResultResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/chatroom")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

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
}
