package com.booktory.booktoryserver.Chat.controller;

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
    public CustomResponse createChatRoom(@RequestBody ChatDTO chat) {
        String roomId = createRoomId(chat);
        chat.setRoom_id(roomId);
        int result = chatService.createChatRoom(chat);

        if (result > 0) {
            return CustomResponse.ok("채팅방이 생성되었습니다.", null);
        } else {
            return CustomResponse.failure("채팅방 생성에 실패하였습니다.");
        }
    }

    // roomId 생성
    private String createRoomId(ChatDTO chat) {
        String[] emails = {chat.getSeller_email(), chat.getBuyer_email()};
        Arrays.sort(emails);
        String roomId = String.join("_", emails) + "_" + chat.getUsed_book_id();

        log.info("roomId {} " + roomId);
        return roomId;
    }

    // 내 채팅방 리스트 불러오기
    @GetMapping("/rooms/{user_id}")
    public CustomResponse getChatRoomList (@PathVariable ("user_id") Long user_id) {
        List<ChatListEntity> chatList = chatService.getChatRoomList(user_id);

        if (!chatList.isEmpty()) {
            return CustomResponse.ok("채팅방 리스트 조회 성공", chatList);
        } else {
            return CustomResponse.failure("채팅방 리스트를 조회 실패하였습니다.");
        }
    }

    // 특정 채팅방 내용 불러오기
    @GetMapping("/room/{chat_id}/{user_id}")
    public CustomResponse getChatHistory (@PathVariable ("chat_id") Long chat_id, @PathVariable ("user_id") Long user_id) {
        ChatHistoryDTO chatHistory = chatService.getChatHistory(chat_id, user_id);

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
