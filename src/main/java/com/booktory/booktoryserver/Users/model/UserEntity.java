package com.booktory.booktoryserver.Users.model;

import com.booktory.booktoryserver.Users.constant.Gender;
import com.booktory.booktoryserver.Users.constant.Role;
import com.booktory.booktoryserver.Users.dto.request.ProfileDTO;
import com.booktory.booktoryserver.Users.dto.request.UserRegisterDTO;
import com.booktory.booktoryserver.Users.dto.response.OAuth2Response;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserEntity {

    private Long user_id; // 사용자 고유 ID

    private String user_email; // 사용자 이메일

    private String user_password; // 사용자 비밀번호

    private String user_nickname; // 사용자 닉네임

    private String user_name; // 사용자 이름

    private String user_mobile; // 사용자 휴대전화 번호

    private String user_gender; // 사용자 성별 (MALE, FEMALE, OTHER)

    private LocalDateTime user_birth; // 사용자 생년월일

    private String user_address; // 사용자 주소

    private String user_info;

    private String user_img;

    private Role user_role; // 사용자 역할 (USER, ADMIN, MODERATOR)

    private LocalDateTime create_at; // 계정 생성 시간

    private LocalDateTime update_at; // 계정 정보 마지막 수정 시간

    public static UserEntity createUser(UserRegisterDTO userRegisterDTO) {
        return UserEntity.builder()
                .user_email(userRegisterDTO.getUser_email())
                .user_password(userRegisterDTO.getUser_password())
                .user_name(userRegisterDTO.getUser_name())
                .user_nickname(userRegisterDTO.getUser_nickname())
                .user_mobile(userRegisterDTO.getUser_mobile())
                .user_gender(userRegisterDTO.getUser_gender())
                .user_birth(userRegisterDTO.getUser_birth())
                .user_address(userRegisterDTO.getUser_address())
                .user_role(Role.USER)
                .build();
    }


    public static UserEntity createOAuth2User(String userName, OAuth2Response oAuth2Response, String defaultPassword, String defaultMobile) {
        return UserEntity.builder()
                .user_name(userName)
                .user_password(defaultPassword)
                .user_mobile(defaultMobile)
                .user_email(oAuth2Response.getEmail())
                .user_nickname(oAuth2Response.getName())
                .user_role(Role.USER)
                .build();
    }

    public static UserEntity updateById(ProfileDTO profileDTO, Long user_id) {
        return UserEntity.builder()
                .user_id(user_id)
                .user_name(profileDTO.getUser_name())
                .user_nickname(profileDTO.getUser_nickname())
                .user_address(profileDTO.getUser_address())
                .user_info(profileDTO.getUser_info())
                .build();

    }
}
