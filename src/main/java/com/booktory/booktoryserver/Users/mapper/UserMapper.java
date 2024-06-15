package com.booktory.booktoryserver.Users.mapper;

import com.booktory.booktoryserver.Users.model.UserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserMapper {
    int insertUser(UserEntity user);

    Optional<UserEntity> findByEmail(String email);

}
