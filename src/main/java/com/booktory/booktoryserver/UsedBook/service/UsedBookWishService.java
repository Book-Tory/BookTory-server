package com.booktory.booktoryserver.UsedBook.service;

import com.amazonaws.services.s3.AmazonS3;
import com.booktory.booktoryserver.Chat.domain.ChatListEntity;
import com.booktory.booktoryserver.UsedBook.domain.wish.WishEntity;
import com.booktory.booktoryserver.UsedBook.domain.wish.WishListEntity;
import com.booktory.booktoryserver.UsedBook.mapper.UsedBookMapper;
import com.booktory.booktoryserver.UsedBook.mapper.UsedBookWishMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsedBookWishService {
    private final UsedBookWishMapper usedBookWishMapper;
    private final UsedBookMapper usedBookMapper;
    private final AmazonS3 amazonS3;
    @Value("${spring.s3.bucket}")
    private String bucketName;


    public boolean isWished(Long used_book_id, String username) {
        Long userId = usedBookMapper.findIdByEmail(username);

        return usedBookWishMapper.isWished(used_book_id, userId);
    }
    public boolean addOrRemoveWish(Long used_book_id, String username) {
        Long userId = usedBookMapper.findIdByEmail(username);

        // 이미 찜 했다면 삭제
        if (isWished(used_book_id, username)) {
            usedBookWishMapper.removeWish(used_book_id, userId);
            return false;
        } else {
            // 찜 안 했으면 추가
            usedBookWishMapper.addWish(used_book_id, userId);
            return true;
        }
    }

    public List<WishListEntity> getWishList(Long userId) {
        List<WishListEntity> wishListEntity = usedBookWishMapper.getWishList(userId);

        String url = "";

        for(WishListEntity wishList : wishListEntity) {
            if (wishList.getStored_image_name() != null) {
                url = amazonS3.getUrl(bucketName, "profile/" + wishList.getStored_image_name()).toString();
                wishList.setStored_image_name(url);
            }
        }

        return wishListEntity;
    }
}