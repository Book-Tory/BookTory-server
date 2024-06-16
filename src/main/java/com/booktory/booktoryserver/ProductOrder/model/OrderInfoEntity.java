package com.booktory.booktoryserver.ProductOrder.model;

import com.booktory.booktoryserver.ProductOrder.dto.request.OrderInfoRequestDto;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class OrderInfoEntity {

    private Long order_id;
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

    public static OrderInfoEntity toOrderInfoEntity(OrderInfoRequestDto requestDto){
        return OrderInfoEntity.builder()
               .merchant_uid(requestDto.getMerchant_uid())
               .user_name(requestDto.getUser_name())
               .product_name(requestDto.getProduct_name())
               .product_price(requestDto.getProduct_price())
               .quantity(requestDto.getQuantity())
               .recipient(requestDto.getRecipient())
               .post(requestDto.getPost())
               .addr(requestDto.getAddr())
               .phone(requestDto.getPhone())
               .message(requestDto.getMessage())
               .build();
    }
}
