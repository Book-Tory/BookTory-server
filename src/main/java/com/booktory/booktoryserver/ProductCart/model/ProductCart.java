package com.booktory.booktoryserver.ProductCart.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCart {
    private Long cartId;
    private Long productId;
    private Long userId;
    private int productStockCount;
    private String imageUrl;
    private String createdAt;
    private String updatedAt;
}