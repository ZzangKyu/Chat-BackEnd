package com.chat.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "채팅 서비스 API 문서",
                description = "이 문서는 채팅 서비스의 API를 설명합니다."
        ),
        servers = {
                @Server(url = "/", description = "기본 서버 (로컬 개발 환경)"),
        }
)
@Configuration
public class OpenApiConfig {

}