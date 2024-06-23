package com.booktory.booktoryserver.Users.controller;

import com.booktory.booktoryserver.Users.dto.request.ProfileDTO;
import com.booktory.booktoryserver.Users.dto.request.UserDTO;
import com.booktory.booktoryserver.Users.mapper.UserMapper;
import com.booktory.booktoryserver.Users.model.UserEntity;
import com.booktory.booktoryserver.Users.service.UserService;
import com.booktory.booktoryserver.common.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserMapper userMapper;

    private final UserService userService;

    @GetMapping("")
    public CustomResponse<UserEntity> getProfile(@AuthenticationPrincipal UserDetails userDetails) {

        String email = userDetails.getUsername();


        Optional<UserEntity> userExist = userMapper.findByEmail(email);

        if(userExist.isPresent()){
            return CustomResponse.ok("유저 프로필 조회", userExist.get());
        } else {
            return CustomResponse.error("유저 프로필 조회 실패");
        }
    }


    @PutMapping("")
    public CustomResponse<UserEntity> updateProfile(@RequestBody ProfileDTO profileDTO, @AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();

        Optional<UserEntity> userExist = userMapper.findByEmail(email);
        System.out.println("profile " + profileDTO);

        if(userExist.isPresent()) {
            Long user_id = userExist.get().getUser_id();
            userService.updateById(profileDTO, user_id);

            return CustomResponse.ok("유저 프로필 수정", null);
        } else {
            return CustomResponse.error("유저 프로필 수정 실패");
        }
    }
}
