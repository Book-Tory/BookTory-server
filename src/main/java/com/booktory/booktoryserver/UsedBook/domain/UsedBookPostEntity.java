package com.booktory.booktoryserver.UsedBook.domain;

import com.booktory.booktoryserver.UsedBook.dto.request.UsedBookInfoDTO;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Getter
@Setter
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

    // 이미지
    private Long used_book_image_id;
    private String original_image_name;
    private String stored_image_name;
    private int image_check;
    private List<Map<Long, String>> imageUrls; // 이미지 url이 담긴 리스트

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

    // 수정
    public static UsedBookPostEntity toUpdateEntity(Long used_book_id, UsedBookInfoDTO usedBookInfoDTO, Long bookId) {
        return UsedBookPostEntity.builder()
                .used_book_id(used_book_id)
                .title(usedBookInfoDTO.getTitle())
                .discount(usedBookInfoDTO.getDiscount())
                .description(usedBookInfoDTO.getDescription())
                .book_id(bookId)
                .book_condition_id(usedBookInfoDTO.getBook_condition_id())
                .image_check(usedBookInfoDTO.getImage_check())
                .build();
    }

    // 생성
    public static UsedBookPostEntity toCreateEntity(UsedBookInfoDTO usedBookInfoDTO, Long bookId) {
        return UsedBookPostEntity.builder()
                .title(usedBookInfoDTO.getTitle())
                .discount(usedBookInfoDTO.getDiscount())
                .description(usedBookInfoDTO.getDescription())
                .book_id(bookId)
                .user_id(usedBookInfoDTO.getUser_id())
                .book_condition_id(usedBookInfoDTO.getBook_condition_id())
                .image_check(usedBookInfoDTO.getImage_check())
                .build();
    }
}
