package com.booktory.booktoryserver.payment.dto.request;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaymentRequest {

    private String merchant_uid;
    private BigDecimal amount;
    private List<OrderItem> items;
}
