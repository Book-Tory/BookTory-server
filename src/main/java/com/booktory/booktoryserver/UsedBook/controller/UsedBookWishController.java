package com.booktory.booktoryserver.UsedBook.controller;

import com.booktory.booktoryserver.UsedBook.service.UsedBookWishService;
import com.booktory.booktoryserver.common.CustomResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/used-books-wish")
@RequiredArgsConstructor
@Slf4j
public class UsedBookWishController {
    private final UsedBookWishService usedBookWishService;

    // 찜하기
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
