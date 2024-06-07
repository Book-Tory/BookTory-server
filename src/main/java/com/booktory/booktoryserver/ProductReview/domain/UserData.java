package com.booktory.booktoryserver.ProductReview.domain;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserData {

    private Long user_id; // 유저 아이디

    private String user_email; // 유저 이메일

    private String user_nickname; // 유저 닉네임

}
