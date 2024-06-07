package com.booktory.booktoryserver.Chat.service;

import com.booktory.booktoryserver.Chat.domain.ChatEntity;
import com.booktory.booktoryserver.Chat.domain.ChatListEntity;
import com.booktory.booktoryserver.Chat.dto.Chat;
import com.booktory.booktoryserver.Chat.mapper.ChatMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ChatService {
    private final ChatMapper chatMapper;
    public int createChatRoom(Chat chat) {
        ChatEntity chatEntity = ChatEntity.toEntity(chat);
        return chatMapper.createChatRoom(chatEntity);
    }

    public List<ChatListEntity> getChatRoomList(Long user_id) {
        return chatMapper.getChatRoomList(user_id);
    }
}
