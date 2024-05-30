package com.booktory.booktoryserver.Image_S3.service;

import com.booktory.booktoryserver.BookToryServerApplication;
import com.booktory.booktoryserver.Image_S3.domain.ImageEntity;
import com.booktory.booktoryserver.Image_S3.domain.ImageFileEntity;
import com.booktory.booktoryserver.Image_S3.dto.request.ImageDTO;
import com.booktory.booktoryserver.Image_S3.mapper.ImageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageMapper imageMapper;

    public void imageSave(ImageDTO imageDTO) throws IOException {


        // 파일 없음
        if(imageDTO.getImageFile().isEmpty()){
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
                System.out.println("Original filename: " + originalFilename);

                // 저장용 이름 만들기
                System.out.println(System.currentTimeMillis());
                String storeFilename = System.currentTimeMillis() + originalFilename;

                // ImageFileEntity 세팅
                ImageFileEntity boarFilEntity = ImageFileEntity.builder()
                        .imageId(imageId)
                        .originalImageName(originalFilename)
                        .storedImageName(storeFilename)
                        .build();

                // 파일 저장용 폴더에 파일 저장 처리
                String savePath = "/Users/baegseungchan/Desktop/aaaa/" + storeFilename;

                imageFile.transferTo(new File(savePath));

                imageMapper.saveFile(boarFilEntity);

            }
        }
    }
}
