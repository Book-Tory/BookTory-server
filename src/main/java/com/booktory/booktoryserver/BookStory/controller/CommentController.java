package com.booktory.booktoryserver.BookStory.controller;

import com.booktory.booktoryserver.BookStory.dto.request.CommentCreateDTO;
import com.booktory.booktoryserver.BookStory.dto.response.CommentViewDTO;
import com.booktory.booktoryserver.BookStory.service.CommentService;
import com.booktory.booktoryserver.common.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stories")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Comments", description = "독후감 게시물 댓글 관련 API")
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/{story_board_id}/comments")
    @Operation(summary = "댓글 조회", description = "독후감 게시물에 달린 모든 댓글을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "댓글 조회 성공", content = @Content(schema = @Schema(implementation = CommentViewDTO.class)))
    public CustomResponse getAllCommentsByStoryId(@PathVariable("story_board_id") @Parameter(description = "독후감 게시물 ID") Long story_board_id) {
        List<CommentViewDTO> comments = commentService.getAllCommentsByStoryId(story_board_id);
        if (!comments.isEmpty()) {
            return CustomResponse.ok("댓글 조회 성공", comments);
        } else {
            return CustomResponse.failure("댓글 조회 실패");
        }
    }

    @PostMapping("/{story_board_id}/comments")
    @Operation(summary = "댓글 등록", description = "독후감 게시물에 새로운 댓글을 등록합니다.")
    @ApiResponse(responseCode = "200", description = "댓글 등록 성공", content = @Content(schema = @Schema(implementation = Boolean.class)))
    public CustomResponse createComment(@PathVariable("story_board_id") @Parameter(description = "독후감 게시물 ID") Long story_board_id,
                                        @RequestBody @Parameter(description = "댓글 생성 정보") CommentCreateDTO commentCreateDTO) {
        commentCreateDTO.setBoardId(story_board_id);
        boolean result = commentService.createComment(commentCreateDTO);
        if (result) {
            return CustomResponse.ok("댓글 등록 성공", true);
        } else {
            return CustomResponse.failure("댓글 등록 실패");
        }
    }

    @DeleteMapping("/comments/{comment_id}")
    @Operation(summary = "댓글 삭제", description = "댓글 ID로 댓글을 삭제합니다.")
    @ApiResponse(responseCode = "200", description = "댓글 삭제 성공", content = @Content(schema = @Schema(implementation = Boolean.class)))
    public CustomResponse deleteComment(@PathVariable("comment_id") @Parameter(description = "삭제할 댓글의 ID") Long comment_id) {
        boolean result = commentService.deleteComment(comment_id);
        if (result) {
            return CustomResponse.ok("댓글 삭제 성공", true);
        } else {
            return CustomResponse.failure("댓글 삭제 실패");
        }
    }

    @PutMapping("/comments/{comment_id}")
    @Operation(summary = "댓글 수정", description = "댓글 ID로 댓글을 수정합니다.")
    @ApiResponse(responseCode = "200", description = "댓글 수정 성공", content = @Content(schema = @Schema(implementation = Boolean.class)))
    public CustomResponse updateComment(@PathVariable("comment_id") @Parameter(description = "수정할 댓글의 ID") Long comment_id,
                                        @RequestBody @Parameter(description = "댓글 수정 정보") CommentCreateDTO commentCreateDTO) {
        boolean result = commentService.updateComment(comment_id, commentCreateDTO);
        if (result) {
            return CustomResponse.ok("댓글 수정 성공", true);
        } else {
            return CustomResponse.failure("댓글 수정 실패");
        }
    }
}
