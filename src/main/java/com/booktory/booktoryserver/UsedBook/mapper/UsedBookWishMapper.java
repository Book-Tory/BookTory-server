package com.booktory.booktoryserver.UsedBook.mapper;

import com.booktory.booktoryserver.UsedBook.domain.wish.WishEntity;
import com.booktory.booktoryserver.UsedBook.domain.wish.WishListEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UsedBookWishMapper {
    boolean isWished(Long used_book_id, Long user_id);
    void addWish(Long used_book_id, Long user_id);
    void removeWish(Long used_book_id, Long user_id);

    List<WishListEntity> getWishList(Long userId);
}
