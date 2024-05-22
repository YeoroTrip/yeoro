package com.yeoro.domain.chatRoom.model.mapper;

import com.yeoro.domain.chatRoom.entity.ChatRoom;
import com.yeoro.domain.chatRoom.model.dto.ChatRoomDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatRoomMapper {

    // 전체 채팅방 조회
    List<ChatRoomDto> findAllRoom();

    // 채팅방 조회: 여행지 아이디로 조회
    ChatRoomDto findRoomByPlaceId(Long id);

    // 채팅방 조회: 구글 플레이스 아이디로 조회
    ChatRoomDto findRoomByGoogleId(String googleId);

    // 채팅방 조회: 채팅방 아이디(UUID)로 조회
    ChatRoomDto findRoomById(String roomId);

    // 채팅방 등록
    int insertRoom(ChatRoom chatRoom);
}

