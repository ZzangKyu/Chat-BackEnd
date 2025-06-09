package com.chat.service;

import com.chat.entity.ChatRoomParticipant;
import com.chat.repository.ChatRoomParticipantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatRoomParticipantService {

    private final ChatRoomParticipantRepository chatRoomParticipantRepository;

    @Transactional
    public void enterChatRoom(Long chatRoomId, Long memberId) {
        ChatRoomParticipant chatRoomParticipant = chatRoomParticipantRepository.findByChatRoomIdAndMemberId(chatRoomId, memberId);
        chatRoomParticipant.enterChatRoom();
    }

    @Transactional
    public void leaveChatRoom(Long chatRoomId, Long memberId) {
        ChatRoomParticipant chatRoomParticipant = chatRoomParticipantRepository.findByChatRoomIdAndMemberId(chatRoomId, memberId);
        chatRoomParticipant.leaveChatRoom();
    }
}
