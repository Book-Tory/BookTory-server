package com.booktory.booktoryserver.Alram.domain;

import com.booktory.booktoryserver.Chat.dto.ChatMessageDTO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AlarmEntity {
    private Long alarm_id;
    private Long source_user_id;
    private Long destination_user_id;
    private Long entity_id;
    private Long entity_type_id;
    private Boolean is_read;
    private Date create_date;
    private Long chat_id;

    public static AlarmEntity toAlarmEntity(ChatMessageDTO chatMessage, Long receiverId) {
        return AlarmEntity.builder()
                .source_user_id(chatMessage.getSender_id())
                .destination_user_id(receiverId)
                .entity_id(chatMessage.getChat_message_id())
                .entity_type_id(1L)
                .is_read(false)
                .create_date(new Date())
                .build();
    }
}
