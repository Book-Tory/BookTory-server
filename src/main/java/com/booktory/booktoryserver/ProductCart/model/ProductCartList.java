package com.booktory.booktoryserver.ProductCart.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCartList {
    private Long product_id;

    private String product_name;

    private String product_price;

    private String product_script;
}
