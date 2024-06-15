package com.booktory.booktoryserver.Image_S3.domain;


import com.booktory.booktoryserver.Image_S3.dto.request.ImageFileDTO;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageFileEntity {

    private Long id;

    private Long imageId; // 외래키

    private String originalImageName; // 원본 파일 이름

    private String storedImageName; // 서버 저장용 파일 이름

    public static ImageFileEntity toSaveImageFile(ImageFileDTO imageFileDTO){
        return ImageFileEntity.builder()
               .originalImageName(imageFileDTO.getOriginalImageName())
               .storedImageName(imageFileDTO.getStoredImageName())
               .build();
    }
}
