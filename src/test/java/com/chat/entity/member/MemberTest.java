package com.chat.entity.member;

import com.chat.entity.Member;
import com.chat.exception.CustomException;
import com.chat.exception.ErrorCode;
import org.assertj.core.api.AbstractObjectAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberTest {

    @Test
    @DisplayName("회원 엔티티를 생성한다.")
    void createMemberTest() {
        // given
        String username = "username";
        String password = "password";
        String nickname = "nickname";

        // when
        Member member = Member.of(username, password, nickname);

        // then
        assertThat(member.getUsername()).isEqualTo(username);
        assertThat(member.getPassword()).isEqualTo(password);
        assertThat(member.getNickname()).isEqualTo(nickname);
    }

    @Test
    @DisplayName("사용자 ID 가 없을 시 회원 엔티티를 생성하면 CustomException 이 발생한다.")
    void emptyUsernameCreateMemberFailTest() {
        // given
        String password = "password";
        String nickname = "nickname";

        // when
        AbstractObjectAssert<?, CustomException> extracting = assertThatThrownBy(
                () -> Member.of(null, password, nickname))
                .isInstanceOf(CustomException.class)
                .extracting(ex -> (CustomException) ex);

        // then
        extracting.satisfies(ex -> {
            assertThat(ex.getErrorCode()).isEqualTo(ErrorCode.EMPTY_USERNAME);
        });
    }
}