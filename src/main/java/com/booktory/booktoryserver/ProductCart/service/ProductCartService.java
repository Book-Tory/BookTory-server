package com.booktory.booktoryserver.ProductCart.service;

import com.booktory.booktoryserver.ProductCart.dto.response.ProductCartResponseDTO;
import com.booktory.booktoryserver.ProductCart.mapper.ProductCartMapper;
import com.booktory.booktoryserver.ProductCart.model.ProductCart;
import com.booktory.booktoryserver.ProductCart.model.ProductCartList;
import com.booktory.booktoryserver.Products_shop.mapper.ProductMapper;
import com.booktory.booktoryserver.Users.mapper.UserMapper;
import com.booktory.booktoryserver.Users.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<ProductCartResponseDTO> cartList(String username) {
        UserEntity user = userMapper.findByEmail(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));


        List<ProductCartList> productCarts = productCartMapper.selectCartProductsByUserId(user.getUser_id());

        return productCarts.stream().map(ProductCartResponseDTO::toProductCartResponseD).collect(Collectors.toList());
    }
}
