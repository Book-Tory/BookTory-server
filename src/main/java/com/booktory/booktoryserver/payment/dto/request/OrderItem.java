package com.booktory.booktoryserver.payment.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderItem {
    private Long cart_id;
    private Long product_id;
    private String product_name;
    private Long product_price;
    private String product_script;
    private int product_stock;
    private Integer quantity;
}
