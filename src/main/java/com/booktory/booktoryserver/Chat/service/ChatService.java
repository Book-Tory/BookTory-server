package com.booktory.booktoryserver.Chat.service;

import com.booktory.booktoryserver.Chat.domain.ChatEntity;
import com.booktory.booktoryserver.Chat.dto.Chat;
import com.booktory.booktoryserver.Chat.mapper.ChatMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatService {
    private final ChatMapper chatMapper;
    public int createChatRoom(Chat chat) {
        ChatEntity chatEntity = ChatEntity.toEntity(chat);
        return chatMapper.createChatRoom(chatEntity);
    }
}
