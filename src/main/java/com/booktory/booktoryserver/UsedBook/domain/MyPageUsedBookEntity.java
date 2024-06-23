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
public class MyPageUsedBookEntity {
    // 글 정보
    private Long used_book_id;
    private String title;
    private int discount;
    private String description;
    private Date created_at;

    // 상품 상태
    private Long status; // 1: 판매중, 2: 판매중, 3: 판매완료
}
