package com.booktory.booktoryserver.users.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public enum Gender {

    MALE("MALE"),
    FEMALE("FEMALE"),
    OTHER("OTHER");

    private final String key;

}
