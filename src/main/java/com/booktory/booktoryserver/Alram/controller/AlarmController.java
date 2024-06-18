package com.booktory.booktoryserver.Alram.controller;

import com.booktory.booktoryserver.Alram.domain.AlarmEntity;
import com.booktory.booktoryserver.Alram.dto.AlarmDTO;
import com.booktory.booktoryserver.Alram.service.AlarmService;
import com.booktory.booktoryserver.Users.mapper.UserMapper;
import com.booktory.booktoryserver.Users.model.UserEntity;
import com.booktory.booktoryserver.common.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/alarms")
@RequiredArgsConstructor
public class AlarmController {
    private final AlarmService alarmService;

    private final UserMapper userMapper;
    @GetMapping("/alarm")
    public CustomResponse getAlarmsByUserId(@AuthenticationPrincipal UserDetails user) {
        String username = user.getUsername();

        Optional<UserEntity> userEntity = userMapper.findByEmail(username);

        Long userId = userEntity.get().getUser_id();

        List<AlarmDTO> alarms = alarmService.getAlarmsByUserId(userId);

        return CustomResponse.ok("조회 성공", alarms);
    }
}
