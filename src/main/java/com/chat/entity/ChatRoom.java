package com.chat.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "chat_room_id")
    private Long id;

    private String title;

    private ChatRoom(String title) {
        this.title = title;
    }

    public static ChatRoom of(List<String> participants, String title) {
        return new ChatRoom(title != null && !title.isEmpty()
                ? title : generateDefaultTitle(participants));
    }

    private static String generateDefaultTitle(List<String> participants) {
        return participants.stream()
                .sorted()
                .collect(Collectors.joining(", "));
    }
}
