package com.booktory.booktoryserver.Image_S3.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.booktory.booktoryserver.BookToryServerApplication;
import com.booktory.booktoryserver.Image_S3.domain.ImageEntity;
import com.booktory.booktoryserver.Image_S3.domain.ImageFileEntity;
import com.booktory.booktoryserver.Image_S3.dto.request.ImageDTO;
import com.booktory.booktoryserver.Image_S3.mapper.ImageMapper;
import com.booktory.booktoryserver.config.NaverCloudConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageMapper imageMapper;

    private final AmazonS3 amazonS3Client;

    @Value("${spring.s3.bucket}")
    private String bucketName;

    public List<String> imageSave(ImageDTO imageDTO) throws IOException {
        List<String> urls = new ArrayList<>();

        // 파일 없음
        if(imageDTO.getImageFile() == null){
            imageDTO.setFileCheck(0);
            ImageEntity image = ImageEntity.toSaveImage(imageDTO);
            imageMapper.save(image);
        } else {
            // 파일 있음
            imageDTO.setFileCheck(1);
            ImageEntity image = ImageEntity.toSaveImage(imageDTO);

            // 이미지 저장 후 id값 활용을 위해 리턴 받음
            imageMapper.save(image);

            Long imageId = image.getId();

            // 파일만 따로 가져오기
            for(MultipartFile imageFile : imageDTO.getImageFile()){
                // 파일 이름 가져오기
                String originalFilename = imageFile.getOriginalFilename();
                // 저장용 이름 만들기
                String storeFilename = System.currentTimeMillis() + originalFilename;

                // ImageFileEntity 세팅
                ImageFileEntity boarFilEntity = ImageFileEntity.builder()
                        .imageId(imageId)
                        .originalImageName(originalFilename)
                        .storedImageName(storeFilename)
                        .build();

//                // 파일 저장용 폴더에 파일 저장 처리
//                String savePath = "/Users/baegseungchan/Desktop/aaaa/" + storeFilename;
//                imageFile.transferTo(new File(savePath));

                // 파일 메타데이터 설정
                ObjectMetadata objectMetadata = new ObjectMetadata();
                objectMetadata.setContentLength(imageFile.getSize());
                objectMetadata.setContentType(imageFile.getContentType());

                // 저장될 위치 + 파일명
                String key = "profile" + "/" + storeFilename;

                // 클라우드에 파일 저장
                amazonS3Client.putObject(bucketName, key, imageFile.getInputStream(), objectMetadata);
                amazonS3Client.setObjectAcl(bucketName, key, CannedAccessControlList.PublicRead);

                imageMapper.saveFile(boarFilEntity);

                String url = amazonS3Client.getUrl(bucketName, key).toString();
                urls.add(url);
            }
        }

        System.out.println("chan " + urls);

        return urls;
    }
}
