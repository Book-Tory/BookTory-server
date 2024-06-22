package com.booktory.booktoryserver.Users.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    private String role;
    private String name;
    private String userName;
    private String userEmail;

}