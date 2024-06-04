package com.booktory.booktoryserver.users.dto.request;

import com.booktory.booktoryserver.users.constant.Gender;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegisterDTO {

    private Long id; // 사용자 고유 ID

    @Email(message = "올바른 이메일 형식이 아닙니다")
    @NotBlank(message = "이메일은 필수 입력 항목입니다")
    private String user_email; // 사용자 이메일

    @NotBlank(message = "비밀번호는 필수 입력 항목입니다")
    @Pattern(regexp = "^(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,}$", message = "비밀번호는 특수문자를 포함하여 최소 8자 이상이어야 합니다")
    private String user_password; // 사용자 비밀번호


    @NotBlank(message = "닉네임은 필수 입력 항목입니다")
    private String user_nickname; // 사용자 닉네임

    @NotBlank(message = "휴대전화 번호는 필수 입력 항목입니다")
    @Pattern(regexp = "^\\d{10,11}$", message = "휴대전화 번호 형식이 잘못되었습니다")
    private String user_mobile; // 사용자 휴대전화 번호

    @NotNull(message = "성별은 필수 입력 항목입니다")
    private Gender user_gender; // 사용자 성별 (MALE, FEMALE, OTHER)

    @NotNull(message = "생년월일은 필수 입력 항목입니다")
    @Past(message = "생년월일은 과거 날짜여야 합니다")
    private LocalDateTime user_birth; // 사용자 생년월일

    @NotBlank(message = "주소는 필수 입력 항목입니다")
    private String user_address; // 사용자 주소

}
