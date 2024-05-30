package com.booktory.booktoryserver.UsedBook.mapper;

import com.booktory.booktoryserver.UsedBook.domain.UsedBookPostEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UsedBookMapper {
    List<UsedBookPostEntity> getList();

    UsedBookPostEntity getPostById(Long used_book_id);
}
