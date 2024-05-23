package com.yeoro.domain.chat.model.repository;

import com.yeoro.domain.chat.entity.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ChatRepository extends MongoRepository<Chat, String> {
    List<Chat> findTop50ByRoomIdAndTimeLessThanOrderByTimeAsc(String roomId, LocalDateTime time);
}
