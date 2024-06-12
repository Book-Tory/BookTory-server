package com.booktory.booktoryserver.Users.model;


import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshEntity {

    private Long id;

    private String username;

    private String refresh;

    private String expiration;
}
