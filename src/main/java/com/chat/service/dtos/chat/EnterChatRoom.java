package com.chat.service.dtos.chat;

import com.chat.service.dtos.MessageType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class EnterChatRoom {
    private MessageType messageType = MessageType.CHAT_ENTER;
    private Long memberId;
    private Long lastReadChatId;

    @Builder
    public EnterChatRoom(Long memberId, Long lastReadChatId) {
        this.memberId = memberId;
        this.lastReadChatId = lastReadChatId;
    }

    public String toJson() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");

        sb.append("\"messageType\":\"").append(messageType != null ? messageType.name() : "").append("\",");
        sb.append("\"memberId\":").append(memberId != null ? memberId : "null").append(",");
        sb.append("\"lastReadChatId\":").append(lastReadChatId != null ? lastReadChatId : "null");

        sb.append("}");
        return sb.toString();
    }
}
