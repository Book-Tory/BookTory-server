package com.booktory.booktoryserver.UsedBook.mapper;

import com.booktory.booktoryserver.UsedBook.domain.wish.WishEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UsedBookWishMapper {
    boolean isWished(Long used_book_id, Long user_id);
    void addWish(Long used_book_id, Long user_id);
    void removeWish(Long used_book_id, Long user_id);
}
