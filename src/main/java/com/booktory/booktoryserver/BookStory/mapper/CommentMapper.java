package com.booktory.booktoryserver.BookStory.mapper;

import com.booktory.booktoryserver.BookStory.domain.CommentEntity;
import com.booktory.booktoryserver.BookStory.dto.response.CommentViewDTO;

import java.util.List;

public interface CommentMapper {
    List<CommentEntity> getAllCommentsByStoryId(Long story_board_id);
}
