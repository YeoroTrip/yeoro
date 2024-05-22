package com.yeoro.domain.chat.model.dto;

import com.yeoro.domain.chat.entity.Chat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatDto {
    public enum MessageType{
        ENTER, TALK, LEAVE;
    }
    private MessageType type;
    private String roomId;
    private String sender;
    private String message;
    private LocalDateTime time;

    public Chat toEntity(){
        return Chat.builder()
                .roomId(this.roomId)
                .type(this.type)
                .sender(this.sender)
                .message(this.message)
                .time(this.time)
                .build();
    }
}


