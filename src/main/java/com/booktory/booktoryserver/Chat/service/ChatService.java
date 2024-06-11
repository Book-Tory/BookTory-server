package com.booktory.booktoryserver.Chat.service;

import com.booktory.booktoryserver.Chat.domain.ChatEntity;
import com.booktory.booktoryserver.Chat.domain.ChatHistoryEntity;
import com.booktory.booktoryserver.Chat.domain.ChatListEntity;
import com.booktory.booktoryserver.Chat.domain.ChatMessageEntity;
import com.booktory.booktoryserver.Chat.dto.ChatDTO;
import com.booktory.booktoryserver.Chat.dto.ChatHistoryDTO;
import com.booktory.booktoryserver.Chat.dto.ChatMessageDTO;
import com.booktory.booktoryserver.Chat.mapper.ChatMapper;
import com.booktory.booktoryserver.common.CustomResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class ChatService {
    private final ChatMapper chatMapper;
    public CustomResponse createChatRoom(ChatDTO chat) {
        String sellerEmail = chatMapper.findEmailById(chat.getSeller_id());

        chat.setSeller_email(sellerEmail);
        chat.setBuyer_id(chatMapper.findIdByEmail(chat.getBuyer_email()));

        String roomId = createRoomId(chat);

        // 이미 같은 상품에 같은 판매자, 구매자의 채팅방이 존재하는지 확인
        ChatEntity existChatRoom = chatMapper.isExistChatRoom(roomId);

        if (existChatRoom == null) {
            chat.setRoom_id(roomId);
            ChatEntity chatEntity = ChatEntity.toEntity(chat);
            int result = chatMapper.createChatRoom(chatEntity); // 채팅방 생성

            if (result > 0) {
                return CustomResponse.ok("채팅방이 생성되었습니다.", null);
            } else {
                return CustomResponse.failure("채팅방 생성에 실패하였습니다.");
            }
        } else {
            return CustomResponse.ok("이미 존재하는 채팅방이 있음", null);
        }
    }

    public List<ChatListEntity> getChatRoomList(String username) {
        return chatMapper.getChatRoomList(username);
    }

    public ChatHistoryDTO getChatHistory(Long chat_id, String username) {
        List<ChatHistoryEntity> chatHistory = chatMapper.getChatHistory(chat_id, username);

        if (chatHistory.isEmpty()) {
            return null;
        }

        ChatHistoryEntity firstHistoryEntity = chatHistory.get(0);

        List<ChatMessageDTO> messages = chatHistory.stream()
                .map(entity -> ChatMessageDTO.builder()
                        .sender_id(entity.getSender_id())
                        .sender_nickname(entity.getSender_nickname())
                        .sender_email(entity.getSender_email())
                        .message_content(entity.getMessage_content())
                        .message_created_at(entity.getMessage_created_at())
                        .build())
                .collect(Collectors.toList());

        return ChatHistoryDTO.toDTO(firstHistoryEntity, messages);
    }

    public int saveMessage(ChatMessageDTO chatMessage) {
        return chatMapper.saveMessage(ChatMessageEntity.toEntity(chatMessage));
    }

    public ChatEntity isExistChatRoom(String room_id) {
        return chatMapper.isExistChatRoom(room_id);
    }

    // roomId 생성
    public String createRoomId(ChatDTO chat) {
        String[] emails = {chat.getSeller_email(), chat.getBuyer_email()};
        Arrays.sort(emails);
        String roomId = String.join("_", emails) + "_" + chat.getUsed_book_id();

        log.info("roomId {} " + roomId);
        return roomId;
    }
}
