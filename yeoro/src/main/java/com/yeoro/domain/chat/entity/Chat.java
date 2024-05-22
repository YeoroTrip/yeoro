package com.yeoro.domain.chat.entity;

import com.yeoro.domain.chat.model.dto.ChatDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Document(collection = "chat")
public class Chat {

    @Id
    private String id;
    private ChatDto.MessageType type;
    private String roomId;
    private String sender;
    private String message;
    private LocalDateTime time;

    public ChatDto toDto(){
        return ChatDto.builder()
                .roomId(this.roomId)
                .type(this.type)
                .sender(this.sender)
                .message(this.message)
                .time(this.time)
                .build();
    }
}
