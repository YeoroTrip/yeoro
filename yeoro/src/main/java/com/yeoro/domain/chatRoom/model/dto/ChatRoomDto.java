package com.yeoro.domain.chatRoom.model.dto;

import lombok.*;

import java.util.*;

@Getter
@Setter
@ToString
public class ChatRoomDto {
    private String roomId;      // 채팅방 아이디
    private Long placeId;       // 여행지 아이디
    private Long userCount;     // 채팅방 인원수
    private List<String> userList = new LinkedList<>(); // 유저 목록

    @Builder
    public ChatRoomDto(String roomId, Long placeId) {
        this.roomId = roomId;
        this.placeId = placeId;
        this.userCount = 0l;
    }
}
