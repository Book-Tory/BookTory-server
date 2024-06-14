package com.booktory.booktoryserver.BookStory.controller;


import com.booktory.booktoryserver.BookStory.dto.request.CommentCreateDTO;
import com.booktory.booktoryserver.BookStory.dto.response.CommentViewDTO;
import com.booktory.booktoryserver.BookStory.service.CommentService;
import com.booktory.booktoryserver.QnAComment.service.QnaCommentService;
import com.booktory.booktoryserver.common.CustomResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/{story_board_id}/comments")
    public CustomResponse createComment(@PathVariable Long story_board_id, @RequestBody CommentCreateDTO commentCreateDTO){
        commentCreateDTO.setBoardId(story_board_id);
        boolean result = commentService.createComment(commentCreateDTO);
        if(result){
            return CustomResponse.ok("댓글 등록 성공", true);
        }else{
            return CustomResponse.failure("댓글 등록 실패");
        }
    }

    @DeleteMapping("/comments/{comment_id}")
    public CustomResponse deleteComment(@PathVariable Long comment_id) {
        boolean result = commentService.deleteComment(comment_id);
        if(result) {
            return CustomResponse.ok("댓글 삭제 성공", true);
        } else {
            return CustomResponse.failure("댓글 삭제 실패");
        }
    }

    @PutMapping("/comments/{comment_id}")
    public CustomResponse updateComment(@PathVariable Long comment_id, @RequestBody CommentCreateDTO commentCreateDTO) {
        boolean result = commentService.updateComment(comment_id, commentCreateDTO);
        if(result) {
            return CustomResponse.ok("댓글 수정 성공", true);
        } else {
            return CustomResponse.failure("댓글 수정 실패");
        }
    }

    @GetMapping("/comments/{comment_id}")
    public CustomResponse getCommentById(@PathVariable Long comment_id) {
        CommentViewDTO comment = commentService.getCommentById(comment_id);
        if(comment != null) {
            return CustomResponse.ok("댓글 조회 성공", comment);
        } else {
            return CustomResponse.failure("댓글 조회 실패");
        }
    }
}
