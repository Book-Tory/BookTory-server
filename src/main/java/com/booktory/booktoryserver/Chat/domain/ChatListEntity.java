package com.booktory.booktoryserver.Chat.domain;


import com.booktory.booktoryserver.Chat.dto.Chat;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatListEntity {
    private Long chat_id; // 채팅방 id (PK)
    private Long user_id; // 현재 로그인한 내 id
    private String chat_partner_nickname; // 상대방 닉네임
    private String chat_partner_img; // 상대방 프로필 사진
    private String last_message_content; // 마지막에 온 채팅 내용
    private Date last_message_time; // 마지막에 온 채팅 시간
}
