package com.booktory.booktoryserver.BookStory.controller;

import com.booktory.booktoryserver.BookStory.service.StoryLikeService;
import com.booktory.booktoryserver.BookStory.service.StoryService;
import com.booktory.booktoryserver.Users.mapper.UserMapper;
import com.booktory.booktoryserver.Users.model.UserEntity;
import com.booktory.booktoryserver.common.CustomResponse;
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
public class StoryLikeController {
    private final StoryLikeService storyLikeService;

    private final UserMapper userMapper;
    @PostMapping("/{story_board_id}/like")
    public CustomResponse likeStory(@PathVariable Long story_board_id, @AuthenticationPrincipal UserDetails userDetails){
        String useremail = userDetails.getUsername();

        Optional<UserEntity> userEntity = userMapper.findByEmail(useremail);
        Long user_id = userEntity.get().getUser_id();

        boolean result = storyLikeService.likeStory(story_board_id, user_id);
        if(result){
            return CustomResponse.ok("좋아요 성공", true);
            //CustomResponse의 반환 값이 두개인 것 같다. 값을 뭐로 해야하나?
        }else{
            return CustomResponse.failure("이미 좋아요를 누른 게시물입니다.");
        }
    }

}
