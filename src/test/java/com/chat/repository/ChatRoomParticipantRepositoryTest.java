package com.chat.repository;

import com.chat.entity.ChatRoom;
import com.chat.entity.ChatRoomParticipant;
import com.chat.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class ChatRoomParticipantRepositoryTest {

    @Autowired
    private ChatRoomParticipantRepository chatRoomParticipantRepository;
    @Autowired
    private ChatRoomRepository chatRoomRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("채팅방 참여 정보를 저장한다.")
    void saveTest() {
        // given
        String username = "username";
        String password = "password";
        String nickname = "nickname";
        Member member = Member.of(username, password, nickname);
        Member savedMember = memberRepository.save(member);

        String title = "title";
        ChatRoom chatRoom = ChatRoom.of(null, title);
        ChatRoom savedChatRoom = chatRoomRepository.save(chatRoom);
        ChatRoomParticipant chatRoomParticipant = new ChatRoomParticipant(true, savedMember, savedChatRoom);

        // when
        ChatRoomParticipant savedChatRoomParticipant = chatRoomParticipantRepository.save(chatRoomParticipant);

        // then
        assertThat(savedChatRoomParticipant.getId()).isNotNull();
        assertThat(savedChatRoomParticipant.getMember()).isEqualTo(savedMember);
        assertThat(savedChatRoomParticipant.getChatRoom()).isEqualTo(savedChatRoom);
    }
}