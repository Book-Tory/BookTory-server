package com.booktory.booktoryserver.ProductReview.controller;

import com.booktory.booktoryserver.ProductReview.dto.request.ReviewRequestDTO;
import com.booktory.booktoryserver.ProductReview.dto.response.ProductReviewResponseDTO;
import com.booktory.booktoryserver.ProductReview.service.ReviewService;
import com.booktory.booktoryserver.common.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
@Tag(name = "Review API", description = "리뷰 관련 API")
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(summary = "리뷰 등록", description = "새로운 리뷰를 등록합니다.")
    @PostMapping("/register")
    public CustomResponse register(@ModelAttribute @Parameter(description = "리뷰 정보") ReviewRequestDTO reviewRequestDTO) throws IOException {
        int result = reviewService.register(reviewRequestDTO , "bsc7386@naver.com");
        if (result > 0) {
            return CustomResponse.ok("리뷰 작성되었습니다.", null);
        } else {
            return CustomResponse.failure("리뷰 작성 실패하였습니다.");
        }
    }

    @Operation(summary = "상품별 리뷰 조회", description = "특정 상품의 리뷰를 조회합니다.")
    @GetMapping("/product/{product_id}")
    public CustomResponse getReviewByProductId(@Parameter(description = "상품 ID") @PathVariable("product_id") Long product_id) {
        List<ProductReviewResponseDTO> productReview = reviewService.getReviewByProductId(product_id);
        return CustomResponse.ok("상품별 리뷰 전체 조회 성공", productReview);
    }

    @Operation(summary = "리뷰 삭제", description = "특정 리뷰를 삭제합니다.")
    @DeleteMapping("/{product_review_id}")
    public CustomResponse deleteReview(@Parameter(description = "리뷰 ID") @PathVariable("product_review_id") Long product_review_id){
        int result = reviewService.deleteByReviewId(product_review_id);
        if(result > 0){
            return CustomResponse.ok("리뷰 삭제 성공", null);
        } else {
            return CustomResponse.failure("리뷰 삭제 실패하였습니다.");
        }
    }
}
