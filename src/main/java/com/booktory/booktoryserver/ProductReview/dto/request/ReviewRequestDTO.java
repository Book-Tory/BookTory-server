package com.booktory.booktoryserver.ProductReview.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Data
@ToString
@Builder
public class ReviewRequestDTO {

    private Long review_id;

    private String review_content;

    private String review_file_check;

    private MultipartFile review_image;

    private String review_original_image; // 오리지널 이미지 이름

    private String review_stored_image; // 서버 저장 이미지

}
