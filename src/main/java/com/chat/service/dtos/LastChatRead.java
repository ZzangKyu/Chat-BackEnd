package com.chat.service.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LastChatRead {
    private Long memberId;
    private Long lastChatReadId;

    public LastChatRead(Long memberId, Long lastChatReadId) {
        this.memberId = memberId;
        this.lastChatReadId = lastChatReadId;
    }
}
