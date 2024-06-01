package com.booktory.booktoryserver.products_shop.dto.response;

        import com.booktory.booktoryserver.products_shop.constant.ProductStock;
        import com.booktory.booktoryserver.products_shop.domain.Product;
        import com.booktory.booktoryserver.products_shop.domain.ProductsList;
        import lombok.*;

        import java.time.LocalDateTime;
        import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponseDTO {

    private Long product_id; // 상품 아이디
    private String product_name; // 상품 이름
    private String product_price; // 상품 가격
    private String product_script; // 상품 설명
    private ProductStock product_stock; // 상품 재고 상태
    private String product_category; // 상품 카테고리 FK ( T-SHIRT, ACCESSORY )
    private int product_image_check; // 파일 첨부 0, 파일 첨부 1
    private LocalDateTime created_at; // 상품 등록일
    private LocalDateTime updated_at; // 상품 수정일
    private List<String> product_image_url;


    public static ProductResponseDTO toProductInfo(ProductsList product, List<String> urls){
        return ProductResponseDTO.builder()
                .product_id(product.getProduct_id())
                .product_name(product.getProduct_name())
                .product_price(product.getProduct_price())
                .product_script(product.getProduct_script())
                .product_stock(ProductStock.valueOf(product.getProduct_stock()))
                .product_category(product.getProduct_category())
                .product_image_check(product.getProduct_image_check())
                .created_at(product.getCreated_at())
                .updated_at(product.getUpdated_at())
                .product_image_url(urls)
                .build();
    }
}
