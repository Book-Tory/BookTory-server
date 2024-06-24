package com.booktory.booktoryserver.Users.filter;

import com.booktory.booktoryserver.Users.mapper.RefreshMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@RequiredArgsConstructor
public class CustomLogoutFilter extends GenericFilterBean {

    private final JWTUtil jwtUtil;
    private final RefreshMapper refreshMapper;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // path and method verify
        String requestUri = httpRequest.getRequestURI();

        if (!requestUri.matches("^(/api)?/logout$")) {
            filterChain.doFilter(request, response);
            return;
        }

        String requestMethod = httpRequest.getMethod();

        if (!requestMethod.equals("POST")) {
            filterChain.doFilter(request, response);
            return;
        }

        // get refresh token
        String refresh = null;
        Cookie[] cookies = httpRequest.getCookies();


        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("refresh")) {
                    refresh = cookie.getValue();
                }
            }
        }
        // refresh null check
        if (refresh == null) {
            httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // expired check
        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // 토큰이 refresh인지 확인 (발급시 페이로드에 명시)
        String category = jwtUtil.getCategory(refresh);
        if (!category.equals("refresh")) {
            httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }


        // DB에 저장되어 있는지 확인
        Boolean isExist = refreshMapper.existsByRefresh(refresh);
        if (!isExist) {
            httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }


        // 로그아웃 진행
        // Refresh 토큰 DB에서 제거
        refreshMapper.deleteByRefresh(refresh);

        // Refresh 토큰 Cookie 값 0
        Cookie cookie = new Cookie("refresh", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        //cookie.setDomain("52.78.9.158");  // 52.78.9.158 이거는 토큰발급됨
        cookie.setHttpOnly(true);
        httpResponse.addCookie(cookie);
        httpResponse.setStatus(HttpServletResponse.SC_OK);
    }
}
