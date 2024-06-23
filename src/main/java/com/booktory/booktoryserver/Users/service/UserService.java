package com.booktory.booktoryserver.Users.service;

import com.booktory.booktoryserver.Chat.mapper.ChatMapper;
import com.booktory.booktoryserver.Users.dto.request.ProfileDTO;
import com.booktory.booktoryserver.Users.dto.request.UserRegisterDTO;
import com.booktory.booktoryserver.Users.dto.response.UserResponseDTO;
import com.booktory.booktoryserver.Users.mapper.UserMapper;
import com.booktory.booktoryserver.Users.model.UserEntity;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONUtil;
import org.springframework.security.authentication.BadCredentialsException;
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

        System.out.println("username111 " + username);

        Optional<UserEntity> userData = userMapper.findByEmail(username);

        if (userData.isPresent()) {
            UserEntity user = userData.get();
            return new CustomUserDetails(user);
        } else {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username);
        }
    }


    public List<UserResponseDTO> findByAllUser(){
        List<UserEntity> users = userMapper.findAllUsers();

        return users.stream().map(UserResponseDTO::toUserResponseDTO).collect(Collectors.toList());
    }

    public int deleteUserById(Long userId) {
        chatMapper.deleteBySellerId(userId);
        return userMapper.deleteUserById(userId);
    }

    public int updateById(ProfileDTO profileDTO, Long user_id) {

        UserEntity userUpdate = UserEntity.updateById(profileDTO, user_id);

        return userMapper.updateUserById(userUpdate);
    }
}
