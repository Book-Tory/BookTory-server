package com.booktory.booktoryserver.ProductCart.controller;

import com.booktory.booktoryserver.ProductCart.dto.response.ProductCartResponseDTO;
import com.booktory.booktoryserver.ProductCart.service.ProductCartService;
import com.booktory.booktoryserver.Users.service.CustomUserDetails;
import com.booktory.booktoryserver.common.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


    @GetMapping("/list")
    public CustomResponse cartList(@AuthenticationPrincipal CustomUserDetails userDetails){
        String username = userDetails.getUsername();

        List<ProductCartResponseDTO> list = productCartService.cartList(username);
        return CustomResponse.ok("장바구니 리스트 조회 성공", list);
    }


    @DeleteMapping("/{cart_id}")
    public CustomResponse deleteCart(@PathVariable("cart_id") Long cart_id){
        System.out.println("deleteCart" + cart_id);
        int result = productCartService.deleteCart(cart_id);

        if(result > 0){
            return CustomResponse.ok("장바구니 삭제 성공", null);
        } else {
            return CustomResponse.failure("장바구니 삭제 실패하였습니다.");
        }
    }

}
