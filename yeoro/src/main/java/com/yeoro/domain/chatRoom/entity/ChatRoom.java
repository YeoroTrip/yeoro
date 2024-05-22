package com.yeoro.domain.chatRoom.entity;


import com.yeoro.domain.chatRoom.model.dto.ChatRoomDto;
import com.yeoro.domain.place.entity.Place;
import com.yeoro.entityDefault.BaseTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ChatRoom extends BaseTime {

    @NonNull
    private String id;  // UUID
    private Place place;

    @Builder
    public ChatRoom(String id, Place place){
        this.id = id;   // UUID 값 전달
        this.place = place;
    }

    protected ChatRoom(){}

    public ChatRoomDto toDto(){
        return ChatRoomDto.builder()
                .roomId(this.id)
                .placeId(this.place.getId())
                .build();
    }
}
