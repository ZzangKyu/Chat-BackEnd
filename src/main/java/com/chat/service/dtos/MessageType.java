package com.chat.service.dtos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MessageType {

    CHAT_MESSAGE("채팅 메시지"),
    CHAT_ENTER("채팅방 접속"),
    ;

    private final String description;
}
