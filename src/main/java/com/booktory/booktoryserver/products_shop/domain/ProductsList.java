package com.booktory.booktoryserver.products_shop.domain;

import com.booktory.booktoryserver.products_shop.constant.ProductStock;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductsList {

    private Long product_id; // 상품 아이디
    private String product_name; // 상품 이름
    private String product_price; // 상품 가격
    private String product_script; // 상품 설명
    private String product_stock; // 상품 재고 상태
    private String product_category; // 상품 카테고리 FK ( T-SHIRT, ACCESSORY )
    private int product_image_check; // 파일 첨부 0, 파일 첨부 1
    private LocalDateTime created_at; // 상품 등록일
    private LocalDateTime updated_at; // 상품 수정일
    private String originalImageName; // 원본 파일 이름
    private String storedImageName; // 서버 저장용 파일 이름
    private List<String> imageUrls; // 이미지 url

}
