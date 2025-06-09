package com.chat.service.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@NoArgsConstructor
public class SaveChatRoomDTO {

    private Long senderId;
    private Set<Long> receiverIds;
    private String title;

    @Builder
    public SaveChatRoomDTO(Long senderId, Set<Long> receiverIds, String title) {
        this.senderId = senderId;
        this.receiverIds = receiverIds;
        this.title = title;
    }
}
