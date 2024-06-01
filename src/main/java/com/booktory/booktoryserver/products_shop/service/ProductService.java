package com.booktory.booktoryserver.products_shop.service;

import com.amazonaws.services.s3.AmazonS3;
import com.booktory.booktoryserver.products_shop.domain.Product;
import com.booktory.booktoryserver.products_shop.dto.request.ProductRegisterDTO;
import com.booktory.booktoryserver.products_shop.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductMapper productMapper;

    private final AmazonS3 amazonS3Client;

    @Value("${spring.s3.bucket}")
    private String bucketName;

    public List<String> register(ProductRegisterDTO productDTO) {

        log.info("productDTOService : {}", productDTO);

        if(productDTO.getProduct_image() == null || productDTO.getProduct_image().isEmpty()){
            productDTO.setProductImageCheck(0);
            Product product = Product.toSaveProduct(productDTO);

            productMapper.register(product);

        } else {
            productDTO.setProductImageCheck(1);
        }

        return null;
    }
}
