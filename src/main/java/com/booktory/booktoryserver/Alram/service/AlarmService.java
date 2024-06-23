package com.booktory.booktoryserver.Alram.service;

import com.booktory.booktoryserver.Alram.domain.AlarmEntity;
import com.booktory.booktoryserver.Alram.dto.AlarmDTO;
import com.booktory.booktoryserver.Alram.mapper.AlarmMapper;
import com.booktory.booktoryserver.Chat.mapper.ChatMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class AlarmService {
    private final AlarmMapper alarmMapper;
    private final ChatMapper chatMapper;

    public void saveAlarm(AlarmEntity alarmEntity) {
        alarmMapper.saveAlarm(alarmEntity);
    }

    public List<AlarmDTO> getAlarmsByUserId(Long userId) {
        List<AlarmEntity> alarmEntities = alarmMapper.getAlarmsByUserId(userId);

        return alarmEntities.stream()
                .map(this::toAlarmDTO)
                .collect(Collectors.toList());
    }

    private AlarmDTO toAlarmDTO(AlarmEntity alarmEntity) {
        String message = "";
        String user_nickname = "";
        String content ="";
        if (alarmEntity.getEntity_type_id() == 1) {
            Long chatId =  alarmMapper.getChatIdByMessageId(alarmEntity.getEntity_id());
            System.out.println("chatId = " + chatId);
            alarmEntity.setChat_id(chatId);
            content = chatMapper.findById(alarmEntity.getEntity_id());
            message = "채팅이 도착했습니다.";
            user_nickname = alarmMapper.findUserNicknameById(alarmEntity.getSource_user_id());
        } else if (alarmEntity.getEntity_type_id() == 2) {// message = commentMapper.findById(alarmEntity.getEntityId()).getContent();
            message = "댓글을 남겼습니다.";
        } else if (alarmEntity.getEntity_type_id() == 3) {
            message = "좋아요를 받았습니다.";
        }
        return AlarmDTO.toDTO(alarmEntity, message, user_nickname, content);
    }
}
