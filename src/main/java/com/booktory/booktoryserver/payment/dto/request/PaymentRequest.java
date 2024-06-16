package com.booktory.booktoryserver.payment.dto.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaymentRequest {

    private String merchantUid;
    private Long amount;
    private List<OrderItem> items;
}
