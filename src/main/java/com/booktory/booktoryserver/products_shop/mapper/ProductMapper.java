package com.booktory.booktoryserver.products_shop.mapper;

import com.booktory.booktoryserver.products_shop.domain.Product;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductMapper {
    int register(Product product);
}
