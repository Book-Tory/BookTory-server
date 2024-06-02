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


    @GetMapping("/detail/{product_id}")
    public CustomResponse<ProductResponseDTO> productDetail(@PathVariable("product_id") Long product_id){
        ProductResponseDTO productResponseDTO = productService.findById(product_id);

        return CustomResponse.ok("상품 상세 정보", productResponseDTO);
    }


    @DeleteMapping("/{product_id}")
    public CustomResponse deletePostById(@PathVariable("product_id") Long product_id){
        int result = productService.deleteById(product_id);

        if (result > 0) {
            return CustomResponse.ok("삭제되었습니다.", result);
        } else {
            return CustomResponse.failure("삭제 실패하였습니다.");
        }
    }


    @PutMapping("/update/{product_id}")
    public CustomResponse updatePostById(@PathVariable("product_id") Long product_id, @ModelAttribute ProductRegisterDTO productDTO){
        int result = productService.updateById(product_id, productDTO);

        return null;
    }
}
