package com.booktory.booktoryserver.ProductReview.controller;

import com.booktory.booktoryserver.ProductReview.dto.request.ReviewRequestDTO;
import com.booktory.booktoryserver.ProductReview.dto.response.ProductReviewResponseDTO;
import com.booktory.booktoryserver.ProductReview.service.ReviewService;
import com.booktory.booktoryserver.common.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;


    @PostMapping("/register")
    public CustomResponse register(@ModelAttribute ReviewRequestDTO reviewRequestDTO) throws IOException {

        int result = reviewService.register(reviewRequestDTO , "bsc7386@naver.com");

        if (result > 0) {
            return CustomResponse.ok("리뷰 작성되었습니다.", null);
        } else {
            return CustomResponse.failure("리뷰 작성 실패하였습니다.");
        }
    }

    @GetMapping("product/{product_id}")
    public CustomResponse getReviewByProductId(@PathVariable("product_id") Long product_id) {
         List<ProductReviewResponseDTO> productReview = reviewService.getReviewByProductId(product_id);
        return CustomResponse.ok("상품별 리뷰 조회 성공", productReview);
    }

}
