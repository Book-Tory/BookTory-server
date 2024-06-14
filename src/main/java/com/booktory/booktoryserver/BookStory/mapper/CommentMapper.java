package com.booktory.booktoryserver.BookStory.mapper;

import com.booktory.booktoryserver.BookStory.domain.CommentEntity;
import com.booktory.booktoryserver.BookStory.dto.response.CommentViewDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
    List<CommentEntity> getAllCommentsByStoryId(Long story_board_id);
    int createComment(CommentEntity commentEntity);
    int deleteComment(Long comment_id);
    int updateComment(CommentEntity commentEntity);
}
