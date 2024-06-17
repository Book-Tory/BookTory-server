package com.booktory.booktoryserver.ProductCart.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCartList {

    private Long cart_id;

    private Long product_id;

    private String product_name;

    private String product_price;

    private String product_script;

    private int product_stock_count;

    private String image_url;
}
