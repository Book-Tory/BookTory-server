package com.booktory.booktoryserver.UsedBook.controller;

import com.booktory.booktoryserver.UsedBook.domain.wish.WishListEntity;
import com.booktory.booktoryserver.UsedBook.dto.response.WishResponseDTO;
import com.booktory.booktoryserver.UsedBook.service.UsedBookWishService;
import com.booktory.booktoryserver.Users.mapper.UserMapper;
import com.booktory.booktoryserver.Users.model.UserEntity;
import com.booktory.booktoryserver.common.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/used-books-wish")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "UsedBookWish API", description = "중고 책 찜 관련 API")
public class UsedBookWishController {
    private final UsedBookWishService usedBookWishService;
    private final UserMapper userMapper;

    @Operation(summary = "찜 상태 확인", description = "특정 중고 책의 찜 상태를 확인합니다.")
    @GetMapping("/check")
    public CustomResponse isWished(@RequestParam("used_book_id") @Parameter(description = "중고 책 ID") Long used_book_id, @AuthenticationPrincipal UserDetails user) {
        if (user == null) {
            return CustomResponse.ok("사용자가 인증되지 않았습니다.", false);
        }

        String username = user.getUsername();
        boolean isWished = usedBookWishService.isWished(used_book_id, username);
        Optional<UserEntity> userEntity = userMapper.findByEmail(username);

        if (userEntity.isEmpty()) {
            return CustomResponse.ok("사용자를 찾을 수 없습니다.", false);
        }

        Long userId = userEntity.get().getUser_id();
        WishResponseDTO response = new WishResponseDTO(isWished, userId);

        if (isWished) {
            return CustomResponse.ok("찜 되어있음", response);
        } else {
            return CustomResponse.ok("찜 안 되어있음", response);
        }
    }

    @Operation(summary = "찜 목록 조회", description = "마이페이지 찜 목록 조회합니다.")
    @GetMapping("/list")
    public CustomResponse getWishList(@AuthenticationPrincipal UserDetails user) {
        String userEmail = user.getUsername();

        Optional<UserEntity> userEntity = userMapper.findByEmail(userEmail);

        Long userId = userEntity.get().getUser_id();

        List<WishListEntity> wishList = usedBookWishService.getWishList(userId);

        if (!wishList.isEmpty()) {
            return CustomResponse.ok("찜 목록 조회 성공", wishList);
        } else {
            return CustomResponse.failure("찜 목록 조회 실패");
        }
    }

    @Operation(summary = "찜 추가/삭제", description = "특정 중고 책의 찜 상태를 확인 후, 찜 추가 및 삭제 기능을 수행합니다.")
    @PostMapping("/wish")
    public CustomResponse addOrRemoveWish(@RequestParam("used_book_id") @Parameter(description = "중고 책 ID") Long used_book_id, @AuthenticationPrincipal UserDetails user) {
        String username = user.getUsername();
        boolean addOrRemove = usedBookWishService.addOrRemoveWish(used_book_id, username);
        if (addOrRemove) {
            return CustomResponse.ok("찜 추가", null);
        } else {
            return CustomResponse.ok("찜 삭제", null);
        }
    }
}
