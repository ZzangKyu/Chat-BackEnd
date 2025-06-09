package com.chat.service.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginResponse {

    private Long memberId;
    private String nickname;

    @Builder
    public LoginResponse(Long memberId, String nickname) {
        this.memberId = memberId;
        this.nickname = nickname;
    }
}
