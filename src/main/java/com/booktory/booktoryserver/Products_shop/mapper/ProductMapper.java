package com.booktory.booktoryserver.Products_shop.mapper;

import com.booktory.booktoryserver.Products_shop.domain.Product;
import com.booktory.booktoryserver.Products_shop.domain.ProductImageFile;
import com.booktory.booktoryserver.Products_shop.domain.ProductsList;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {
    int register(Product product);

    int saveFile(ProductImageFile productImageFile);

    List<ProductsList> findAllProducts();

    List<ProductsList> findById(Long productId);

    int deleteById(Long productId);

    int updateByProduct(Product product);

    List<ProductImageFile> findImagesByProductId(Long productId);

    ProductImageFile imageSearch(Long imageId);

    int deleteByImage(Long image_id);
}
