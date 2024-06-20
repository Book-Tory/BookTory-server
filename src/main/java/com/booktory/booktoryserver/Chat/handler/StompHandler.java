package com.booktory.booktoryserver.Chat.handler;

import com.booktory.booktoryserver.Users.filter.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class StompHandler implements ChannelInterceptor {
    private final JWTUtil jwtUtil;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        System.out.println("preSend called with command: " + accessor.getCommand());

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String authorizationHeader = accessor.getFirstNativeHeader("Authorization");

            if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
                String jwtToken = authorizationHeader.substring(7);

                if (jwtUtil.isExpired(jwtToken)) {
                    throw new IllegalStateException("Expired JWT token");
                }
                if (!"access".equals(jwtUtil.getCategory(jwtToken))) {
                    throw new IllegalStateException("Invalid JWT token category");
                }
            } else {
                System.out.println("Authorization header is missing or invalid.");
            }
        }
        return message;
    }

}