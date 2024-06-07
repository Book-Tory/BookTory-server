package com.booktory.booktoryserver.Chat.controller;

import com.booktory.booktoryserver.Chat.dto.Chat;
import com.booktory.booktoryserver.Chat.service.ChatService;
import com.booktory.booktoryserver.common.CustomResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;
    // 채팅방 생성
    @PostMapping("/room")
    public CustomResponse createChatRoom(@RequestBody Chat chat) {
        int result = chatService.createChatRoom(chat);

        if (result > 0) {
            return CustomResponse.ok("채팅방이 생성되었습니다.", null);
        } else {
            return CustomResponse.failure("채팅방 생성에 실패하였습니다.");
        }
    }
}
