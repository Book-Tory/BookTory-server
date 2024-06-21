package com.booktory.booktoryserver.UsedBook.dto.response;

import com.booktory.booktoryserver.UsedBook.domain.UsedBookPostEntity;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsedBookPostDTO {
    // 글 정보
    private Long used_book_id;
    private String title;
    private int discount;
    private String description;
    private Date created_at;

    // 이미지
    private int image_check;
    private List<Map<Long,String>> imageUrls; // 이미지 url이 담긴 리스트

    // 사용자
    private Long user_id;
    private String user_nickname;

    // 책 상태
    private Long book_condition_id;
    private String condition;

    // 상품 상태
    private int status; // 1: 판매중, 2: 판매중, 3: 판매완료

    // 책 정보
    private Long book_id;
    private String book_name;
    private String book_author;
    private int book_price;
    private String book_publisher;
    private Date book_publication_date;
    private Long book_isbn;

    public static UsedBookPostDTO toDTO(UsedBookPostEntity usedBook, List<Map<Long,String>> imageUrls) {
        return UsedBookPostDTO.builder()
                .used_book_id(usedBook.getUsed_book_id())
                .title(usedBook.getTitle())
                .discount(usedBook.getDiscount())
                .description(usedBook.getDescription())
                .created_at(usedBook.getCreated_at())
                .image_check(usedBook.getImage_check())
                .imageUrls(imageUrls)
                .status(usedBook.getStatus())
                .user_id(usedBook.getUser_id())
                .user_nickname(usedBook.getUser_nickname())
                .book_condition_id(usedBook.getBook_condition_id())
                .condition(usedBook.getCondition())
                .book_id(usedBook.getBook_id())
                .book_name(usedBook.getBook_name())
                .book_author(usedBook.getBook_author())
                .book_price(usedBook.getBook_price())
                .book_publisher(usedBook.getBook_publisher())
                .book_publication_date(usedBook.getBook_publication_date())
                .book_isbn(usedBook.getBook_isbn())
                .build();
    }
}
