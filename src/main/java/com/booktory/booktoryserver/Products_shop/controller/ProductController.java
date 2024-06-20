package com.booktory.booktoryserver.Products_shop.controller;

import com.booktory.booktoryserver.Products_shop.dto.request.ProductFilterDTO;
import com.booktory.booktoryserver.Products_shop.dto.request.ProductUpdateDTO;
import com.booktory.booktoryserver.common.CustomResponse;
import com.booktory.booktoryserver.Products_shop.dto.request.ProductRegisterDTO;
import com.booktory.booktoryserver.Products_shop.dto.response.ProductResponseDTO;
import com.booktory.booktoryserver.Products_shop.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/product_shop")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Product Shop", description = "상품 상점 관련 API")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/register")
    @Operation(summary = "상품 등록", description = "주어진 상세 정보로 새 상품을 등록합니다.")
    @ApiResponse(responseCode = "200", description = "상품 등록 성공", content = @Content(schema = @Schema(implementation = String.class)))
    public CustomResponse<String> insert(@ModelAttribute ProductRegisterDTO productDTO) throws IOException {
        String result = productService.register(productDTO);
        return CustomResponse.ok(result, null);
    }

    @GetMapping("/list")
    @Operation(summary = "모든 상품 조회", description = "모든 상품의 페이징된 목록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "상품 목록 조회 성공", content = @Content(schema = @Schema(implementation = Page.class)))
    public CustomResponse productsAll(
            @RequestParam(required = false) @Parameter(description = "상품 이름으로 필터링") String productName,
            @RequestParam(required = false) @Parameter(description = "상품 카테고리로 필터링") String productCategory,
            @RequestParam @Parameter(description = "페이지 번호") int page,
            @RequestParam @Parameter(description = "페이지 크기") int size,
            @RequestParam @Parameter(description = "정렬 순서") String sort,
            Pageable pageable) {

        ProductFilterDTO productFilterDTO = new ProductFilterDTO();
        productFilterDTO.setProductName(productName);
        productFilterDTO.setProductCategory(productCategory);

        Page<ProductResponseDTO> productResponseDTOList = productService.findAllProducts(productFilterDTO, pageable);
        return CustomResponse.ok("전체 상품 정보", productResponseDTOList);
    }

    @GetMapping("/detail/{product_id}")
    @Operation(summary = "상품 상세 정보 조회", description = "상품 ID로 상품의 상세 정보를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "상품 상세 정보 조회 성공", content = @Content(schema = @Schema(implementation = ProductResponseDTO.class)))
    public CustomResponse<ProductResponseDTO> productDetail(@PathVariable("product_id") @Parameter(description = "조회할 상품의 ID") Long product_id) {
        ProductResponseDTO productResponseDTO = productService.findById(product_id);
        return CustomResponse.ok("상품 상세 정보", productResponseDTO);
    }

    @DeleteMapping("/{product_id}")
    @Operation(summary = "상품 삭제", description = "상품 ID로 상품을 삭제합니다.")
    @ApiResponse(responseCode = "200", description = "상품 삭제 성공", content = @Content(schema = @Schema(implementation = String.class)))
    public CustomResponse deletePostById(@PathVariable("product_id") @Parameter(description = "삭제할 상품의 ID") Long product_id) {
        int result = productService.deleteById(product_id);

        if (result > 0) {
            return CustomResponse.ok("삭제되었습니다.", result);
        } else {
            return CustomResponse.failure("삭제 실패하였습니다.");
        }
    }

    @DeleteMapping("/image/{image_id}")
    @Operation(summary = "상품 이미지 삭제", description = "이미지 ID로 이미지를 삭제합니다.")
    @ApiResponse(responseCode = "200", description = "이미지 삭제 성공", content = @Content(schema = @Schema(implementation = String.class)))
    public CustomResponse imageDelete(@PathVariable("image_id") @Parameter(description = "삭제할 이미지의 ID") Long image_id) {
        int result = productService.deleteByImage(image_id);

        if (result > 0) {
            return CustomResponse.ok("삭제되었습니다.", result);
        } else {
            return CustomResponse.failure("삭제 실패하였습니다.");
        }
    }

    @PutMapping("/update/{product_id}")
    @Operation(summary = "상품 수정", description = "상품 ID로 상품의 상세 정보를 수정합니다.")
    @ApiResponse(responseCode = "200", description = "상품 수정 성공", content = @Content(schema = @Schema(implementation = String.class)))
    public CustomResponse updatePostById(@PathVariable("product_id") @Parameter(description = "수정할 상품의 ID") Long product_id, @ModelAttribute ProductUpdateDTO productDTO) throws IOException {
        System.out.println("product_id : " + product_id);
        System.out.println("ProductUpdateDTO : " + productDTO);

        String result = productService.updateById(product_id, productDTO);
        return CustomResponse.ok(result, null);
    }
}
