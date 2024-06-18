package com.booktory.booktoryserver.ProductOrder.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderInfoRequestDto {
    private String merchant_uid;
    private String user_name;
    private String product_name;
    private Long product_price;
    private Integer quantity;
    private String recipient;
    private String post;
    private String addr;
    private String phone;
    private String message;
}
