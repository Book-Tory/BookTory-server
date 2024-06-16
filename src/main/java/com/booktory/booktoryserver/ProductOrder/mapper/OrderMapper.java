package com.booktory.booktoryserver.ProductOrder.mapper;

import com.booktory.booktoryserver.ProductOrder.model.OrderInfoEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {

    @Insert("INSERT INTO product_orders (merchant_uid, user_name, product_name, product_price, quantity, recipient, post, addr, phone, message) " +
            "VALUES (#{merchant_uid}, #{user_name}, #{product_name}, #{product_price}, #{quantity}, #{recipient}, #{post}, #{addr}, #{phone}, #{message})")
    int saveOrderInfo(OrderInfoEntity orderInfo);
}
