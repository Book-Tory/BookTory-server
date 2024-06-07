package com.booktory.booktoryserver.config;

import com.booktory.booktoryserver.config.constants.SecurityConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class JWTTokenGeneratorFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(null != authentication){
            SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
            String jwt = Jwts.builder()
                    .setIssuer("Book Tory") // 토큰 발행자 설정
                    .setSubject("JWT Token") // 토큰 주제 설정
                    .claim("username", authentication.getName()) // 사용자 이름 클레임 추가
                    .claim("authorities", populateAuthorities(authentication.getAuthorities())) // 사용자 권한 클레임 추가
                    .setIssuedAt(new Date()) // 토큰 발행 시간 설정
                    .setExpiration(new Date((new Date()).getTime() + 30000)) // 토큰 만료 시간 설정
                    .signWith(key) // 서명 키로 토큰 서명
                    .compact(); // 토큰 생성

            response.setHeader(SecurityConstants.JWT_HEADER, jwt);

        }
    }


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        //        return !request.getServletPath().equals("/user");
        // 즉, 항상 필터가 실행되도록 false를 반환합니다.
        return false;
    }

    private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<String> authoritiesSet = new HashSet<>();

        for(GrantedAuthority authority : authorities) {
            authoritiesSet.add(authority.getAuthority());
        }

        return String.join(",", authoritiesSet);
    }
}
