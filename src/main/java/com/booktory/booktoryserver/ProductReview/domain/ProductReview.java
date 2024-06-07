package com.booktory.booktoryserver.ProductReview.domain;

import com.booktory.booktoryserver.ProductReview.dto.request.ReviewRequestDTO;
import com.booktory.booktoryserver.products_shop.dto.request.ProductRegisterDTO;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ProductReview {

    private Long product_review_id; // 리뷰 아이디

    private String user_nickname; // 유저 아이디

    private Long product_id; // 상품 아이디

    private String review_content; // 리뷰 내용

    private String review_file_check; // 이미지 체크 여부

    private String review_original_image; // 오리지널 이미지 이름

    private String review_stored_image; // 서버 저장 이미지



    public static ProductReview toReviewEntity(ReviewRequestDTO reviewRequestDTO, String user_nickname){
        return ProductReview.builder()
                .user_nickname(user_nickname)
                .product_id(19L)
                .review_content(reviewRequestDTO.getReview_content())
                .review_file_check(reviewRequestDTO.getReview_file_check())
                .review_original_image(reviewRequestDTO.getReview_original_image())
                .review_stored_image(reviewRequestDTO.getReview_stored_image())
                .build();
    }
}
