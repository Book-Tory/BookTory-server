package com.booktory.booktoryserver.Products_shop.domain;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductImageFile {

    private Long image_id;

    private Long product_id; // 외래키

    private String originalImageName; // 원본 파일 이름

    private String storedImageName; // 서버 저장용 파일 이름

}
