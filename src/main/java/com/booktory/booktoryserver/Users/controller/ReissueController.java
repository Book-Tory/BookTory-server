package com.booktory.booktoryserver.Users.controller;

import com.booktory.booktoryserver.Users.filter.JWTUtil;
import com.booktory.booktoryserver.Users.mapper.RefreshMapper;
import com.booktory.booktoryserver.Users.model.RefreshEntity;
import com.booktory.booktoryserver.common.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequiredArgsConstructor
@Tag(name = "Token Reissue API", description = "토큰 재발급 관련 API")
public class ReissueController {

    private final JWTUtil jwtUtil;
    private final RefreshMapper refreshMapper;

    @Operation(summary = "토큰 재발급", description = "리프레시 토큰을 사용하여 새로운 액세스 토큰과 리프레시 토큰을 발급합니다.")
    @PostMapping("/api/reissue")
    public CustomResponse reissue(HttpServletRequest request, HttpServletResponse response){

        // get refresh token
        String refresh = null;
        Cookie[] cookies = request.getCookies();

        System.out.println("cookie " + cookies);

        for(Cookie cookie : cookies){
            if(cookie.getName().equals("refresh")){
                refresh = cookie.getValue();
            }
        }

        System.out.println("refresh: " + refresh);

        if(refresh == null){
            // response status code
            return CustomResponse.failure("refresh token null");
        }

        // expired check
        try{
            jwtUtil.isExpired(refresh);
        } catch(Exception e) {
            // response status code
            return CustomResponse.failure("refresh token expired");
        }

        // 토큰이 refresh인지 확인 (발급시 페이로드에 명시)
        String category = jwtUtil.getCategory(refresh);

        if(!category.equals("refresh")){
            // response status code
            return CustomResponse.failure("invalid refresh token");
        }

        // DB에 저장되어 있는지 확인
        Boolean isExist = refreshMapper.existsByRefresh(refresh);
        if(!isExist){
            return CustomResponse.failure("invalid refresh token");
        }

        // 토큰이 refresh인지 확인 (발급시 페이로드에 명시)
        String username = jwtUtil.getUsername(refresh);
        String role = jwtUtil.getRole(refresh);

        // make new JWT
        String newAccess = jwtUtil.createToken("access", username, role, 1800000L);
        String newRefresh = jwtUtil.createToken("refresh", username, role, 86400000L);

        // Refresh 토큰 저장 DB에 기존의 Refresh 토큰 삭제 후 새 Refresh 토큰 저장
        refreshMapper.deleteByRefresh(refresh);
        addRefreshEntity(username, newRefresh, 86400000L);

        // response
        response.setHeader("access", newAccess);
        response.addCookie(createCookie("refresh", newRefresh));

        return CustomResponse.ok("토큰발급", null);
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24*60*60);
        //cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setDomain("52.78.9.158");
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
