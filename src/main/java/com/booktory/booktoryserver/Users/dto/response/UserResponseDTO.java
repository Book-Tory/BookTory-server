package com.booktory.booktoryserver.Users.dto.response;

import com.booktory.booktoryserver.Users.model.UserEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDTO {

    private Long userId;
    private String userEmail;
    private String userNickname;
    private String userAddress;
    private String userRole;
    private LocalDateTime createdAt;

    public static UserResponseDTO toUserResponseDTO(UserEntity userEntity){
        return UserResponseDTO.builder()
               .userId(userEntity.getUser_id())
               .userEmail(userEntity.getUser_email())
               .userNickname(userEntity.getUser_nickname())
               .userAddress(userEntity.getUser_address())
               .userRole(String.valueOf(userEntity.getUser_role()))
               .createdAt(userEntity.getCreate_at())
               .build();
    }
}
