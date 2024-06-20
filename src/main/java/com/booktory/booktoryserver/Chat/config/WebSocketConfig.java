package com.booktory.booktoryserver.Chat.config;

import com.booktory.booktoryserver.Chat.handler.StompHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker // STOMP를 사용할 수 있게 해주는 어노테이션
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    private final StompHandler stompHandler;

    // 구독과 발행시 사용할 prefix 지정
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // '/queue'가 prefix인 destination을 가진 메세지를 브로커로 라우팅해주는 설정
        config.enableSimpleBroker("/queue");
        // '/app'이 prefix로 붙은 메세지를 컨트롤러내에서 @MessageMapping이 붙은 메소드로 라우팅 하겠다는 설정
        config.setApplicationDestinationPrefixes("/app");
    }

    // 웹소켓 연결을 위해 사용할 엔드포인트 등록
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOrigins("*");
    }

    // 인증
    // configureClientInboundChannel : 클라이언트에서 서버로 들어오는 요청을 전달하는 채널
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(stompHandler);
    }
}
