package com.booktory.booktoryserver.Chat.dto.response;


import com.booktory.booktoryserver.Chat.domain.ChatListEntity;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomResponseDTO {
    private Long userId;
    private List<ChatListEntity> chatList;
}
