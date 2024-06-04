package com.booktory.booktoryserver.BookStory.mapper;

import com.booktory.booktoryserver.BookStory.domain.StoryEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StoryMapper {
    List<StoryEntity> getAllStory();

    StoryEntity getStoryById(Long story_board_id);

    void createStory(StoryEntity storyEntity);

    void deleteStory(@Param("story_board_id") long id);

    void updateStory(StoryEntity storyEntity);

}
