package com.booktory.booktoryserver.ProductOrder.service;

import com.booktory.booktoryserver.ProductCart.mapper.ProductCartMapper;
import com.booktory.booktoryserver.ProductOrder.dto.request.OrderInfoRequestDto;
import com.booktory.booktoryserver.ProductOrder.dto.response.OrderResponseDTO;
import com.booktory.booktoryserver.ProductOrder.mapper.OrderMapper;
import com.booktory.booktoryserver.ProductOrder.model.OrderInfoEntity;
import com.booktory.booktoryserver.Products_shop.domain.Product;
import com.booktory.booktoryserver.Products_shop.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderMapper orderMapper;

    private final ProductCartMapper productCartMapper;

    private final ProductMapper productMapper;

    @Transactional
    public int saveOrderInfo(OrderInfoRequestDto orderInfo) {

        OrderInfoEntity orderInfoEntity = OrderInfoEntity.toOrderInfoEntity(orderInfo);
        try {
            String[] orderProduct = orderInfoEntity.getProduct_name().split(",");
            orderMapper.saveOrderInfo(orderInfoEntity);

            for(String order : orderProduct){
                Product product = productMapper.findByProductName(order.trim());
                productCartMapper.deleteByOrderCart(product.getProduct_id());
            }

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List<OrderResponseDTO> findOrderInfo() {

        List<OrderInfoEntity> orderInfo = orderMapper.findOrderInfo();

        return orderInfo.stream().map(OrderResponseDTO::toOrderResponseDTO).collect(Collectors.toList());
    }
}
