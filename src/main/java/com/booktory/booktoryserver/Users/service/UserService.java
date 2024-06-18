package com.booktory.booktoryserver.Users.service;

import com.booktory.booktoryserver.Chat.mapper.ChatMapper;
import com.booktory.booktoryserver.Users.dto.request.UserRegisterDTO;
import com.booktory.booktoryserver.Users.dto.response.UserResponseDTO;
import com.booktory.booktoryserver.Users.mapper.UserMapper;
import com.booktory.booktoryserver.Users.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    private final ChatMapper chatMapper;


    public int register(UserRegisterDTO userRegisterDTO) {

        Optional<UserEntity> existsUser = userMapper.findByEmail(userRegisterDTO.getUser_email());

        if (existsUser.isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        userRegisterDTO.setUser_password(passwordEncoder.encode(userRegisterDTO.getUser_password()));

        UserEntity user = UserEntity.createUser(userRegisterDTO);

        return userMapper.insertUser(user);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userData = userMapper.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 사용자입니다."));

        if(userData != null) {
            return new CustomUserDetails(userData);
        }

        return null;
    }



    public List<UserResponseDTO> findByAllUser(){
        List<UserEntity> users = userMapper.findAllUsers();

        return users.stream().map(UserResponseDTO::toUserResponseDTO).collect(Collectors.toList());
    }

    public int deleteUserById(Long userId) {
        chatMapper.deleteBySellerId(userId);
        return userMapper.deleteUserById(userId);
    }
}
