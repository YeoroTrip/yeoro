package com.yeoro.domain.chat.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class ChatRoom {
    private String roomId;
    private String roomName;
    private Long userCount;
    private Map<String, String> userList = new HashMap<>();

    public ChatRoom create(String roomName) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomId = UUID.randomUUID().toString();
        chatRoom.roomName = roomName;
        chatRoom.userCount = 0l;

        return chatRoom;
    }
}
