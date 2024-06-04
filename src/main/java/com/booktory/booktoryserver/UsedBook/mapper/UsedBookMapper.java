package com.booktory.booktoryserver.UsedBook.mapper;

import com.booktory.booktoryserver.UsedBook.domain.BookEntity;
import com.booktory.booktoryserver.UsedBook.domain.UsedBookImage;
import com.booktory.booktoryserver.UsedBook.domain.UsedBookPostEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UsedBookMapper {
    List<UsedBookPostEntity> getList();

    UsedBookPostEntity getPostById(Long used_book_id);

    int deletePostById(Long used_book_id);

    Long getBookId(Long d_isbn);

    void createBookInfo(BookEntity bookInfo);

    int updatePost(UsedBookPostEntity entity);

    int createPost(UsedBookPostEntity entity);

    int saveFile(UsedBookImage usedBookImage);
}
