package com.chat.repository;

import com.chat.entity.Chat;
import com.chat.entity.ChatRoom;
import com.chat.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class ChatRepositoryTest {

    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private ChatRoomRepository chatRoomRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("채팅 정보를 저장한다.")
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

        String message = "message";
        Chat chat = new Chat(message, savedMember, savedChatRoom);

        // when
        Chat savedChat = chatRepository.save(chat);

        // then
        assertThat(savedChat.getId()).isNotNull();
        assertThat(savedChat.getMessage()).isEqualTo(message);
        assertThat(savedChat.getMember()).isEqualTo(savedMember);
        assertThat(savedChat.getChatRoom()).isEqualTo(savedChatRoom);
    }
}