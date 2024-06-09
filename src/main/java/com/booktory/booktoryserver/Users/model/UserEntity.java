package com.booktory.booktoryserver.Users.model;

import com.booktory.booktoryserver.Users.constant.Gender;
import com.booktory.booktoryserver.Users.constant.Role;
import com.booktory.booktoryserver.Users.dto.request.UserRegisterDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity {

    private Long user_id; // 사용자 고유 ID

    private String user_email; // 사용자 이메일

    private String user_password; // 사용자 비밀번호

    private String user_nickname; // 사용자 닉네임

    private String user_mobile; // 사용자 휴대전화 번호

    private Gender user_gender; // 사용자 성별 (MALE, FEMALE, OTHER)

    private LocalDateTime user_birth; // 사용자 생년월일

    private String user_address; // 사용자 주소

    private Role user_role; // 사용자 역할 (USER, ADMIN, MODERATOR)

    private LocalDateTime create_at; // 계정 생성 시간

    private LocalDateTime update_at; // 계정 정보 마지막 수정 시간

    public static UserEntity createUser(UserRegisterDTO userRegisterDTO) {
        return UserEntity.builder()
                .user_email(userRegisterDTO.getUser_email())
                .user_password(userRegisterDTO.getUser_password())
                .user_nickname(userRegisterDTO.getUser_nickname())
                .user_mobile(userRegisterDTO.getUser_mobile())
                .user_gender(userRegisterDTO.getUser_gender())
                .user_birth(userRegisterDTO.getUser_birth())
                .user_address(userRegisterDTO.getUser_address())
                .user_role(Role.USER)
                .build();
    }
}
