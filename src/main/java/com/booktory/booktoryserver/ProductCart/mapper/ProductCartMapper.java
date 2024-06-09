package com.booktory.booktoryserver.ProductCart.mapper;

import com.booktory.booktoryserver.ProductCart.dto.response.ProductCartResponseDTO;
import com.booktory.booktoryserver.ProductCart.model.ProductCart;
import com.booktory.booktoryserver.ProductCart.model.ProductCartList;
import com.booktory.booktoryserver.Users.model.UserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductCartMapper {
    int cartSave(ProductCart productCart);

    List<ProductCartList> selectCartProductsByUserId(Long userId);
}
