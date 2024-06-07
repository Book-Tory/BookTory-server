package com.booktory.booktoryserver.Chat.mapper;

import com.booktory.booktoryserver.Chat.domain.ChatEntity;
import com.booktory.booktoryserver.Chat.domain.ChatListEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatMapper {
    int createChatRoom(ChatEntity chatEntity);

    List<ChatListEntity> getChatRoomList(Long user_id);
}
