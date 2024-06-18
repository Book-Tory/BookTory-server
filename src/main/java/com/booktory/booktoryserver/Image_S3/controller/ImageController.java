package com.booktory.booktoryserver.Image_S3.controller;

import com.booktory.booktoryserver.Image_S3.dto.request.ImageDTO;
import com.booktory.booktoryserver.Image_S3.service.ImageService;
import com.booktory.booktoryserver.common.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Image API", description = "이미지 관리 API")
public class ImageController {

    private final ImageService imageService;

    @Operation(summary = "이미지 저장", description = "이미지를 저장합니다.")
    @PostMapping("/save")
    public CustomResponse imageSave(@ModelAttribute @Parameter(description = "이미지 정보") ImageDTO imageDTO) throws IOException {
        log.info("imageDTO : {}", imageDTO);
        imageService.imageSave(imageDTO);
        return CustomResponse.ok("이미지 저장 완료", null);
    }
}
