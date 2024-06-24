package com.booktory.booktoryserver.BookStory.controller;

import com.booktory.booktoryserver.BookStory.service.StoryLikeService;
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

import java.util.Optional;

@RestController
@RequestMapping("/api/stories")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Story Likes", description = "독후감(스토리) 좋아요 관련 API")
public class StoryLikeController {
    private final StoryLikeService storyLikeService;
    private final UserMapper userMapper;

    @PostMapping("/{story_board_id}/like")
    @Operation(summary = "독후감 좋아요", description = "독후감 게시물에 좋아요를 추가합니다.")
    @ApiResponse(responseCode = "200", description = "좋아요 성공", content = @Content(schema = @Schema(implementation = Boolean.class)))
    public CustomResponse likeStory(@PathVariable("story_board_id") @Parameter(description = "좋아요를 추가할 독후감 ID") Long story_board_id,
                                    @AuthenticationPrincipal @Parameter(hidden = true) UserDetails userDetails) {
        String useremail = userDetails.getUsername();
        Optional<UserEntity> userEntity = userMapper.findByEmail(useremail);
        Long user_id = userEntity.get().getUser_id();

        boolean result = storyLikeService.likeStory(story_board_id, user_id);
        if (result) {
            return CustomResponse.ok("좋아요 성공", true);
        } else {
            return CustomResponse.failure("이미 좋아요를 누른 게시물입니다.");
        }
    }

    @DeleteMapping("/{story_board_id}/unlike")
    @Operation(summary = "독후감 좋아요 취소", description = "독후감 게시물에 좋아요를 취소합니다.")
    @ApiResponse(responseCode = "200", description = "좋아요 취소 성공", content = @Content(schema = @Schema(implementation = Boolean.class)))
    public CustomResponse unlikeStory(@PathVariable("story_board_id") @Parameter(description = "좋아요를 취소할 독후감 ID") Long story_board_id,
                                      @AuthenticationPrincipal @Parameter(hidden = true) UserDetails userDetails) {
        String useremail = userDetails.getUsername();
        Optional<UserEntity> userEntity = userMapper.findByEmail(useremail);
        Long user_id = userEntity.get().getUser_id();

        boolean result = storyLikeService.unlikeStory(story_board_id, user_id);
        if (result) {
            return CustomResponse.ok("좋아요 취소 성공", true);
        } else {
            return CustomResponse.failure("좋아요를 취소 할 수 없습니다. 좋아요를 누르지 않은 게시물입니다.");
        }
    }
}
