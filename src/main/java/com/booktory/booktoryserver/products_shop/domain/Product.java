package com.booktory.booktoryserver.products_shop.domain;

import com.booktory.booktoryserver.products_shop.constant.ProductStock;
import com.booktory.booktoryserver.products_shop.dto.request.ProductRegisterDTO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Product {

    private Long product_id; // 상품 아이디
    private String product_name; // 상품 이름
    private String product_price; // 상품 가격
    private String product_script; // 상품 설명
    private ProductStock product_stock; // 상품 재고 상태
    private String product_category; // 상품 카테고리 FK ( T-SHIRT, ACCESSORY )
    private int product_image_check; // 파일 첨부 0, 파일 첨부 1
    private LocalDateTime created_at; // 상품 등록일
    private LocalDateTime updated_at; // 상품 수정일

    public static Product toSaveProduct(ProductRegisterDTO productDTO){
        return Product.builder()
                .product_name(productDTO.getProduct_name())
                .product_price(productDTO.getProduct_price())
                .product_script(productDTO.getProduct_script())
                .product_stock(ProductStock.IN_STOCK) // 기본값 설정
                .product_category("T-SHIRT") // 기본값 설정
                .product_image_check(productDTO.getProductImageCheck())
                .build();
    }


    public static Product toUpdateProduct(ProductRegisterDTO productDTO, Long productId){
        return Product.builder()
                .product_id(productId)
                .product_name(productDTO.getProduct_name())
                .product_price(productDTO.getProduct_price())
                .product_script(productDTO.getProduct_script())
                .product_stock(ProductStock.IN_STOCK) // 기본값 설정
                .product_category("T-SHIRT") // 기본값 설정
                .product_image_check(productDTO.getProductImageCheck())
                .build();
    }
}
