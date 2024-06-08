package com.booktory.booktoryserver.Chat.service;

import com.booktory.booktoryserver.Chat.domain.ChatEntity;
import com.booktory.booktoryserver.Chat.domain.ChatHistoryEntity;
import com.booktory.booktoryserver.Chat.domain.ChatListEntity;
import com.booktory.booktoryserver.Chat.domain.ChatMessageEntity;
import com.booktory.booktoryserver.Chat.dto.ChatDTO;
import com.booktory.booktoryserver.Chat.dto.ChatHistoryDTO;
import com.booktory.booktoryserver.Chat.dto.ChatMessageDTO;
import com.booktory.booktoryserver.Chat.mapper.ChatMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ChatService {
    private final ChatMapper chatMapper;
    public int createChatRoom(ChatDTO chat) {
        ChatEntity chatEntity = ChatEntity.toEntity(chat);
        return chatMapper.createChatRoom(chatEntity);
    }

    public List<ChatListEntity> getChatRoomList(Long user_id) {
        return chatMapper.getChatRoomList(user_id);
    }

    public ChatHistoryDTO getChatHistory(Long chat_id, Long user_id) {
        List<ChatHistoryEntity> chatHistory = chatMapper.getChatHistory(chat_id, user_id);

        if (chatHistory.isEmpty()) {
            return null;
        }

        ChatHistoryEntity firstHistoryEntity = chatHistory.get(0);

        List<ChatMessageDTO> messages = chatHistory.stream()
                .map(entity -> ChatMessageDTO.builder()
                        .sender_id(entity.getSender_id())
                        .message_content(entity.getMessage_content())
                        .message_created_at(entity.getMessage_created_at())
                        .build())
                .collect(Collectors.toList());

        return ChatHistoryDTO.toDTO(firstHistoryEntity, messages);
    }

    public int saveMessage(ChatMessageDTO chatMessage) {
        return chatMapper.saveMessage(ChatMessageEntity.toEntity(chatMessage));
    }
}
