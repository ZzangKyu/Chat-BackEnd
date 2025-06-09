package com.chat.socket.config;

import com.chat.api.request.member.JoinRequest;
import com.chat.api.request.member.LoginRequest;
import com.chat.service.ChatRoomService;
import com.chat.service.MemberService;
import com.chat.service.dtos.SaveChatRoomDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.io.IOException;
import java.net.URI;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WebSocketConfigTest {

    @LocalServerPort
    private int port;
    private String WEBSOCKET_URI;
    private StandardWebSocketClient webSocketClient;
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MemberService memberService;
    @Autowired
    private ChatRoomService chatRoomService;

    @BeforeEach
    void setup() {
        WEBSOCKET_URI = "ws://localhost:" + port + "/ws/chat/room/1";
        webSocketClient = new StandardWebSocketClient();
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("웹 소켓에 연결한다.")
    void connectWebSocketTest() throws IOException, ExecutionException, InterruptedException, TimeoutException {
        // given
        CompletableFuture<WebSocketSession> futuresession = new CompletableFuture<>();

        WebSocketHandler handler = new AbstractWebSocketHandler() {
            @Override
            public void afterConnectionEstablished(WebSocketSession session) throws Exception {
                futuresession.complete(session);
            }
        };

        CompletableFuture<WebSocketSession> execute = webSocketClient.execute(handler, WEBSOCKET_URI);
        WebSocketSession session = execute.get(10, TimeUnit.SECONDS);
        assertThat(session.isOpen()).isTrue();

        session.close();
    }

    @Test
    @DisplayName("두 클라이언트가 메시지를 주고 받는다.")
    void sendMessageTest() throws Exception {
        // given
        Long username1 = createMember("username1");
        Long username2 = createMember("username2");

        LoginRequest request = LoginRequest
                .builder()
                .username("username1")
                .password("password")
                .build();

        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.post("/api/member/login")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
        );

        request = LoginRequest
                .builder()
                .username("username2")
                .password("password")
                .build();

        perform = mockMvc.perform(MockMvcRequestBuilders.post("/api/member/login")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
        );

        Set<Long> set = new HashSet<>();
        set.add(username2);
        SaveChatRoomDTO saveChatRoomDTO = SaveChatRoomDTO
                .builder()
                .senderId(username1)
                .receiverIds(set)
                .title("title")
                .build();
        Long chatRoomId = chatRoomService.saveChatRoom(saveChatRoomDTO);

        CompletableFuture<String> client1Message = new CompletableFuture<>();


        // then
    }

    Long createMember(String username) {
        JoinRequest request = JoinRequest
                .builder()
                .username(username)
                .password("password")
                .nickname("nickname")
                .build();

        return memberService.join(request);
    }

    @Test
    @DisplayName("두 클라이언트가 메시지를 주고받을 수 있다.")
    void testChatRoomMessaging() throws Exception {

        Long username1 = createMember("username1");
        Long username2 = createMember("username2");
        LoginRequest request = LoginRequest
                .builder()
                .username("username2")
                .password("password")
                .build();

        // MockHttpSession 생성
        MockHttpSession mockHttpSession = new MockHttpSession();

        // 세션에 속성 추가
        mockHttpSession.setAttribute("SESSION_ID", "mockSessionId");

        // 로그인 요청을 보냄
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/api/member/login")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .session(mockHttpSession)); // 세션을 설정

        String id = mockHttpSession.getId();

        // 응답 결과에서 JSESSIONID 추출
        Cookie[] cookies = resultActions.andReturn().getResponse().getCookies();
        Collection<String> headerNames = resultActions.andReturn().getResponse().getHeaderNames();
        String jsessionId = resultActions.andReturn().getResponse().getHeader("Set-Cookie");

        // Set-Cookie에서 JSESSIONID 값을 파싱
        String sessionId = null;
        if (jsessionId != null && jsessionId.contains("JSESSIONID=")) {
            sessionId = jsessionId.split("JSESSIONID=")[1].split(";")[0];
        }

        String change = mockHttpSession.changeSessionId();

        Set<Long> set = new HashSet<>();
        set.add(username2);
        SaveChatRoomDTO saveChatRoomDTO = SaveChatRoomDTO
                .builder()
                .senderId(username1)
                .receiverIds(set)
                .title("title")
                .build();
        Long chatRoomId = chatRoomService.saveChatRoom(saveChatRoomDTO);

        // 웹소켓 핸들러 정의
        WebSocketHandler webSocketHandler = new AbstractWebSocketHandler() {
            @Override
            public void afterConnectionEstablished(WebSocketSession session) throws Exception {
                // WebSocket 연결 후 처리
                System.out.println("WebSocket 연결 성공!");
            }
        };

        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", "JSESSIONID=" + id); // 세션 쿠키를 추가

        WebSocketSession session = webSocketClient.execute(
                webSocketHandler,
                WEBSOCKET_URI, // null 대신 올바른 URI 템플릿을 전달
                URI.create(WEBSOCKET_URI),
                new HttpHeaders() {{
                    set("Cookie", "JSESSIONID=" + id);
                }}
        ).get(5, TimeUnit.SECONDS);

        session.sendMessage(new TextMessage("Hello WebSocket!"));
    }
}