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
public class ProductRegisterDTO {

    private Long product_id; // 상품 아이디

    private String product_name; // 상품 이름

    private String product_price; // 상품 가격

    private String product_script; // 상품 설명

    private MultipartFile product_image; // 상품 이미지

    private int productImageCheck; // 파일 첨부 0, 파일 첨부 1

}
