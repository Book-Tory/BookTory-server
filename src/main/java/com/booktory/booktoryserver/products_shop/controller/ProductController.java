package com.booktory.booktoryserver.products_shop.controller;

import com.booktory.booktoryserver.products_shop.dto.request.ProductRegisterDTO;
import com.booktory.booktoryserver.products_shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product_shop")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @PostMapping("/register")
    public void insert(@ModelAttribute ProductRegisterDTO productDTO) {
        log.info("productDTO : {}", productDTO);

        productService.register(productDTO);

    }
}
