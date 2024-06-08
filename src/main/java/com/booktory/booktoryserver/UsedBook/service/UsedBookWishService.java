package com.booktory.booktoryserver.UsedBook.service;

import com.booktory.booktoryserver.UsedBook.domain.wish.WishEntity;
import com.booktory.booktoryserver.UsedBook.mapper.UsedBookWishMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsedBookWishService {
    private final UsedBookWishMapper usedBookWishMapper;

    public boolean isWished(Long used_book_id, Long user_id) {
        return usedBookWishMapper.isWished(used_book_id, user_id);
    }
    public boolean addOrRemoveWish(Long used_book_id, Long user_id) {
        // 이미 찜 했다면 삭제
        if (isWished(used_book_id, user_id)) {
            usedBookWishMapper.removeWish(used_book_id, user_id);
            return false;
        } else {
            // 찜 안 했으면 추가
            usedBookWishMapper.addWish(used_book_id, user_id);
            return true;
        }
    }
}
