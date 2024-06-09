package com.booktory.booktoryserver.ProductCart.mapper;

import com.booktory.booktoryserver.ProductCart.model.ProductCart;
import com.booktory.booktoryserver.Users.model.UserEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductCartMapper {
    int cartSave(ProductCart productCart);

}
