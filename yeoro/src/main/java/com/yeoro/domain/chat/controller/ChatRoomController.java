package com.yeoro.domain.chat.controller;


import com.yeoro.domain.chat.model.dto.ChatRoom;
import com.yeoro.domain.chat.model.repository.ChatRepository;
import com.yeoro.global.result.ResultCode;
import com.yeoro.global.result.ResultResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/chatroom")
public class ChatRoomController {

    private final ChatRepository chatRepository;

    // 채팅 리스트 화면
    @GetMapping("/")
    public ResponseEntity<ResultResponse> goChatRoom(){
        List<ChatRoom> chatRooms = chatRepository.findAllRoom();

        return ResponseEntity.ok(ResultResponse.of(ResultCode.CREATE_POST_SUCCESS, chatRooms));
    }

    // 채팅방 생성
    @PostMapping("/room")
    public ResponseEntity<ResultResponse> createRoom(@RequestParam String name){
        ChatRoom chatRoom = chatRepository.createChatRooms(name);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.CREATE_POST_SUCCESS, chatRoom.getRoomId()));
    }

    // 채팅에 참여한 유저 리스트 반환
    @GetMapping("userList")
    public ArrayList<String> userList(String roomId){
        return chatRepository.getUserList(roomId);
    }

}
