package com.booktory.booktoryserver.products_shop.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.booktory.booktoryserver.products_shop.domain.Product;
import com.booktory.booktoryserver.products_shop.domain.ProductImageFile;
import com.booktory.booktoryserver.products_shop.dto.request.ProductRegisterDTO;
import com.booktory.booktoryserver.products_shop.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductMapper productMapper;

    private final AmazonS3 amazonS3Client;

    @Value("${spring.s3.bucket}")
    private String bucketName;

    public List<String> register(ProductRegisterDTO productDTO) throws IOException {

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
                        .imageId(productId)
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

        return null;
    }
}
