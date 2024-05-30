package com.booktory.booktoryserver.Image_S3.domain;

import com.booktory.booktoryserver.Image_S3.dto.request.ImageDTO;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ImageEntity {

    private Long id;

    private int fileCheck;

    public static ImageEntity toSaveImage(ImageDTO imageDTO){
        return ImageEntity.builder()
                .fileCheck(imageDTO.getFileCheck())
                .build();
    }

}
