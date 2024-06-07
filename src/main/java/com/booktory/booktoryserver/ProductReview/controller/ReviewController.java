package com.booktory.booktoryserver.ProductReview.controller;

import com.booktory.booktoryserver.ProductReview.dto.request.ReviewRequestDTO;
import com.booktory.booktoryserver.ProductReview.service.ReviewService;
import com.booktory.booktoryserver.common.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

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

}
