package com.booktory.booktoryserver.BookStory.controller;


import com.booktory.booktoryserver.BookStory.dto.response.CommentViewDTO;
import com.booktory.booktoryserver.BookStory.service.CommentService;
import com.booktory.booktoryserver.QnAComment.service.QnaCommentService;
import com.booktory.booktoryserver.common.CustomResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/stories")
@RequiredArgsConstructor
@Slf4j
public class CommentController {
    private final CommentService commentService;

    //우선 독후감(게시물) 하나에 달린 모든 댓글 조회
    @GetMapping("/{story_board_id}/comments")
    public CustomResponse getAllCommentsByStoryId(@PathVariable Long story_board_id){
        List<CommentViewDTO> comments = commentService.getAllCommentsByStoryId(story_board_id);
        if(!comments.isEmpty()){
            return CustomResponse.ok("댓글 조회 성공", comments);
        }else{
            return CustomResponse.failure("댓글 조회 실패");
        }
    }


}
