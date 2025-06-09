package com.chat.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoomParticipant extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "chat_room_participant_id")
    private Long id;

    private boolean isParticipate;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    @Builder
    public ChatRoomParticipant(boolean isParticipate, Member member, ChatRoom chatRoom) {
        this.isParticipate = isParticipate;
        this.member = member;
        this.chatRoom = chatRoom;
    }

    public void enterChatRoom() {
        this.isParticipate = true;
    }

    public void leaveChatRoom() {
        this.isParticipate = false;
    }
}
