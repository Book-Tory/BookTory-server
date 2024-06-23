package com.booktory.booktoryserver.UsedBook.domain.wish;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WishListEntity {
    private Long used_book_wish_id;
    private Date wish_date; // 찜한 날짜
    private Long user_id; // 찜한 사람 id

    private Long used_book_id; // 찜한 중고서적 글 id
    private String title; // 찜한 중고서적 글 제목
    private int discount; // 찜한 중고서적 글 가격
    private String stored_image_name; // 상품 사진

    private Long seller_id; // 판매자 id
    private String seller_nickname; // 판매자 닉네임
}
