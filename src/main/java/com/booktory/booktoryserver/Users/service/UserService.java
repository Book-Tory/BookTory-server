package com.booktory.booktoryserver.Users.service;

import com.booktory.booktoryserver.Users.dto.request.UserRegisterDTO;
import com.booktory.booktoryserver.Users.dto.response.AuthResponse;
import com.booktory.booktoryserver.Users.mapper.UserMapper;
import com.booktory.booktoryserver.Users.model.Users;
import com.booktory.booktoryserver.config.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    public int register(UserRegisterDTO userDTO) {

        userDTO.setUser_password(passwordEncoder.encode(userDTO.getUser_password()));
        Users user = Users.createUser(userDTO);

        return userMapper.insertUser(user);
    }

    public AuthResponse authenticate(Users user) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUser_email(), user.getUser_password())
        );

        return new AuthResponse(jwtService.generateToken(user));
    }


}
