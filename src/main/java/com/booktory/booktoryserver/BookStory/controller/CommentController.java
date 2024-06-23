package com.booktory.booktoryserver.BookStory.controller;

import com.booktory.booktoryserver.BookStory.domain.CommentEntity;
import com.booktory.booktoryserver.BookStory.dto.request.CommentCreateDTO;
import com.booktory.booktoryserver.BookStory.dto.response.CommentViewDTO;
import com.booktory.booktoryserver.BookStory.service.CommentService;
import com.booktory.booktoryserver.Users.mapper.UserMapper;
import com.booktory.booktoryserver.Users.model.UserEntity;
import com.booktory.booktoryserver.common.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/stories")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Comments", description = "독후감 게시물 댓글 관련 API")
public class CommentController {
    private final CommentService commentService;
    private final UserMapper userMapper;


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
                                        @RequestBody @Parameter(description = "댓글 생성 정보") CommentCreateDTO commentCreateDTO, @AuthenticationPrincipal UserDetails useremail) {

        System.out.println(story_board_id);
        String userEmail = useremail.getUsername();
        Optional<UserEntity> userEntity = userMapper.findByEmail(userEmail);


        Long userId = userEntity.get().getUser_id();

        log.info(String.valueOf(userId));
        System.out.println("현재 로그인한 댓글 작성자: " + userId);

        commentCreateDTO.setBoardId(story_board_id);//현재 게시물로 댓글의 게시물id 설정
        commentCreateDTO.setUserId(userId); //현재 로그인한 아이디로 댓글 작성자의 userId 설정

        long commentId = commentService.createComment(commentCreateDTO);
        System.out.println("result = " + commentId);

        CommentViewDTO commentViewDTO = commentService.getCommentsBycommentId(commentId);

        System.out.println("commentEntity = " + commentViewDTO);
        
        if (commentId >0) {
            return CustomResponse.ok("댓글 등록 성공", commentViewDTO);
        } else {
            return CustomResponse.failure("댓글 등록 실패");
        }
    }

    @DeleteMapping("/comments/{comment_id}")
    @Operation(summary = "댓글 삭제", description = "댓글 ID로 댓글을 삭제합니다.")
    @ApiResponse(responseCode = "200", description = "댓글 삭제 성공", content = @Content(schema = @Schema(implementation = Boolean.class)))
    public CustomResponse deleteComment(@PathVariable("comment_id") @Parameter(description = "삭제할 댓글의 ID") Long comment_id, @AuthenticationPrincipal UserDetails useremail) {

        String userEmail = useremail.getUsername();
        Optional<UserEntity> userEntityOptional = userMapper.findByEmail(userEmail);


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
