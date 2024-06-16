package com.booktory.booktoryserver.BookStory.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StoryLikeMapper {


    boolean isAlreadyLiked(Long story_board_id, Long user_id);

    void likeStory(Long story_board_id, Long user_id);

    void incrementLoveCount(Long story_board_id);
}
