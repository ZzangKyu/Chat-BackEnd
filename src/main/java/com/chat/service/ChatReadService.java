package com.chat.service;

import com.chat.repository.ChatReadRepository;
import com.chat.service.dtos.LastChatRead;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatReadService {

    private final ChatReadRepository chatReadRepository;

    public List<LastChatRead> findMembersChatReadIn(Long chatRoomId) {
        return chatReadRepository.findLastReadChatsBy(chatRoomId);
    }

    public LastChatRead findLastChatBy(Long memberId, Long chatRoomId) {
        return chatReadRepository.findLastReadChatBy(memberId, chatRoomId)
                .stream()
                .findFirst()
                .orElse(null);
    }
}
