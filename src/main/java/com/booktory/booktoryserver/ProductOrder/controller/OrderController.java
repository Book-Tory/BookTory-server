package com.booktory.booktoryserver.ProductOrder.controller;

import com.booktory.booktoryserver.ProductOrder.dto.request.OrderInfoRequestDto;
import com.booktory.booktoryserver.ProductOrder.dto.response.OrderResponseDTO;
import com.booktory.booktoryserver.ProductOrder.model.OrderInfoEntity;
import com.booktory.booktoryserver.ProductOrder.service.OrderService;
import com.booktory.booktoryserver.common.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("")
    public CustomResponse getOrderInfo(){
        List<OrderResponseDTO> ordersList = orderService.findOrderInfo();
        return CustomResponse.ok("주문 조회 성공", ordersList);
    }

    @GetMapping("/today")
    public CustomResponse<List<OrderInfoEntity>> getTodaySales() {
        List<OrderInfoEntity> sales = orderService.getTodayOrders();
        return CustomResponse.ok("Today's Sales", sales);
    }

    @GetMapping("/week")
    public CustomResponse<List<OrderInfoEntity>> getThisWeekSales() {
        List<OrderInfoEntity> sales = orderService.getThisWeekOrders();
        return CustomResponse.ok("This Week's Sales", sales);
    }

    @GetMapping("/month")
    public CustomResponse<List<OrderInfoEntity>> getThisMonthSales() {
        List<OrderInfoEntity> sales = orderService.getThisMonthOrders();
        return CustomResponse.ok("This Month's Sales", sales);
    }

    @GetMapping("/year")
    public CustomResponse<List<OrderInfoEntity>> getThisYearSales() {
        List<OrderInfoEntity> sales = orderService.getThisYearOrders();
        return CustomResponse.ok("This Year's Sales", sales);
    }

}
