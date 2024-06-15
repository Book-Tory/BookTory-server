package com.booktory.booktoryserver.Chat.domain;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatHistoryEntity {
    private Long chat_id; // 선택한 채팅방 id
    private String chat_partner_nickname; // 상대방 닉네임
    private Long sender_id;
    private String sender_nickname;
    private String sender_email;
//    private String stored_image_name;
//    private String used_book_image_url; 로 변환
    private String title; // 글 제목
    private int discount; // 중고 서적 가격
    private String message_content; // 메세지 내용
    private Date message_created_at; // 메세지 보낸 날짜
}
