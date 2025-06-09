package com.chat.repository;

import com.chat.entity.Chat;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Query("SELECT c" +
            " FROM Chat c" +
            " JOIN FETCH c.member" +
            " WHERE c.chatRoom.id = :chatRoomId ORDER BY c.createdDate DESC")
    List<Chat> findLastChatBy(@Param("chatRoomId") Long chatRoomId, Pageable pageable);

    @Query("SELECT c" +
            " FROM Chat c" +
            " JOIN FETCH c.member" +
            " WHERE c.chatRoom.id = :chatRoomId ORDER BY c.createdDate ASC")
    List<Chat> findChatHistory(@Param("chatRoomId") Long chatRoomId);
}
