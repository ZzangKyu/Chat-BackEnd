package com.chat.api.response.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetMembersResponse {

    private Long memberId;
    private String username;
    private String nickname;

    @Builder
    public GetMembersResponse(Long memberId, String username, String nickname) {
        this.memberId = memberId;
        this.username = username;
        this.nickname = nickname;
    }
}
