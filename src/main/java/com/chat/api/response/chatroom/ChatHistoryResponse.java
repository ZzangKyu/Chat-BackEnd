package com.chat.api.response.chatroom;

import com.chat.service.dtos.ChatHistory;
import com.chat.service.dtos.LastChatRead;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ChatHistoryResponse {

    List<ChatHistory> chatHistories = new ArrayList<>();
    List<LastChatRead> lastChatReads = new ArrayList<>();

    public ChatHistoryResponse(List<ChatHistory> chatHistories, List<LastChatRead> lastChatReads) {
        this.chatHistories = chatHistories;
        this.lastChatReads = lastChatReads;
    }
}
