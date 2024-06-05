package com.booktory.booktoryserver.Users.service;

import com.booktory.booktoryserver.Users.dto.request.UserRegisterDTO;
import com.booktory.booktoryserver.Users.mapper.UserMapper;
import com.booktory.booktoryserver.Users.model.Users;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    public int register(UserRegisterDTO userDTO) {

        userDTO.setUser_password(passwordEncoder.encode(userDTO.getUser_password()));
        Users user = Users.createUser(userDTO);

        return userMapper.insertUser(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Username : " + username);

        String userName = null;
        String password = null;
        List<GrantedAuthority> authorities = new ArrayList<>();
        Optional<Users> userOptional = userMapper.findByEmail(username);

        Users user = userOptional.orElseThrow(() -> new UsernameNotFoundException(username));
        userName = user.getUser_email();
        password = user.getUser_password();
        authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getUser_role().getKey()));

        return new User(userName, password, authorities);
    }
}
