package com.booktory.booktoryserver.Users.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tokens {

    private Long id;

    private String tokenType; // BEARER

    private boolean expired;

    private boolean revoked;

    private String user_name;
}
