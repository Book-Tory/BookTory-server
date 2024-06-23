package com.booktory.booktoryserver.Alram.mapper;

import com.booktory.booktoryserver.Alram.domain.AlarmEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AlarmMapper {
    void saveAlarm(AlarmEntity alarmEntity);

    List<AlarmEntity> getAlarmsByUserId(Long userId);

    String findUserNicknameById(Long sourceUserId);

    Long getChatIdByMessageId(Long entityId);
}
