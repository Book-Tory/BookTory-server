package com.booktory.booktoryserver.products_shop.dto.request;

import com.booktory.booktoryserver.products_shop.constant.ProductStock;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ProductUpdateDTO {

    private Long product_id; // 상품 아이디

    private String product_name; // 상품 이름

    private String product_price; // 상품 가격

    private String product_script; // 상품 설명

    private int productImageCheck; // 파일 첨부 0, 파일 첨부 1

    private ProductStock product_stock; // 상품 재고

    private String product_category; // 상품 카테고리 FK ( T-SHIRT, ACCESSORY )

    private List<String> product_image_url; // 상품 이미지 url

    private List<MultipartFile> product_image; // 상품 이미지

}
