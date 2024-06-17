package com.booktory.booktoryserver.UsedBook.controller;

import com.booktory.booktoryserver.UsedBook.dto.response.WishResponseDTO;
import com.booktory.booktoryserver.UsedBook.service.UsedBookWishService;
import com.booktory.booktoryserver.Users.mapper.UserMapper;
import com.booktory.booktoryserver.Users.model.UserEntity;
import com.booktory.booktoryserver.common.CustomResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/used-books-wish")
@RequiredArgsConstructor
@Slf4j
public class UsedBookWishController {
    private final UsedBookWishService usedBookWishService;
    private final UserMapper userMapper;
    // 찜 상태
    @GetMapping("/check")
    public CustomResponse isWished(@RequestParam ("used_book_id") Long used_book_id, @AuthenticationPrincipal UserDetails user) {
        String username = user.getUsername();

        boolean isWished = usedBookWishService.isWished(used_book_id, username);

        Optional<UserEntity> userEntity = userMapper.findByEmail(username);

        Long userId = userEntity.get().getUser_id();

        WishResponseDTO response = new WishResponseDTO(isWished, userId);
        if (isWished) {
            return CustomResponse.ok("찜 되어있음", response);
        } else {
            return CustomResponse.ok("찜 안 되어있음", response);
        }
    }

    // 찜 여부 확인 후, 찜 추가 및 삭제 기능
    @PostMapping("/wish")
    public CustomResponse addOrRemoveWish(@RequestParam ("used_book_id") Long used_book_id, @AuthenticationPrincipal UserDetails user) {
        String username = user.getUsername();

        boolean addOrRemove = usedBookWishService.addOrRemoveWish(used_book_id, username);

        if (addOrRemove) {
            return CustomResponse.ok("찜 추가", null);
        } else {
            return CustomResponse.ok("찜 삭제", null);
        }
    }
}