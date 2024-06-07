package com.booktory.booktoryserver.Chat.mapper;

import com.booktory.booktoryserver.Chat.domain.ChatEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChatMapper {
    int createChatRoom(ChatEntity chatEntity);
}
