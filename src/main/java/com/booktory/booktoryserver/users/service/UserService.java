package com.booktory.booktoryserver.users.service;

import com.booktory.booktoryserver.users.dto.request.UserRegisterDTO;
import com.booktory.booktoryserver.users.mapper.UserMapper;
import com.booktory.booktoryserver.users.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    public int register(UserRegisterDTO userDTO) {

        log.info("userDTO : {}", userDTO);
        userDTO.setUser_password(passwordEncoder.encode(userDTO.getUser_password()));
        User user = User.createUser(userDTO);

        return userMapper.insertUser(user);
    }
}
