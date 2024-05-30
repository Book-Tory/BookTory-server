package com.booktory.booktoryserver.UsedBook.domain;

import lombok.*;

import java.util.Date;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UsedBookPostEntity {
    // 글 정보
    private Long used_book_id;
    private String title;
    private int discount;
    private String description;
    private Date created_at;

    // 사용자
    private Long user_id;
    private String user_nickname;

    // 책 상태
    private Long book_condition_id;
    private String condition;

    // 책 정보
    private Long book_id;
    private String book_name;
    private String book_author;
    private int book_price;
    private String book_publisher;
    private Date book_publication_date;
    private Long book_isbn;

}
