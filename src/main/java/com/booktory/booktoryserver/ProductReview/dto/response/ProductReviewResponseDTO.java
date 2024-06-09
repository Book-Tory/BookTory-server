package com.booktory.booktoryserver.ProductReview.dto.response;


import com.booktory.booktoryserver.ProductReview.domain.ProductReview;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductReviewResponseDTO {

    private Long product_review_id;

    private String user_nickname; // 유저 아이디

    private String review_content; // 리뷰 내용

    private String review_file_check; // 이미지 체크 여부

    private String review_original_image; // 오리지널 이미지 이름

    private String review_stored_image; // 서버 저장 이미지

    private String review_image_url; // 이미지 url

    public static ProductReviewResponseDTO toProductReviewInfo(ProductReview productReview, String url){
        return ProductReviewResponseDTO.builder()
                .product_review_id(productReview.getProduct_review_id())
                .user_nickname(productReview.getUser_nickname())
               .review_content(productReview.getReview_content())
               .review_file_check(productReview.getReview_file_check())
               .review_original_image(productReview.getReview_original_image())
               .review_stored_image(productReview.getReview_stored_image())
               .review_image_url(url)
               .build();

    }


}
