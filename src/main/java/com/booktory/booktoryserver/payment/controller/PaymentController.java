package com.booktory.booktoryserver.payment.controller;

import com.booktory.booktoryserver.Products_shop.domain.Product;
import com.booktory.booktoryserver.Products_shop.mapper.ProductMapper;
import com.booktory.booktoryserver.common.CustomResponse;
import com.booktory.booktoryserver.payment.dto.request.OrderItem;
import com.booktory.booktoryserver.payment.dto.request.PaymentDTO;
import com.booktory.booktoryserver.payment.dto.request.PaymentRequest;
import com.booktory.booktoryserver.payment.service.PaymentService;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.Payment;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
@Tag(name = "Payment API", description = "결제 관련 API")
public class PaymentController {

    private final ProductMapper productMapper;
    private final PaymentService paymentService;

    @Operation(summary = "결제 준비", description = "결제를 준비합니다.")
    @PostMapping("/prepare")
    public CustomResponse preparePayment(@RequestBody @Parameter(description = "결제 요청 정보") PaymentRequest paymentRequest) throws IamportResponseException, IOException {
        Long calculatedAmount = paymentRequest.getItems().stream().mapToLong(item -> item.getProduct_price() * item.getQuantity()).sum() + 3000;
        Long paymentAmount = Long.parseLong(String.valueOf(paymentRequest.getAmount()));

        if (calculatedAmount.equals(paymentAmount)) {
            for (OrderItem orderItem : paymentRequest.getItems()) {
                Product product = productMapper.findByProductId(orderItem.getProduct_id());
                if (product == null) {
                    return CustomResponse.failure("결제 준비 실패: 상품 없음");
                }

                Long productPrice = Long.parseLong(product.getProduct_price());
                if (!productPrice.equals(orderItem.getProduct_price())) {
                    System.out.println("Product price mismatch: " + product.getProduct_price() + " vs " + orderItem.getProduct_price());
                    return CustomResponse.failure("결제 준비 실패: 상품 가격 불일치");
                }
            }
            paymentService.preparePayment(paymentRequest);
            return CustomResponse.ok("결제 준비 완료", null);
        } else {
            return CustomResponse.failure("결제 준비 실패: 금액 불일치");
        }
    }

    @Operation(summary = "결제 검증", description = "결제를 검증합니다.")
    @PostMapping("/validate")
    public Payment validatePayment(@RequestBody @Parameter(description = "결제 정보") PaymentDTO request) throws IamportResponseException, IOException {
        return paymentService.validatePayment(request);
    }
}
