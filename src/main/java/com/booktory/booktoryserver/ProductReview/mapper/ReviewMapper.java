package com.booktory.booktoryserver.ProductReview.mapper;

import com.booktory.booktoryserver.ProductReview.domain.ProductReview;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReviewMapper {
    int register(ProductReview productReview);

    List<ProductReview> getReviewByProductId(Long productId);

    ProductReview findByReviewId(Long productReviewId);

    int deleteByReviewId(Long productReviewId);
}
