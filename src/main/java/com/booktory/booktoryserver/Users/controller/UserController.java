package com.booktory.booktoryserver.Users.controller;

import com.booktory.booktoryserver.Users.dto.response.UserResponseDTO;
import com.booktory.booktoryserver.Users.service.CustomUserDetails;
import com.booktory.booktoryserver.common.CustomResponse;
import com.booktory.booktoryserver.Users.dto.request.UserRegisterDTO;
import com.booktory.booktoryserver.Users.service.UserService;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/auth")
@RequiredArgsConstructor
@Tag(name = "User API", description = "사용자 관리 API")
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원가입", description = "새로운 사용자를 등록합니다.")
    @PostMapping("/register")
    public CustomResponse<String> register(@Validated @RequestBody @Parameter(description = "사용자 등록 정보") UserRegisterDTO userRegisterDTO, BindingResult bindingResult) {
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

    @Operation(summary = "유저 리스트 조회", description = "모든 유저 정보를 조회합니다.")
    @GetMapping("/list")
    public CustomResponse<List<UserResponseDTO>> register(@AuthenticationPrincipal CustomUserDetails userDetails) {
        String userRole = userDetails.getAuthorities().toString();
        if(!userRole.contains("ADMIN")){
            return CustomResponse.failure("ADMIN이 아닙니다.");
        }
        List<UserResponseDTO> users = userService.findByAllUser();
        return CustomResponse.ok("모든 유저 정보 조회.", users);
    }

    @Operation(summary = "유저 삭제", description = "특정 유저를 삭제합니다.")
    @DeleteMapping("/{userId}")
    public CustomResponse<Void> deleteUser(@Parameter(description = "유저 ID") @PathVariable Long userId) {
        int result = userService.deleteUserById(userId);
        if (result > 0) {
            return CustomResponse.ok("User deleted successfully.", null);
        } else {
            return CustomResponse.failure("User deletion failed.");
        }
    }
}
