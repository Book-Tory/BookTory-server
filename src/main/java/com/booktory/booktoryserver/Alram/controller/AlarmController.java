package com.booktory.booktoryserver.Alram.controller;

import com.booktory.booktoryserver.Alram.domain.AlarmEntity;
import com.booktory.booktoryserver.Alram.dto.AlarmDTO;
import com.booktory.booktoryserver.Alram.service.AlarmService;
import com.booktory.booktoryserver.Users.mapper.UserMapper;
import com.booktory.booktoryserver.Users.model.UserEntity;
import com.booktory.booktoryserver.common.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
@Tag(name = "Alarm API", description = "알림 관련 API")
public class AlarmController {
    private final AlarmService alarmService;
    private final SimpMessagingTemplate messagingTemplate;
    private final UserMapper userMapper;
    @Operation(summary = "알림 리스트 불러오기", description = "현재 사용자의 알림 리스트를 불러옵니다.")
    @GetMapping("/alarm")
    public CustomResponse getAlarmsByUserId(@AuthenticationPrincipal UserDetails user) {
        String userEmail = user.getUsername();

        Optional<UserEntity> userEntity = userMapper.findByEmail(userEmail);

        Long userId = userEntity.get().getUser_id();

        List<AlarmDTO> alarms = alarmService.getAlarmsByUserId(userId);

        return CustomResponse.ok("조회 성공", alarms);
    }

    @MessageMapping("/alarm/{userId}")
    public void message(@DestinationVariable("userId") Long userId) {
        messagingTemplate.convertAndSend("/queue/alarm/" + userId, "alarm socket connection completed.");
    }

    @GetMapping("/user")
    public CustomResponse getUserId(@AuthenticationPrincipal UserDetails user) {
        String userEmail = user.getUsername();

        Optional<UserEntity> userEntity = userMapper.findByEmail(userEmail);

        Long userId = userEntity.get().getUser_id();

        System.out.println("userId = " + userId);

        return CustomResponse.ok("userId 조회 성공", userId);
    }
}
