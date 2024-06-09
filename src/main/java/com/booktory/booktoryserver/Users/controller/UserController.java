package com.booktory.booktoryserver.Users.controller;

import com.booktory.booktoryserver.Users.model.Users;
import com.booktory.booktoryserver.common.CustomResponse;
import com.booktory.booktoryserver.Users.dto.request.UserRegisterDTO;
import com.booktory.booktoryserver.Users.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public CustomResponse<String> register(@Valid @RequestBody UserRegisterDTO userRegisterDTO, BindingResult bindingResult) {


        if(bindingResult.hasErrors()){
            return CustomResponse.failure(bindingResult.getAllErrors().get(0).getDefaultMessage());
        } else {
            int result = userService.register(userRegisterDTO);
            if (result > 0) {
                return CustomResponse.ok("등록되었습니다.", null);
            } else {
                return CustomResponse.failure("등록에 실패하였습니다.");
            }
        }
    };

//    @PostMapping("/login")
//    public CustomResponse authenticate(@RequestBody AuthRequest login) {
//
//        Users user = Users.builder()
//                .user_email(login.email())
//                .user_password(login.password())
//                .build();
//
//        try {
//            return CustomResponse.ok("로그인 성공", userService.authenticate(user));
//        } catch (Exception e) {
//            return CustomResponse.failure("로그인 실패: " + e.getMessage());
//        }
//    }
}
