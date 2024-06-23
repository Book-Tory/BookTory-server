package com.booktory.booktoryserver.Chat.controller;

import com.booktory.booktoryserver.Alram.domain.AlarmEntity;
import com.booktory.booktoryserver.Alram.service.AlarmService;
import com.booktory.booktoryserver.Chat.domain.ChatEntity;
import com.booktory.booktoryserver.Chat.domain.ChatListEntity;
import com.booktory.booktoryserver.Chat.dto.ChatDTO;
import com.booktory.booktoryserver.Chat.dto.ChatHistoryDTO;
import com.booktory.booktoryserver.Chat.dto.ChatMessageDTO;
import com.booktory.booktoryserver.Chat.dto.response.ChatRoomResponseDTO;
import com.booktory.booktoryserver.Chat.service.ChatService;
import com.booktory.booktoryserver.Users.mapper.UserMapper;
import com.booktory.booktoryserver.Users.model.UserEntity;
import com.booktory.booktoryserver.common.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/api/chat")
@RequiredArgsConstructor
@Tag(name = "Chat API", description = "채팅 관련 API")
public class ChatController {
    private final ChatService chatService;
    private final UserMapper userMapper;
    private final SimpMessagingTemplate messagingTemplate;
    private final AlarmService alarmService;

    @Operation(summary = "채팅방 생성", description = "채팅방을 생성합니다.")
    @PostMapping("/room")
    public CustomResponse createChatRoom(@AuthenticationPrincipal UserDetails user, @RequestBody ChatDTO chat) {
        chat.setBuyer_email(user.getUsername());
        return chatService.createChatRoom(chat);
    }

    @Operation(summary = "내 채팅방 리스트 불러오기", description = "현재 사용자의 채팅방 리스트를 불러옵니다.")
    @GetMapping("/rooms")
    public CustomResponse getChatRoomList(@AuthenticationPrincipal UserDetails user) {
        String username = user.getUsername();
        Optional<UserEntity> userEntity = userMapper.findByEmail(username);
        Long userId = userEntity.get().getUser_id();
        List<ChatListEntity> chatList = chatService.getChatRoomList(username);
        ChatRoomResponseDTO response = new ChatRoomResponseDTO(userId, chatList);
        if (!chatList.isEmpty()) {
            return CustomResponse.ok("채팅방 리스트 조회 성공", response);
        } else {
            return CustomResponse.failure("채팅방 리스트를 조회 실패하였습니다.");
        }
    }

    @Operation(summary = "특정 채팅방 내용 불러오기", description = "특정 채팅방의 내용을 불러옵니다.")
    @GetMapping("/room/{chat_id}")
    public CustomResponse getChatHistory(@Parameter(description = "채팅방 ID", required = true) @PathVariable("chat_id") Long chat_id, @AuthenticationPrincipal UserDetails user) {
        String username = user.getUsername();
        ChatHistoryDTO chatHistory = chatService.getChatHistory(chat_id, username);
        if (chatHistory != null) {
            return CustomResponse.ok("채팅 내역 조회 성공", chatHistory);
        } else {
            return CustomResponse.failure("채팅 내역 조회 실패");
        }
    }

    @Operation(summary = "채팅방 나가기", description = "특정 채팅방 나가기")
    @DeleteMapping("/leave/{chat_id}")
    public CustomResponse leaveChatRoom(@Parameter(description = "채팅방 ID", required = true) @PathVariable("chat_id") Long chat_id, @AuthenticationPrincipal UserDetails user) {
        String username = user.getUsername();

        Optional<UserEntity> userEntity = userMapper.findByEmail(username);

        Long userId = userEntity.get().getUser_id();

        if (!chatService.isMemberOfChatRoom(chat_id, userId)) {
            return CustomResponse.failure("채팅방을 나갈 권한이 없습니다.");
        }

        int result  = chatService.leaveChatRoom(chat_id, userId);
        if (result > 0) {
            return CustomResponse.ok("채팅방 나가기 성공", result);
        } else {
            return CustomResponse.failure("채팅방 나가기 실패");
        }
    }

    @Operation(summary = "채팅 메시지 전송", description = "특정 채팅방에 메시지를 전송합니다.")
    @MessageMapping("/{chatId}")
    public void send(@DestinationVariable Long chatId, @RequestBody ChatMessageDTO chatMessage) {
        Long chat_message_id = chatService.saveMessage(chatMessage); // 메세지 보낼 때 생성되는 chat_message_id

        Long senderId = chatMessage.getSender_id(); // 보낸 사람 id
        Long receiverId = chatService.getReceiverId(chatId, senderId); // 메세지 받은 사람의 id

        chatMessage.setChat_message_id(chat_message_id);
        
        if (chat_message_id > 0) {
            AlarmEntity alarmEntity = AlarmEntity.toAlarmEntity(chatMessage, receiverId);
            alarmService.saveAlarm(alarmEntity); // 알람 테이블에 저장
            alarmService.sendAlarm(alarmEntity);
            messagingTemplate.convertAndSend("/queue/chat/" + chatId , chatMessage);
        } else {
            messagingTemplate.convertAndSend("/queue/chat/" + chatId, chatMessage);
        }
    }


}
