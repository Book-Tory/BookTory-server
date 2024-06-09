package com.booktory.booktoryserver.ProductReview.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.booktory.booktoryserver.ProductReview.domain.ProductReview;
import com.booktory.booktoryserver.ProductReview.domain.UserData;
import com.booktory.booktoryserver.ProductReview.dto.request.ReviewRequestDTO;
import com.booktory.booktoryserver.ProductReview.dto.response.ProductReviewResponseDTO;
import com.booktory.booktoryserver.ProductReview.mapper.ReviewMapper;
import com.booktory.booktoryserver.ProductReview.mapper.UserMappers;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewMapper reviewMapper;

    // 임시 유저 조회 ( bsc7386@naver.com )
    private final UserMappers userMapper;

    private final AmazonS3 amazonS3Client;

    @Value("${spring.s3.bucket}")
    private String bucketName;


    @Transactional
    public int register(ReviewRequestDTO reviewRequestDTO, String user_email) throws IOException {

        UserData userData = userMapper.findUserByEmail(user_email);

        System.out.println("userData : " + userData); // UserData(user_id=8, user_email=bsc7386@naver.com, user_nickname=cnah)

        if(reviewRequestDTO.getReview_image() == null || reviewRequestDTO.getReview_image().isEmpty()) {
            reviewRequestDTO.setReview_file_check("0");
            reviewRequestDTO.setReview_stored_image("0");
            reviewRequestDTO.setReview_original_image("0");

            ProductReview productReview = ProductReview.toReviewEntity(reviewRequestDTO, userData.getUser_nickname());

            return reviewMapper.register(productReview);

        } else {
            String originalFileName = reviewRequestDTO.getReview_image().getOriginalFilename();
            String storeFileName = UUID.randomUUID() + originalFileName;

            reviewRequestDTO.setReview_file_check("1");
            reviewRequestDTO.setReview_original_image(originalFileName);
            reviewRequestDTO.setReview_stored_image(storeFileName);

            ProductReview productReview = ProductReview.toReviewEntity(reviewRequestDTO, userData.getUser_nickname());

            // 파일 메타데이터 설정
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(reviewRequestDTO.getReview_image().getSize());
            objectMetadata.setContentType(reviewRequestDTO.getReview_image().getContentType());

            // 저장될 위치 + 파일명
            String key = "profile" + "/" + storeFileName;

            // 클라우드에 파일 저장
            amazonS3Client.putObject(bucketName, key, reviewRequestDTO.getReview_image().getInputStream(), objectMetadata);
            amazonS3Client.setObjectAcl(bucketName, key, CannedAccessControlList.PublicRead);

            return reviewMapper.register(productReview);
        }
    }

    public List<ProductReviewResponseDTO> getReviewByProductId(Long productId) {
        List<ProductReview> productReviewList = reviewMapper.getReviewByProductId(productId);

        return productReviewList.stream()
                .map(productReview -> {
                    String url = amazonS3Client.getUrl(bucketName, "profile/" + productReview.getReview_stored_image()).toString();
                    return ProductReviewResponseDTO.toProductReviewInfo(productReview, url);
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public int deleteByReviewId(Long reviewId) {

        // 리뷰를 조회합니다.
        ProductReview productReviewOptional = reviewMapper.findByReviewId(reviewId);


        if (productReviewOptional != null) {
            // S3에서 이미지를 삭제합니다.
            if (!"0".equals(productReviewOptional.getReview_stored_image())) {
                String key = "profile/" + productReviewOptional.getReview_stored_image();
                amazonS3Client.deleteObject(bucketName, key);
            }
            // 리뷰를 삭제합니다.
            return reviewMapper.deleteByReviewId(reviewId);
        } else {
            throw new IllegalArgumentException("리뷰를 찾을 수 없습니다.");
        }
    }
}
