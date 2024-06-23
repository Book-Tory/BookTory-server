package com.booktory.booktoryserver.Users.dto.request;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProfileDTO {

    private String user_name;

    private String user_nickname;

    private String user_info;

    private String user_address;

}
