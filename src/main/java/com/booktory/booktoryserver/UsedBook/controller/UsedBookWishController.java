package com.booktory.booktoryserver.UsedBook.controller;

import com.booktory.booktoryserver.UsedBook.service.UsedBookWishService;
import com.booktory.booktoryserver.common.CustomResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/used-books-wish")
@RequiredArgsConstructor
@Slf4j
public class UsedBookWishController {
    private final UsedBookWishService usedBookWishService;
    // 찜 상태
    @GetMapping("/check")
    public CustomResponse isWished(@RequestParam ("used_book_id") Long used_book_id, @RequestParam ("user_id") Long user_id) {
        boolean isWished = usedBookWishService.isWished(used_book_id, user_id);

        if (isWished) {
            return CustomResponse.ok("찜 되어있음", null);
        } else {
            return CustomResponse.ok("찜 안 되어있음", null);
        }
    }

    // 찜 여부 확인 후, 찜 추가 및 삭제 기능
    @PostMapping("/")
    public CustomResponse addOrRemoveWish(@RequestParam ("used_book_id") Long used_book_id, @RequestParam ("user_id") Long user_id) {
        boolean addOrRemove = usedBookWishService.addOrRemoveWish(used_book_id, user_id);

        if (addOrRemove) {
            return CustomResponse.ok("찜 추가", null);
        } else {
            return CustomResponse.ok("찜 삭제", null);
        }
    }
}
