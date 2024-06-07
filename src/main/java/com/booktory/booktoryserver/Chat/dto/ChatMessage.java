package com.booktory.booktoryserver.Chat.dto;


import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
    private Long chat_message_id; // 메세지 id (PK)
    private String content; // 메세지 내용
    private Date message_created_at; // 메세지 보낸 시간
    private Date message_read_date; // 메세지 읽은 시간
    private Long chat_id; // 채팅방 id
    private Long sender_id; // 보내는 사람 id
}
