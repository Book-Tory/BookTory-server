package com.booktory.booktoryserver.products_shop.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.booktory.booktoryserver.products_shop.domain.Product;
import com.booktory.booktoryserver.products_shop.domain.ProductImageFile;
import com.booktory.booktoryserver.products_shop.domain.ProductsList;
import com.booktory.booktoryserver.products_shop.dto.request.ProductRegisterDTO;
import com.booktory.booktoryserver.products_shop.dto.response.ProductResponseDTO;
import com.booktory.booktoryserver.products_shop.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductMapper productMapper;

    private final AmazonS3 amazonS3Client;

    @Value("${spring.s3.bucket}")
    private String bucketName;

    public String register(ProductRegisterDTO productDTO) throws IOException {

        List<String> urls = new ArrayList<>();

        if(productDTO.getProduct_image() == null || productDTO.getProduct_image().isEmpty()){
            productDTO.setProductImageCheck(0);
            Product product = Product.toSaveProduct(productDTO);
            productMapper.register(product);
        } else {
            productDTO.setProductImageCheck(1);
            Product product = Product.toSaveProduct(productDTO);

            productMapper.register(product);

            Long productId = product.getProduct_id();

            for(MultipartFile productFile : productDTO.getProduct_image()){
                String originalFilename = productFile.getOriginalFilename();
                String storeFilename = System.currentTimeMillis() + originalFilename;

                ProductImageFile productImageFile = ProductImageFile.builder()
                        .product_id(productId)
                        .originalImageName(originalFilename)
                        .storedImageName(storeFilename)
                        .build();

                // 파일 메타데이터 설정
                ObjectMetadata objectMetadata = new ObjectMetadata();
                objectMetadata.setContentLength(productFile.getSize());
                objectMetadata.setContentType(productFile.getContentType());

                // 저장될 위치 + 파일명
                String key = "profile" + "/" + storeFilename;

                // 클라우드에 파일 저장
                amazonS3Client.putObject(bucketName, key, productFile.getInputStream(), objectMetadata);
                amazonS3Client.setObjectAcl(bucketName, key, CannedAccessControlList.PublicRead);

                productMapper.saveFile(productImageFile);

                String url = amazonS3Client.getUrl(bucketName, key).toString();
                urls.add(url);
            }
        }

        return "상품 등록되었습니다.";
    }


    public List<ProductResponseDTO> findAllProducts() {
        List<ProductsList> productsLists = productMapper.findAllProducts();

        Map<Long, ProductsList> productsMap = new HashMap<>();
        for (ProductsList product : productsLists) {
            if (!productsMap.containsKey(product.getProduct_id())) {
                product.setImageUrls(new ArrayList<>());
                productsMap.put(product.getProduct_id(), product);
            }
            if (product.getStoredImageName() != null) {
                String url = amazonS3Client.getUrl(bucketName, "profile/" + product.getStoredImageName()).toString();
                productsMap.get(product.getProduct_id()).getImageUrls().add(url);
            }
        }

        return productsMap.values().stream().map(product -> {
            return ProductResponseDTO.toProductInfo(product, product.getImageUrls());
        }).collect(Collectors.toList());
    }

    public ProductResponseDTO findById(Long productId) {

        List<ProductsList> productsList  = productMapper.findById(productId);

        if(productsList == null || productsList.isEmpty()) {
            return null;
        }

        ProductsList firstProduct = productsList.get(0);

        List<String> productImageUrls = productsList.stream().map(product -> amazonS3Client.getUrl(bucketName, "profile/" + product.getStoredImageName()).toString())
                .collect(Collectors.toList());

        return ProductResponseDTO.toProductInfo(firstProduct, productImageUrls);

    }

    @Transactional
    public int deleteById(Long productId) {
        List<ProductImageFile> productImageFiles = productMapper.findImagesByProductId(productId);

        for(ProductImageFile productImageFile : productImageFiles){
            String key = "profile/" + productImageFile.getStoredImageName();
            amazonS3Client.deleteObject(new DeleteObjectRequest(bucketName, key));
        }

        return productMapper.deleteById(productId);
    }

    public int updateById(Long productId, ProductRegisterDTO productDTO) {


        Product product = Product.toUpdateProduct(productDTO, productId);
        return productMapper.updateById(product);
    }

}
