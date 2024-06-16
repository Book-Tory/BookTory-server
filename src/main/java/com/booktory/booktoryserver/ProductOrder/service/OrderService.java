package com.booktory.booktoryserver.ProductOrder.service;

import com.booktory.booktoryserver.ProductOrder.dto.request.OrderInfoRequestDto;
import com.booktory.booktoryserver.ProductOrder.mapper.OrderMapper;
import com.booktory.booktoryserver.ProductOrder.model.OrderInfoEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderMapper orderMapper;
    public int saveOrderInfo(OrderInfoRequestDto orderInfo) {

        OrderInfoEntity orderInfoEntity = OrderInfoEntity.toOrderInfoEntity(orderInfo);

        return orderMapper.saveOrderInfo(orderInfoEntity);

    }
}
