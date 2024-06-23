package com.booktory.booktoryserver.UsedBook.mapper;

import com.booktory.booktoryserver.UsedBook.domain.*;
import com.booktory.booktoryserver.UsedBook.page.PageRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UsedBookMapper {
    List<UsedBookPostEntity> getList(String searchKey, PageRequest pageRequest);

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

    int countList(String searchKey);

    int updateStatus(Long used_book_id, Long status);

    List<MyPageUsedBookEntity> myPageUsedBookList(Long userId);
}
