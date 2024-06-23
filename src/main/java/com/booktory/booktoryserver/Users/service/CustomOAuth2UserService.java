package com.booktory.booktoryserver.Users.service;


import com.booktory.booktoryserver.Users.dto.request.UserDTO;
import com.booktory.booktoryserver.Users.dto.response.GoogleResponse;
import com.booktory.booktoryserver.Users.dto.response.NaverResponse;
import com.booktory.booktoryserver.Users.dto.response.OAuth2Response;
import com.booktory.booktoryserver.Users.mapper.UserMapper;
import com.booktory.booktoryserver.Users.model.UserEntity;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserMapper userMapper;

    public CustomOAuth2UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        System.out.println("oAuth2User : " + oAuth2User);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        System.out.println("registionId : " + registrationId);

        OAuth2Response oAuth2Response = null;

        if (registrationId.equals("naver")) {

            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
        }
        else if (registrationId.equals("google")) {

            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        }
        else {

            return null;
        }

        String username = oAuth2Response.getProvider() + " " + oAuth2Response.getProviderId();
        System.out.println("username " + username);

        UserEntity existData = userMapper.findByUserName(username);

        if(existData == null) {
            String defaultPassword = UUID.randomUUID().toString();
            String defaultMobile = ""; // 기본 값 설정

            UserEntity user = UserEntity.createOAuth2User(username, oAuth2Response, defaultPassword, defaultMobile);

            System.out.println("user " + user);


            userMapper.insertUser(user);

            UserDTO userDTO = new UserDTO();
            userDTO.setUserName(username);
            userDTO.setName(oAuth2Response.getName());
            userDTO.setUserEmail(oAuth2Response.getEmail());
            userDTO.setRole("ROLE_USER");

            return new CustomOAuth2User(userDTO);
        }

        existData.setUser_email(oAuth2Response.getEmail());
        existData.setUser_nickname(oAuth2Response.getName());

        UserDTO userDTO = new UserDTO();
        userDTO.setUserName(username);
        userDTO.setName(oAuth2Response.getName());
        userDTO.setUserEmail(oAuth2Response.getEmail());
        userDTO.setRole(existData.getUser_role().toString());

        return new CustomOAuth2User(userDTO);
    }
}
