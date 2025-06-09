package com.chat.service.dtos.chat;

import com.chat.service.dtos.MessageType;
import com.chat.service.dtos.SaveChatData;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SendChat {

    private MessageType messageType = MessageType.CHAT_MESSAGE;
    private Long senderId;
    private String senderNickname;
    private Long chatRoomId;
    private String message;
    private Long chatId;
    private Long unReadCount;
    private LocalDateTime createDate;

    private SendChat(Long senderId, String senderNickname, Long chatRoomId, String message) {
        this.senderId = senderId;
        this.senderNickname = senderNickname;
        this.chatRoomId = chatRoomId;
        this.message = message;
    }

    public static SendChat fromJson(String jsonMessage) {
        // JSON 문자열을 단순 파싱
        jsonMessage = jsonMessage.trim().replaceAll("[{}\"]", ""); // { }, " 제거
        String[] keyValuePairs = jsonMessage.split(",");

        Long senderId = null;
        String senderNickname = null;
        Long chatRoomId = null;
        String message = null;

        for (String pair : keyValuePairs) {
            String[] entry = pair.split(":", 2); // key:value 형식
            String key = entry[0].trim().replaceAll("\\\\", "");
            String value = entry[1].trim();

            switch (key) {
                case "senderId":
                    senderId = Long.parseLong(value);
                    break;
                case "senderNickname":
                    senderNickname = value.replaceAll("\\\\", "");
                    break;
                case "chatRoomId":
                    chatRoomId = Long.parseLong(value);
                    break;
                case "message":
                    message = value.replaceAll("\\\\", "");
                    break;
            }
        }

        return new SendChat(senderId, senderNickname, chatRoomId, message);
    }

    public String toJson() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");

        sb.append("\"messageType\":\"").append(messageType != null ? messageType : "").append("\","); // enum.name() 대신 toString() 사용 시
        sb.append("\"senderId\":").append(senderId != null ? senderId : "null").append(",");
        sb.append("\"senderNickname\":\"").append(senderNickname != null ? escapeJson(senderNickname) : "").append("\",");
        sb.append("\"chatRoomId\":").append(chatRoomId != null ? chatRoomId : "null").append(",");
        sb.append("\"message\":\"").append(message != null ? escapeJson(message) : "").append("\",");
        sb.append("\"chatId\":").append(chatId != null ? chatId : "null").append(",");
        sb.append("\"unReadCount\":").append(unReadCount != null ? unReadCount : "null").append(",");
        sb.append("\"createDate\":\"").append(createDate != null ? escapeJson(createDate.toString()) : "").append("\""); // createDate도 escapeJson 처리

        sb.append("}");
        return sb.toString();
    }

    // ⭐ escapeJson 메서드 수정 제안
    // (이 메서드가 현재 클래스 내부에 정의되어 있거나 유틸리티 클래스에 있다고 가정)
    private String escapeJson(String text) {
        if (text == null) {
            return "";
        }
        StringBuilder escapedText = new StringBuilder();
        for (char c : text.toCharArray()) {
            switch (c) {
                case '"':
                    escapedText.append("\\\"");
                    break;
                case '\\': // 백슬래시 자체를 이스케이프
                    escapedText.append("\\\\");
                    break;
                case '\b': // 백스페이스
                    escapedText.append("\\b");
                    break;
                case '\f': // 폼 피드
                    escapedText.append("\\f");
                    break;
                case '\n': // 줄바꿈
                    escapedText.append("\\n");
                    break;
                case '\r': // 캐리지 리턴
                    escapedText.append("\\r");
                    break;
                case '\t': // 탭
                    escapedText.append("\\t");
                    break;
                default:
                    // 제어 문자 (0x00 ~ 0x1F)는 유니코드로 이스케이프
                    if (c < '\u0020' || (c >= '\u007f' && c <= '\u009f')) {
                        escapedText.append(String.format("\\u%04x", (int) c));
                    } else {
                        escapedText.append(c);
                    }
                    break;
            }
        }
        return escapedText.toString();
    }

    public void updateSavedChat(SaveChatData saveChatData) {
       this.chatId = saveChatData.getChatId();
       this.unReadCount = saveChatData.getUnReadCount();
       this.createDate = saveChatData.getCreateDate();
    }
}
