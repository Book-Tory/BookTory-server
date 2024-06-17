package com.booktory.booktoryserver.Chat.dto;


import com.booktory.booktoryserver.Chat.domain.ChatHistoryEntity;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatHistoryDTO {
    private Long chat_id; // 선택한 채팅방 id
    private String chat_partner_nickname; // 상대방 닉네임
    private String title; // 글 제목
    private int discount; // 중고 서적 가격
    private List<ChatMessageDTO> messages; // 메세지 내용들
    private String stored_image_name; // 중고서적 글 첫 번재 사진

    public static ChatHistoryDTO toDTO(ChatHistoryEntity firstHistoryEntity, List<ChatMessageDTO> messages, String url) {
        return ChatHistoryDTO.builder()
                .chat_id(firstHistoryEntity.getChat_id())
                .chat_partner_nickname(firstHistoryEntity.getChat_partner_nickname())
                .title(firstHistoryEntity.getTitle())
                .discount(firstHistoryEntity.getDiscount())
                .messages(messages)
                .stored_image_name(url)
                .build();
    }
}
