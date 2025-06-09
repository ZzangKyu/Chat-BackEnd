package com.chat.api.response.chatroom;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatRoomResponse {

    private Long chatRoomId;

    public ChatRoomResponse(Long chatRoomId) {
        this.chatRoomId = chatRoomId;
    }
}
