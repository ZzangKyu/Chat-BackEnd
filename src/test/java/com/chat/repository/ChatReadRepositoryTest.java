package com.chat.repository;

import com.chat.entity.Chat;
import com.chat.entity.ChatRead;
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
class ChatReadRepositoryTest {

    @Autowired
    private ChatReadRepository chatReadRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Test
    @DisplayName("채팅읽음 정보를 저장한다.")
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
        Chat savedChat = chatRepository.save(chat);

        boolean isRead = true;
        ChatRead chatRead = new ChatRead(isRead, savedMember, savedChat);

        // when
        ChatRead savedChatRead = chatReadRepository.save(chatRead);

        // then
        assertThat(savedChatRead.getIsRead()).isTrue();
        assertThat(savedChatRead.getId()).isNotNull();
        assertThat(savedChatRead.getMember()).isEqualTo(savedMember);
        assertThat(savedChatRead.getChat()).isEqualTo(savedChat);
    }
}