package com.booktory.booktoryserver.Products_shop.domain;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ProductCategory {

    private Long product_category_id; // 카테고리 아이디

    private String product_category; // 상품 종류

}
