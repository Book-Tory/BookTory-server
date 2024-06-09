package com.booktory.booktoryserver.ProductCart.dto.response;


import com.booktory.booktoryserver.ProductCart.model.ProductCart;
import com.booktory.booktoryserver.ProductCart.model.ProductCartList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCartResponseDTO {
    private Long cart_id;
    private Long product_id;
    private String product_name;
    private String product_price;
    private String product_script;

    public static ProductCartResponseDTO toProductCartResponseD(ProductCartList cart) {
        return ProductCartResponseDTO.builder()
                .cart_id(cart.getCart_id())
                .product_id(cart.getProduct_id())
                .product_name(cart.getProduct_name())
                .product_price(cart.getProduct_price())
                .product_script(cart.getProduct_script())
                .build();
    }
}
