package com.booktory.booktoryserver.UsedBook.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsedBookImage {
    private Long used_book_image_id; // 중고서적 이미지 id
    private Long used_book_id; // 중고서적 id : 외래키
    private String original_image_name; // 원본 파일 이름
    private String stored_image_name; // 서버 저장용 이름
}
