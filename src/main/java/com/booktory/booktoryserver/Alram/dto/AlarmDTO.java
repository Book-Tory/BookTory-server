package com.booktory.booktoryserver.Alram.dto;

import com.booktory.booktoryserver.Alram.domain.AlarmEntity;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlarmDTO {
    private Long alarm_id;
    private Long source_user_id;
    private Long destination_user_id;
    private Long entity_id;
    private Long entity_type_id;
    private Boolean is_read;
    private Date create_date;
    private String message;
    private String content;
    private String user_nickname;


    public static AlarmDTO toDTO(AlarmEntity alarmEntity, String message, String user_nickname, String content) {
        return AlarmDTO.builder()
                .source_user_id(alarmEntity.getSource_user_id())
                .destination_user_id(alarmEntity.getDestination_user_id())
                .entity_id(alarmEntity.getEntity_id())
                .entity_type_id(alarmEntity.getEntity_type_id())
                .is_read(alarmEntity.getIs_read())
                .create_date(alarmEntity.getCreate_date())
                .content(content)
                .message(message)
                .user_nickname(user_nickname)
                .build();
    }
}
