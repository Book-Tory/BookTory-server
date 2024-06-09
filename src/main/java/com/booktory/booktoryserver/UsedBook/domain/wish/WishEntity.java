package com.booktory.booktoryserver.UsedBook.domain.wish;

import lombok.*;

import java.util.Date;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WishEntity {
    private Long used_book_wish_id;
    private Date wish_date; // 찜한 날짜
    private Long user_id; // 찜한 사람 id
    private Long used_book_id; // 찜한 중고서적 글 id
}
