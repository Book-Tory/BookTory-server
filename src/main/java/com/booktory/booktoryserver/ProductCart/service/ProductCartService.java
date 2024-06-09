package com.booktory.booktoryserver.ProductCart.service;

import com.booktory.booktoryserver.ProductCart.mapper.ProductCartMapper;
import com.booktory.booktoryserver.ProductCart.model.ProductCart;
import com.booktory.booktoryserver.Products_shop.mapper.ProductMapper;
import com.booktory.booktoryserver.Users.mapper.UserMapper;
import com.booktory.booktoryserver.Users.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductCartService {

    private final ProductCartMapper productCartMapper;

    private final ProductMapper productMapper;

    private final UserMapper userMapper;

    public int cartSave(String username, Long productId) {
        UserEntity user = userMapper.findByEmail(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // productId가 유효한지 확인
        if (productMapper.findById(productId) == null) {
            throw new IllegalArgumentException("Invalid productId");
        }

        ProductCart productCart = ProductCart.builder()
                .productId(productId)
                .userId(user.getUser_id())
                .build();

        return productCartMapper.cartSave(productCart); // 수정된 부분
    }
}
