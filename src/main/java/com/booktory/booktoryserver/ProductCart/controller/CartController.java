package com.booktory.booktoryserver.ProductCart.controller;

import com.booktory.booktoryserver.ProductCart.service.ProductCartService;
import com.booktory.booktoryserver.Users.service.CustomUserDetails;
import com.booktory.booktoryserver.common.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product/cart")
@RequiredArgsConstructor
public class CartController {

    private final ProductCartService productCartService;

    @PostMapping("/{product_id}")
    public CustomResponse cart(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable ("product_id") Long product_id){

        String username = userDetails.getUsername();

        int result  = productCartService.cartSave(username, product_id);

        if(result > 0){
            return CustomResponse.ok("장바구니 추가되었습니다.", null);
        }
        else {
            return CustomResponse.failure("장바구니 추가에 실패하였습니다.");
        }
    }
}
