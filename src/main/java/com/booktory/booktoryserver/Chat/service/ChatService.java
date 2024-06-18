package com.booktory.booktoryserver.Chat.service;

import com.amazonaws.services.s3.AmazonS3;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class ChatService {
    private final ChatMapper chatMapper;

    private final AmazonS3 amazonS3;

    @Value("${spring.s3.bucket}")
    private String bucketName;

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
            int result = chatMapper.createChatRoom(chatEntity);

            Long chat_id = chatEntity.getChat_id();
            chatEntity.setChat_id(chat_id);// 채팅방 생성

            if (result > 0) {
                return CustomResponse.ok("채팅방이 생성되었습니다.", chatEntity);
            } else {
                return CustomResponse.failure("채팅방 생성에 실패하였습니다.");
            }
        } else {
            return CustomResponse.failure("이미 존재하는 채팅방이 있음");
        }
    }

    public List<ChatListEntity> getChatRoomList(String username) {
        Long user_id = chatMapper.findIdByEmail(username);

        List<ChatListEntity> chatList = chatMapper.getChatRoomList(user_id);

        String url = "";

        for(ChatListEntity chatListEntity : chatList) {
            if (chatListEntity.getStored_image_name() != null) {
                url = amazonS3.getUrl(bucketName, "profile/" + chatListEntity.getStored_image_name()).toString();
                chatListEntity.setStored_image_name(url);
            }
        }

        return chatList;
    }

    public ChatHistoryDTO getChatHistory(Long chat_id, String username) {
        Long user_id = chatMapper.findIdByEmail(username);

        List<ChatHistoryEntity> chatHistory = chatMapper.getChatHistory(chat_id, user_id);

        if (chatHistory.isEmpty()) {
            return null;
        }

        ChatHistoryEntity firstHistoryEntity = chatHistory.get(0);

        String url = "";

        for (ChatHistoryEntity chatHistoryEntity : chatHistory) {
            if (chatHistoryEntity.getStored_image_name() != null) {
                url = amazonS3.getUrl(bucketName, "profile/" + chatHistoryEntity.getStored_image_name()).toString();
            }
        }

        List<ChatMessageDTO> messages = chatHistory.stream()
                .map(entity -> ChatMessageDTO.builder()
                        .sender_id(entity.getSender_id())
                        .sender_nickname(entity.getSender_nickname())
                        .sender_email(entity.getSender_email())
                        .message_content(entity.getMessage_content())
                        .message_created_at(entity.getMessage_created_at())
                        .build())
                .collect(Collectors.toList());

        return ChatHistoryDTO.toDTO(firstHistoryEntity, messages, url);
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
