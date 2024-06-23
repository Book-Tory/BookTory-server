package com.booktory.booktoryserver.Users.mapper;

import com.booktory.booktoryserver.Users.model.UserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserMapper {
    int insertUser(UserEntity user);

    Optional<UserEntity> findByEmail(String email);

    List<UserEntity> findAllUsers();

    int deleteUserById(Long userId);

    UserEntity findByUserName(String user_name);

    int updateUserById(UserEntity user);

}
