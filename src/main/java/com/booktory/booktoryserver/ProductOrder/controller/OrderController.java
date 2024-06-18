package com.booktory.booktoryserver.ProductOrder.controller;

import com.booktory.booktoryserver.ProductOrder.dto.request.OrderInfoRequestDto;
import com.booktory.booktoryserver.ProductOrder.dto.response.OrderResponseDTO;
import com.booktory.booktoryserver.ProductOrder.model.OrderInfoEntity;
import com.booktory.booktoryserver.ProductOrder.service.OrderService;
import com.booktory.booktoryserver.common.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Tag(name = "Order API", description = "주문 관련 API")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "주문 정보 저장", description = "주문 정보를 저장합니다.")
    @PostMapping("/orderInfo")
    public CustomResponse saveOrderInfo(@RequestBody @Parameter(description = "주문 정보") OrderInfoRequestDto orderInfo){
        int result = orderService.saveOrderInfo(orderInfo);
        if(result > 0){
            return CustomResponse.ok("주문 완료", orderInfo.getMerchant_uid());
        } else {
            return CustomResponse.failure("주문 실패");
        }
    }

    @Operation(summary = "주문 상세 정보 조회", description = "주문 ID로 주문 상세 정보를 조회합니다.")
    @GetMapping("/{order_id}")
    public CustomResponse getOrderById(@Parameter(description = "주문 ID") @PathVariable("order_id") Long orderId) {
        OrderResponseDTO order = orderService.findById(orderId);
        return CustomResponse.ok("주문 상세 정보", order);
    }

    @Operation(summary = "모든 주문 정보 조회", description = "모든 주문 정보를 조회합니다.")
    @GetMapping("")
    public CustomResponse getOrderInfo(){
        List<OrderResponseDTO> ordersList = orderService.findOrderInfo();
        System.out.println("orderList: " + ordersList);
        return CustomResponse.ok("주문 조회 성공", ordersList);
    }

    @Operation(summary = "오늘의 판매 조회", description = "오늘의 판매 정보를 조회합니다.")
    @GetMapping("/today")
    public CustomResponse<List<OrderInfoEntity>> getTodaySales() {
        List<OrderInfoEntity> sales = orderService.getTodayOrders();
        return CustomResponse.ok("Today's Sales", sales);
    }

    @Operation(summary = "이번 주 판매 조회", description = "이번 주의 판매 정보를 조회합니다.")
    @GetMapping("/week")
    public CustomResponse<List<OrderInfoEntity>> getThisWeekSales() {
        List<OrderInfoEntity> sales = orderService.getThisWeekOrders();
        return CustomResponse.ok("This Week's Sales", sales);
    }

    @Operation(summary = "이번 달 판매 조회", description = "이번 달의 판매 정보를 조회합니다.")
    @GetMapping("/month")
    public CustomResponse<List<OrderInfoEntity>> getThisMonthSales() {
        List<OrderInfoEntity> sales = orderService.getThisMonthOrders();
        return CustomResponse.ok("This Month's Sales", sales);
    }

    @Operation(summary = "이번 해 판매 조회", description = "이번 해의 판매 정보를 조회합니다.")
    @GetMapping("/year")
    public CustomResponse<List<OrderInfoEntity>> getThisYearSales() {
        List<OrderInfoEntity> sales = orderService.getThisYearOrders();
        return CustomResponse.ok("This Year's Sales", sales);
    }

}
