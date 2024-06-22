package com.booktory.booktoryserver.Chat.mapper;

import com.booktory.booktoryserver.Chat.domain.*;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatMapper {
    int createChatRoom(ChatEntity chatEntity);

    List<ChatListEntity> getChatRoomList(Long user_id);

    List<ChatHistoryEntity> getChatHistory(Long chat_id, Long user_id);

    int saveMessage(ChatMessageEntity chatMessageEntity);

    ChatEntity isExistChatRoom(String room_id);

    String findEmailById(Long sellerId);

    Long findIdByEmail(String buyerEmail);

    @Delete("DELETE FROM chat WHERE seller_id = #{sellerId}")
    void deleteBySellerId(Long sellerId);

    Long getReceiverId(Long chatId, Long senderId);

    String findById(Long entityId);

    ChatRoomEntity getChatRoomInfo(Long chatId, Long user_id);

    int leaveChatRoom(Long chatId, Long userId);

    boolean isMemberOfChatRoom(Long chatId, Long userId);
}
