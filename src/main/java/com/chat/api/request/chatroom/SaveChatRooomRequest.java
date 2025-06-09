package com.chat.api.request.chatroom;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@NoArgsConstructor
public class SaveChatRooomRequest {

    private Set<Long> receiverIds;
    private String title;

    public SaveChatRooomRequest(Set<Long> receiverIds, String title) {
        this.receiverIds = receiverIds;
        this.title = title;
    }
}
