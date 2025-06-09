package com.chat.api.response.chatroom;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OpponentResponse {

    private Long opponentId;
    private String opponentNickname;

    public OpponentResponse(Long opponentId, String opponentNickname) {
        this.opponentId = opponentId;
        this.opponentNickname = opponentNickname;
    }
}
