package com.booktory.booktoryserver.users.mapper;

import com.booktory.booktoryserver.users.model.Users;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserMapper {
    int insertUser(Users user);

    Optional<Users> findByEmail(String email);
}
