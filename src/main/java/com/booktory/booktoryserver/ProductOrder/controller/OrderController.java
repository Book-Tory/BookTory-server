package com.booktory.booktoryserver.ProductOrder.controller;

import com.booktory.booktoryserver.ProductOrder.dto.request.OrderInfoRequestDto;
import com.booktory.booktoryserver.ProductOrder.service.OrderService;
import com.booktory.booktoryserver.common.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;


    @PostMapping("/orderInfo")
    public CustomResponse saveOrderInfo(@RequestBody OrderInfoRequestDto orderInfo){
        int result = orderService.saveOrderInfo(orderInfo);

        if(result > 0){
            return CustomResponse.ok("주문 완료", orderInfo.getMerchant_uid());
        } else {
            return CustomResponse.failure("주문 실패");
        }
    }
}
