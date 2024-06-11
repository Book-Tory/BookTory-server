package com.booktory.booktoryserver.Chat.mapper;

import com.booktory.booktoryserver.Chat.domain.ChatEntity;
import com.booktory.booktoryserver.Chat.domain.ChatHistoryEntity;
import com.booktory.booktoryserver.Chat.domain.ChatListEntity;
import com.booktory.booktoryserver.Chat.domain.ChatMessageEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatMapper {
    int createChatRoom(ChatEntity chatEntity);

    List<ChatListEntity> getChatRoomList(String username);

    List<ChatHistoryEntity> getChatHistory(Long chat_id, String username);

    int saveMessage(ChatMessageEntity chatMessageEntity);

    ChatEntity isExistChatRoom(String room_id);
}
