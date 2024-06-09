package com.booktory.booktoryserver.Users.service;

import com.booktory.booktoryserver.Users.dto.request.UserRegisterDTO;
import com.booktory.booktoryserver.Users.mapper.UserMapper;
import com.booktory.booktoryserver.Users.model.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;


    public int register(UserRegisterDTO userRegisterDTO) {


        Optional<Users> existsUser = userMapper.findByEmail(userRegisterDTO.getUser_email());


        if (existsUser.isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        userRegisterDTO.setUser_password(passwordEncoder.encode(userRegisterDTO.getUser_password()));

        Users user = Users.createUser(userRegisterDTO);

        return userMapper.insertUser(user);
    }


}
