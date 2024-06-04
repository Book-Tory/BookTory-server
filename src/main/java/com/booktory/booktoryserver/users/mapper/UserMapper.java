package com.booktory.booktoryserver.users.mapper;

import com.booktory.booktoryserver.users.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    int insertUser(User user);
}
