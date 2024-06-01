package com.booktory.booktoryserver.products_shop.controller;

import com.booktory.booktoryserver.common.CustomResponse;
import com.booktory.booktoryserver.products_shop.dto.request.ProductRegisterDTO;
import com.booktory.booktoryserver.products_shop.dto.response.ProductResponseDTO;
import com.booktory.booktoryserver.products_shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/product_shop")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @PostMapping("/register")
    public CustomResponse<String> insert(@ModelAttribute ProductRegisterDTO productDTO) throws IOException {
        log.info("productDTO : {}", productDTO);

        String result  = productService.register(productDTO);

        return CustomResponse.ok(result, null);
    }


    @GetMapping("/products")
    public CustomResponse<List<ProductResponseDTO>> productsAll(){

        List<ProductResponseDTO> productResponseDTOList = productService.findAllProducts();

        return CustomResponse.ok("전체 상품 정보", productResponseDTOList);
    }
}
