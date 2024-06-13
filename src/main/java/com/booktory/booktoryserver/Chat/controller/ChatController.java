package com.booktory.booktoryserver.Chat.controller;

import com.booktory.booktoryserver.Chat.domain.ChatEntity;
import com.booktory.booktoryserver.Chat.domain.ChatListEntity;
import com.booktory.booktoryserver.Chat.dto.ChatDTO;
import com.booktory.booktoryserver.Chat.dto.ChatHistoryDTO;
import com.booktory.booktoryserver.Chat.dto.ChatMessageDTO;
import com.booktory.booktoryserver.Chat.service.ChatService;
import com.booktory.booktoryserver.common.CustomResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    // 채팅방 생성
    @PostMapping("/room")
    public CustomResponse createChatRoom(@AuthenticationPrincipal UserDetails user, @RequestBody ChatDTO chat) {
        chat.setBuyer_email(user.getUsername()); // 현재 로그인한 내 이메일은 구매자 이메일로

        return chatService.createChatRoom(chat);
    }

    // 내 채팅방 리스트 불러오기
    @GetMapping("/rooms")
    public CustomResponse getChatRoomList (@AuthenticationPrincipal UserDetails user) {
        String username = user.getUsername();

        List<ChatListEntity> chatList = chatService.getChatRoomList(username);

        if (!chatList.isEmpty()) {
            return CustomResponse.ok("채팅방 리스트 조회 성공", chatList);
        } else {
            return CustomResponse.failure("채팅방 리스트를 조회 실패하였습니다.");
        }
    }

    // 특정 채팅방 내용 불러오기
    @GetMapping("/room/{chat_id}")
    public CustomResponse getChatHistory (@PathVariable ("chat_id") Long chat_id, @AuthenticationPrincipal UserDetails user) {
        String username = user.getUsername();

        ChatHistoryDTO chatHistory = chatService.getChatHistory(chat_id, username);

        if (chatHistory != null) {
            return CustomResponse.ok("채팅 내역 조회 성공", chatHistory);
        } else {
            return CustomResponse.failure("채팅 내역 조회 실패");
        }
    }

    //
    @MessageMapping("/{roomId}")
    @SendTo("/queue/chat/{roomId}")
    public CustomResponse chat(@DestinationVariable ("roomId") String roomId, ChatMessageDTO chatMessage) {
        int result = chatService.saveMessage(chatMessage);

        if (result > 0) {
            return CustomResponse.ok("채팅이 왔다!", chatMessage);
        } else {
            return CustomResponse.failure("채팅 오류");
        }
    }
}
