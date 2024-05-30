package com.booktory.booktoryserver.Image_S3.controller;

import com.booktory.booktoryserver.Image_S3.dto.request.ImageDTO;
import com.booktory.booktoryserver.Image_S3.service.ImageService;
import com.booktory.booktoryserver.common.CustomResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
@Slf4j
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/save")
    public CustomResponse imageSave(@ModelAttribute ImageDTO imageDTO) throws IOException {
        log.info("imageDTO : {}", imageDTO);

        imageService.imageSave(imageDTO);

        return null;
    }
}
