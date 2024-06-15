package com.booktory.booktoryserver.Image_S3.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ImageFileDTO {

    private Long id;

    private String originalImageName; // 원본 파일 이름

    private String storedImageName; // 서버 저장용 파일 이름, 중복될 수 있어서
}
