package com.booktory.booktoryserver.Products_shop.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public enum ProductStock {

    IN_STOCK("IN_STOCK"), // 재고 있음
    OUT_OF_STOCK("OUT_OF_STOCK"),  // 재고 없음
    LOW_STOCK("LOW_STOCK"), // 재고 부족
    BACK_ORDER("BACK_ORDER"),  // 주문 대기
    DISCONTINUED("DISCONTINUED"); // 단종

    private final String key;
}