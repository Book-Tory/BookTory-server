package com.booktory.booktoryserver.UsedBook.mapper;

import com.booktory.booktoryserver.UsedBook.domain.BookEntity;
import com.booktory.booktoryserver.UsedBook.domain.UsedBookImage;
import com.booktory.booktoryserver.UsedBook.domain.UsedBookPostEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UsedBookMapper {
    List<UsedBookPostEntity> getList();

    List<UsedBookPostEntity> getPostById(Long used_book_id);

    int deletePostById(Long used_book_id);

    Long getBookId(Long d_isbn);

    void createBookInfo(BookEntity bookInfo);

    int updatePost(UsedBookPostEntity entity);

    int createPost(UsedBookPostEntity entity);

    int saveFile(UsedBookImage usedBookImage);

    List<UsedBookImage> getUsedBookImageById(Long used_book_id);

    void deleteImageById(Long used_book_id);

    int deleteImageByImageId(Long used_book_image_id);

    UsedBookImage getUsedBookImageByImageId(Long used_book_image_id);

    Long findIdByEmail(String username);
}
