package com.yeoro.domain.chat.model.dto;

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
}


