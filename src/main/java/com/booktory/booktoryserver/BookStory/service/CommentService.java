package com.booktory.booktoryserver.BookStory.service;

import com.booktory.booktoryserver.BookStory.domain.CommentEntity;
import com.booktory.booktoryserver.BookStory.dto.request.CommentCreateDTO;
import com.booktory.booktoryserver.BookStory.dto.response.CommentViewDTO;
import com.booktory.booktoryserver.BookStory.mapper.CommentMapper;
import com.booktory.booktoryserver.Users.model.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {
    private final CommentMapper commentMapper;

    public List<CommentViewDTO> getAllCommentsByStoryId(Long story_board_id) {
        List<CommentEntity> comments = commentMapper.getAllCommentsByStoryId(story_board_id);
        return comments.stream()
                .map(CommentViewDTO::fromEntity)
                .collect(Collectors.toList());
    }



    public Long createComment(CommentCreateDTO commentCreateDTO) {
        CommentEntity commentEntity = CommentCreateDTO.toEntity(commentCreateDTO);

        commentMapper.createComment(commentEntity);
        long commentId = commentEntity.getComment_id();
        return commentId;
    }

    public boolean deleteComment(Long comment_id) {
        return commentMapper.deleteComment(comment_id) > 0;
    }

    public boolean updateComment(Long comment_id, CommentCreateDTO commentCreateDTO) {
        CommentEntity commentEntity = CommentCreateDTO.toEntity(commentCreateDTO);
        commentEntity.setComment_id(comment_id);
        return commentMapper.updateComment(commentEntity) > 0;
    }

    public CommentViewDTO getCommentsBycommentId(long commentId) {

         return CommentViewDTO.fromEntity(commentMapper.getCommentsBycommentId(commentId));
    }


//    //댓글 id를 통한 상세조회
//    public CommentViewDTO getCommentById(Long comment_id) {
//        CommentEntity comment = commentMapper.getCommentById(comment_id);
//        return comment != null ? CommentViewDTO.fromEntity(comment) : null;
//    }
}
