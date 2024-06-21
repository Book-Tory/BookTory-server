package com.booktory.booktoryserver.Chat.domain;


import com.booktory.booktoryserver.Chat.dto.ChatMessageDTO;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomEntity {
    private Long chat_id; // 채팅방 id (PK)
    private String chat_partner_nickname; // 상대방 닉네임
    private String chat_partner_img; // 상대방 프로필 사진
    private String stored_image_name; // 상품의 첫 번째 사진 가져올 거
    private String title; // 글 제목
    private int discount; // 중고 서적 가격

}
