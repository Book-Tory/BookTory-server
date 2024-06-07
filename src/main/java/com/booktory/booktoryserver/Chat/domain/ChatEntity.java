package com.booktory.booktoryserver.Chat.domain;


import com.booktory.booktoryserver.Chat.dto.Chat;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatEntity {
    private Long chat_id; // 채팅방 id (PK)
    private Long seller_id; // 판매자 id
    private Long buyer_id; // 구매자 id
    private Long used_book_id; // 중고서적 id
    private Date chat_created_at; // 채팅방 만들어진 날짜

    public static ChatEntity toEntity(Chat chat) {
        return ChatEntity.builder()
                .seller_id(chat.getSeller_id())
                .buyer_id(chat.getBuyer_id())
                .used_book_id(chat.getUsed_book_id())
                .build();
    }
}
