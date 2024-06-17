package com.booktory.booktoryserver.ProductCart.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CartRequestDTO {

    private int productStockCount;

    private String imageUrl;
}
