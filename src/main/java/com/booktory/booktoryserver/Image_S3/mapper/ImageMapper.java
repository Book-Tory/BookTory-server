package com.booktory.booktoryserver.Image_S3.mapper;

import com.booktory.booktoryserver.Image_S3.domain.ImageEntity;
import com.booktory.booktoryserver.Image_S3.domain.ImageFileEntity;
import com.booktory.booktoryserver.Image_S3.dto.request.ImageFileDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSessionFactory;

@Mapper
public interface ImageMapper {

    int save(ImageEntity image);

    int saveFile(ImageFileEntity imageFile);

}
