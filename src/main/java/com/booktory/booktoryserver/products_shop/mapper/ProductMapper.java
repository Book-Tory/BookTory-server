package com.booktory.booktoryserver.products_shop.mapper;

import com.booktory.booktoryserver.products_shop.domain.Product;
import com.booktory.booktoryserver.products_shop.domain.ProductImageFile;
import com.booktory.booktoryserver.products_shop.domain.ProductsList;
import com.booktory.booktoryserver.products_shop.dto.response.ProductResponseDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {
    int register(Product product);

    int saveFile(ProductImageFile productImageFile);

    List<ProductsList> findAllProducts();
}
