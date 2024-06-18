package com.booktory.booktoryserver.ProductOrder.dto.response;


import com.booktory.booktoryserver.ProductOrder.model.OrderInfoEntity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class OrderResponseDTO {

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

    public static OrderResponseDTO toOrderResponseDTO(OrderInfoEntity orderResponse){
        return OrderResponseDTO.builder()
               .order_id(orderResponse.getId())
               .merchant_uid(orderResponse.getMerchant_uid())
               .user_name(orderResponse.getUser_name())
               .product_name(orderResponse.getProduct_name())
               .product_price(orderResponse.getProduct_price())
               .quantity(orderResponse.getQuantity())
               .recipient(orderResponse.getRecipient())
               .post(orderResponse.getPost())
               .addr(orderResponse.getAddr())
               .phone(orderResponse.getPhone())
               .message(orderResponse.getMessage())
               .build();
    }
}
