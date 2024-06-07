package com.booktory.booktoryserver.ProductReview.mapper;

import com.booktory.booktoryserver.ProductReview.domain.ProductReview;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReviewMapper {
    int register(ProductReview productReview);
}
