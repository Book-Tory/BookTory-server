package com.booktory.booktoryserver.Image_S3.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageDTO {

    private Long id;

    private List<MultipartFile> imageFile; // 클라이언트에서 넘어올 데이터

    private int fileCheck; // 파일 첨부 여부(첨부1, 미첨부 0)

}
