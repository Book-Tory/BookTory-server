package com.booktory.booktoryserver.BookStory.mapper;

import com.booktory.booktoryserver.BookStory.domain.BookEntity;
import com.booktory.booktoryserver.BookStory.domain.StoryEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface StoryMapper {
    List<StoryEntity> getAllStory();

    List<StoryEntity> getStoryById(Long story_board_id);

    void updateStroy(StoryEntity storyEntity);

    void createStory(StoryEntity storyEntity);

    void deleteStory(@Param("story_board_id") long story_board_id);

    void updateStory(StoryEntity storyEntity);

    Long getBookId(Long dIsbn);

    void saveBookInfo(BookEntity bookInfo);

    Long findIdByEmail(String useremail);

    void createBookInfo(BookEntity bookInfo);
}
