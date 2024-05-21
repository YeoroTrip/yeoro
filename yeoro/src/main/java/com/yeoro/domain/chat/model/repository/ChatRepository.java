package com.yeoro.domain.chat.model.repository;

import com.yeoro.domain.chat.model.dto.ChatRoom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Slf4j
public class ChatRepository {

    private Map<String, ChatRoom> chatRoomMap = new LinkedHashMap<>();

    // 전체 채팅방 조회
    public List<ChatRoom> findAllRoom(){
        List chatRooms = new ArrayList<>(chatRoomMap.values());
        Collections.reverse(chatRooms);

        return chatRooms;
    }

    // roomID 기준 채팅방 조회
    public ChatRoom findRoomById(String roomId){
        return chatRoomMap.get(roomId);
    }

    // roomName으로 채팅방 생성
    public ChatRoom createChatRooms(String roomName){
        ChatRoom chatRoom = new ChatRoom().create(roomName);
        chatRoomMap.put(chatRoom.getRoomId(), chatRoom);

        return chatRoom;
    }

    // 채팅방 유저 인원+1
    public void plusUserCount(String roomId){
        ChatRoom chatRoom = chatRoomMap.get(roomId);
        chatRoom.setUserCount(chatRoom.getUserCount()+1);
    }

    // 채팅방 유저 인원-1
    public void minusUserCount(String roomId){
        ChatRoom chatRoom = chatRoomMap.get(roomId);
        chatRoom.setUserCount(Math.min(chatRoom.getUserCount()-1, 0));
    }

    // 채팅방 유저 추가
    public String addUser(String roomId, String userName){
        ChatRoom chatRoom = chatRoomMap.get(roomId);
        String userUUID = UUID.randomUUID().toString();

        chatRoom.getUserList().put(userUUID, userName);

        return userUUID;
    }

    // 채팅방 유저 삭제
    public void deleteUser(String roomId, String userUUID){
        ChatRoom chatRoom = chatRoomMap.get(roomId);
        chatRoom.getUserList().remove(userUUID);
    }

    // 채팅방 userName 조회
    public String getUserName(String roomId, String userUUID){
        ChatRoom chatRoom = chatRoomMap.get(roomId);
        return chatRoom.getUserList().get(userUUID);
    }

    //채팅방 전체 userList 조회
    public ArrayList<String> getUserList(String roomId){
        ArrayList<String> list = new ArrayList<>();
        ChatRoom chatRoom = chatRoomMap.get(roomId);

        chatRoom.getUserList().forEach((key, val) ->list.add(val));
        return list;
    }
}
