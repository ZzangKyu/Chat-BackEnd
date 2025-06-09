package com.chat.repository;

import com.chat.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class MemberRepositoryTest {
    
    @Autowired
    private MemberRepository memberRepository;
    
    @Test
    @DisplayName("회원 정보를 저장한다.")
    void saveTest() {
        // given
        String username = "username";
        String password = "password";
        String nickname = "nickname";
        Member member = Member.of(username, password, nickname);

        // when
        Member savedMember = memberRepository.save(member);

        // then
        assertThat(savedMember.getId()).isNotNull();
        assertThat(savedMember.getUsername()).isEqualTo(username);
        assertThat(savedMember.getPassword()).isEqualTo(password);
        assertThat(savedMember.getNickname()).isEqualTo(nickname);
    }

}