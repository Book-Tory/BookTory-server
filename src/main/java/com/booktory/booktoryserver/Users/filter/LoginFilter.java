package com.booktory.booktoryserver.Users.filter;

import com.booktory.booktoryserver.Users.mapper.RefreshMapper;
import com.booktory.booktoryserver.Users.model.RefreshEntity;
import com.booktory.booktoryserver.Users.service.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private final JWTUtil jwtUtil;

    private final RefreshMapper refreshMapper;


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String username = obtainUsername(request);
        String password = obtainPassword(request);

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);

        return authenticationManager.authenticate(authToken);
    }
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {

        // access token만 했을 때
//        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
//
//        String username = customUserDetails.getUsername();
//
//        Collection<? extends GrantedAuthority> authorities = customUserDetails.getAuthorities();
//        Iterator<? extends  GrantedAuthority> iterator = authorities.iterator();
//
//        String role = iterator.next().getAuthority();
//        String token = jwtUtil.createToken(username, role, 60*60*1000L);
//
//        response.addHeader("Authorization", "Bearer " + token);

        //유저 정보
        String username = authentication.getName();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        //토큰 생성
        String access = jwtUtil.createToken("access", username, role, 300000L);
        String refresh = jwtUtil.createToken("refresh", username, role, 86400000L);

        addRefreshEntity(username, refresh, 86400000L);


        //응답 설정
        response.setHeader("access", access);
        response.addCookie(createCookie("refresh", refresh));
        response.setStatus(HttpStatus.OK.value());
    }

    private Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24*60*60);
        //cookie.setSecure(true);  // https 통신 일때
        cookie.setPath("/");
        cookie.setDomain("localhost");
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

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        response.setStatus(401);
    }
}
