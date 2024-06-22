package com.booktory.booktoryserver.Users.handler;

import com.booktory.booktoryserver.Users.filter.JWTUtil;
import com.booktory.booktoryserver.Users.mapper.RefreshMapper;
import com.booktory.booktoryserver.Users.model.RefreshEntity;
import com.booktory.booktoryserver.Users.service.CustomOAuth2User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

@Component
@RequiredArgsConstructor
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JWTUtil jwtUtil;

    private final RefreshMapper refreshMapper;

    //OAuth2User

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        //OAuth2User
        CustomOAuth2User customUserDetails = (CustomOAuth2User) authentication.getPrincipal();

        String username = customUserDetails.getUserEmail();


        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        String accessToken = jwtUtil.createToken("access", username, role, 1800000L); // 30분 유효한 access token 생성
        String refresh = jwtUtil.createToken("refresh", username, role, 86400000L); // 24시간 유효한 refresh token 생성

        addRefreshEntity(username, refresh, 86400000L);

        response.addCookie(createCookie("refresh", refresh));
        response.setStatus(HttpStatus.OK.value());

        String redirectUrl = "http://localhost:5173/login?accessToken=" + accessToken;
        response.sendRedirect(redirectUrl);
    }

    private Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24*60*60);
        //cookie.setSecure(true);  // https 통신 일때
        cookie.setPath("/");
//        cookie.setDomain("52.78.9.158");  // 52.78.9.158 이거는 토큰발급됨
        cookie.setHttpOnly(true);

        return cookie;
    }

    private void addRefreshEntity(String username, String refresh, Long expiredMs){
        Date date = new Date(System.currentTimeMillis() + expiredMs);

        RefreshEntity refreshEntity = RefreshEntity.builder()
                .username(username)
                .refresh(refresh)
                .expiration(date.toString())
                .build();

        refreshMapper.save(refreshEntity);
    }
}
