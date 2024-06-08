package com.booktory.booktoryserver.Chat.dto;


import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatDTO {
    private Long chat_id; // 채팅방 id (PK)
    private Long seller_id; // 판매자 id
    private Long buyer_id; // 구매자 id
    private Long used_book_id; // 중고서적 id
    private Date chat_created_at; // 채팅방 만들어진 날짜
}
