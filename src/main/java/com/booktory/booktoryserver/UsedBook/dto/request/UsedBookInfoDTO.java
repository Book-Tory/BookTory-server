package com.booktory.booktoryserver.UsedBook.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsedBookInfoDTO {
    private Long used_book_id; // 중고서적 id
    private String title; // 중고서적 글 제목
    private int discount; // 중고서적 가격
    private String description; // 중고서적 상세설명
    private Date created_at; // 글 쓴 날짜
    private Long user_id; // 판매자 user_id
    private Long book_condition_id; // 책 상태 id
    private Long book_id; // 책 정보 id
    private List<MultipartFile> used_book_image; // 판매자가 올리는 중고서적 이미지
    private int image_check; // 파일 첨부 0, 파일 첨부 1
    private List<Long> delete_Images; // 삭제할 이미지 pk가 담긴 리스트
    private List<MultipartFile> update_images; // 추가할 이미지
}
