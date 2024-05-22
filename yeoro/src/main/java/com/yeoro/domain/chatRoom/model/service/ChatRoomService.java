package com.yeoro.domain.chatRoom.model.service;

import com.yeoro.domain.chatRoom.entity.ChatRoom;
import com.yeoro.domain.chatRoom.model.dto.ChatRoomDto;
import com.yeoro.domain.chatRoom.model.mapper.ChatRoomMapper;
import com.yeoro.domain.place.entity.Place;
import com.yeoro.domain.place.model.mapper.PlaceMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomMapper chatRoomMapper;
    private Map<String, ChatRoomDto> roomMap;

    @PostConstruct
    void init(){
        roomMap= new LinkedHashMap<>();
    }

    public void clearRoomMap(){
        roomMap = new LinkedHashMap<>();
    }

    public List<ChatRoomDto> getChatRoomList(){
        List<ChatRoomDto> list = chatRoomMapper.findAllRoom();
        return list;
    }

    /**
     * 구글 플레이스 아이디로 채팅방 조회
     * 해당 아이디로 개설된 채팅방이 없다면, 새로 생성하여 반환.
     * @param googleId
     * @return
     */
    @Transactional
    public ChatRoomDto getChatRoom(String googleId){
        ChatRoomDto room = chatRoomMapper.findRoomByGoogleId(googleId);

        // 해당 구글 플레이스 아이디로 개설된 채팅방이 없는 경우
        if(room == null){
            // 채팅방 개설
            int result = chatRoomMapper.insertRoom(ChatRoom.builder()
                    .id(UUID.randomUUID().toString())
                    .place(Place.builder().googleId(googleId).build())
                    .build());

            if(result != 1) return null;

            // 채팅방 반환
            room = chatRoomMapper.findRoomByGoogleId(googleId);
            roomMap.put(googleId, room);
            return room;
        }
        // 디비에 채팅 내역이 있고, 현재 채팅방이 열려있는 경우
        else if(roomMap.containsKey(googleId)){
            return roomMap.get(googleId);
        }
        // 디비에 채팅 내역이 있지만, 채팅방이 열리지 않은 경우
        else if(room != null) {
            roomMap.put(googleId, room);
            return room;
        }

        return null;
    }
}
