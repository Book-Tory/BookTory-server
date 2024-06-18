package com.booktory.booktoryserver.ProductOrder.mapper;

import com.booktory.booktoryserver.ProductOrder.model.OrderInfoEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderMapper {

    @Insert("INSERT INTO product_orders (merchant_uid, user_name, product_name, product_price, quantity, recipient, post, addr, phone, message) " +
            "VALUES (#{merchant_uid}, #{user_name}, #{product_name}, #{product_price}, #{quantity}, #{recipient}, #{post}, #{addr}, #{phone}, #{message})")
    int saveOrderInfo(OrderInfoEntity orderInfo);

    @Select("SELECT  * FROM product_orders")
    List<OrderInfoEntity> findOrderInfo();

    @Select("SELECT * FROM product_orders WHERE DATE(order_date) = CURDATE()")
    List<OrderInfoEntity> findTodayOrders();

    @Select("SELECT * FROM product_orders WHERE WEEK(order_date) = WEEK(CURDATE()) AND YEAR(order_date) = YEAR(CURDATE())")
    List<OrderInfoEntity> findThisWeekOrders();

    @Select("SELECT * FROM product_orders WHERE MONTH(order_date) = MONTH(CURDATE()) AND YEAR(order_date) = YEAR(CURDATE())")
    List<OrderInfoEntity> findThisMonthOrders();

    @Select("SELECT * FROM product_orders WHERE YEAR(order_date) = YEAR(CURDATE())")
    List<OrderInfoEntity> findThisYearOrders();
}
