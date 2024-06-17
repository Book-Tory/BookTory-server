package com.booktory.booktoryserver.Products_shop.controller;

import com.booktory.booktoryserver.Products_shop.dto.request.ProductFilterDTO;
import com.booktory.booktoryserver.Products_shop.dto.request.ProductUpdateDTO;
import com.booktory.booktoryserver.common.CustomResponse;
import com.booktory.booktoryserver.Products_shop.dto.request.ProductRegisterDTO;
import com.booktory.booktoryserver.Products_shop.dto.response.ProductResponseDTO;
import com.booktory.booktoryserver.Products_shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/product_shop")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @PostMapping("/register")
    public CustomResponse<String> insert(@ModelAttribute ProductRegisterDTO productDTO) throws IOException {

        String result  = productService.register(productDTO);

        return CustomResponse.ok(result, null);
    }


    @GetMapping("/list")
    public CustomResponse productsAll(
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) String productCategory,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String sort,
            Pageable pageable) {

        ProductFilterDTO productFilterDTO = new ProductFilterDTO();
        productFilterDTO.setProductName(productName);
        productFilterDTO.setProductCategory(productCategory);

        Page<ProductResponseDTO> productResponseDTOList = productService.findAllProducts(productFilterDTO, pageable);
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

    @DeleteMapping("/image/{image_id}")
    public CustomResponse imageDelete(@PathVariable("image_id") Long image_id){
        int result = productService.deleteByImage(image_id);

        if (result > 0) {
            return CustomResponse.ok("삭제되었습니다.", result);
        } else {
            return CustomResponse.failure("삭제 실패하였습니다.");
        }
    }

    @PutMapping("/update/{product_id}")
    public CustomResponse updatePostById(@PathVariable("product_id") Long product_id, @ModelAttribute ProductUpdateDTO productDTO) throws IOException {

        System.out.println("product_id : " + product_id);
        System.out.println("ProductUpdateDTO : " + productDTO);

        String result = productService.updateById(product_id, productDTO);
        return CustomResponse.ok(result, null);
    }
}