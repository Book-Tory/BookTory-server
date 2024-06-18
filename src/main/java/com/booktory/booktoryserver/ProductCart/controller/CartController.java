package com.booktory.booktoryserver.ProductCart.controller;

import com.booktory.booktoryserver.ProductCart.dto.request.CartRequestDTO;
import com.booktory.booktoryserver.ProductCart.dto.response.ProductCartResponseDTO;
import com.booktory.booktoryserver.ProductCart.service.ProductCartService;
import com.booktory.booktoryserver.Users.mapper.UserMapper;
import com.booktory.booktoryserver.Users.model.UserEntity;
import com.booktory.booktoryserver.Users.service.CustomUserDetails;
import com.booktory.booktoryserver.common.CustomResponse;
import com.sun.security.auth.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product/cart")
@RequiredArgsConstructor
public class CartController {

    private final ProductCartService productCartService;

    private final UserMapper userMapper;

    @PostMapping("/{product_id}")
    public CustomResponse cart(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable ("product_id") Long product_id, @RequestBody CartRequestDTO cartRequestDTO){

        String username = userDetails.getUsername();

        int result  = productCartService.cartSave(username, product_id, cartRequestDTO);

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

        int result = productCartService.deleteCart(cart_id);

        if(result > 0){
            return CustomResponse.ok("장바구니 삭제 성공", null);
        } else {
            return CustomResponse.failure("장바구니 삭제 실패하였습니다.");
        }
    }

    @GetMapping("/count")
    public CustomResponse cartCount(@AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null) {
            return CustomResponse.error("인증되지 않은 사용자입니다.");
        }

        String email = userDetails.getUsername();

        Optional<UserEntity> user = userMapper.findByEmail(email);

        if (!user.isPresent()) {
            return CustomResponse.error("사용자를 찾을 수 없습니다.");
        }

        Long userId = user.get().getUser_id();

        int cartCount = productCartService.findCartCount(userId);

        return CustomResponse.ok("장바구니 조회 성공", cartCount);
    }

}
