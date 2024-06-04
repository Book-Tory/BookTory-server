package com.booktory.booktoryserver.users.controller;

import com.booktory.booktoryserver.common.CustomResponse;
import com.booktory.booktoryserver.users.dto.request.UserRegisterDTO;
import com.booktory.booktoryserver.users.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/users")
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

    @GetMapping("/test")
    public String test() {
        return "login successful";
    }
}
