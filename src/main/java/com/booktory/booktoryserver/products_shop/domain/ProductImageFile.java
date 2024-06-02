package com.booktory.booktoryserver.products_shop.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductImageFile {

    private Long id;

    private Long product_id; // 외래키

    private String originalImageName; // 원본 파일 이름

    private String storedImageName; // 서버 저장용 파일 이름

}
