package com.yeoro.domain.chat.model.service;

import com.yeoro.domain.chat.entity.Chat;
import com.yeoro.domain.chat.model.dto.ChatDto;
import com.yeoro.domain.chat.model.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {
    private final ChatRepository chatRepository;

    @Transactional
    public List<ChatDto> getChatList(String roomId){
        List<Chat> chatList = chatRepository.findTop50ByRoomIdAndTimeLessThanOrderByTimeAsc(roomId, LocalDateTime.now());
        List<ChatDto> chatDtos = chatList.stream().map(chat -> chat.toDto()).collect(Collectors.toList());

        return chatDtos;
    }

    @Transactional
    public boolean createChat(Chat chat){
        chat = chatRepository.save(chat);

        return chat!=null? true: false;
    }
}
