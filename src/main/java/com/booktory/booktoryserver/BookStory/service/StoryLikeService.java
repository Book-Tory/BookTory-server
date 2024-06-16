package com.booktory.booktoryserver.BookStory.service;

import com.booktory.booktoryserver.BookStory.mapper.StoryLikeMapper;
import com.booktory.booktoryserver.BookStory.mapper.StoryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoryLikeService {
    private final StoryLikeMapper storyLikeMapper;
    public boolean likeStory(Long story_board_id, Long user_id) {
        log.info("확인용 : " + storyLikeMapper.isAlreadyLiked(story_board_id, user_id));
        //이미 좋아요를 누른 경우 false를 반환
        if(storyLikeMapper.isAlreadyLiked(story_board_id, user_id)){

            return false;
        }
        //좋아요가 눌리지 않은 경우
        else{
            //좋아요를 누르고
            storyLikeMapper.likeStory(story_board_id, user_id);
            //좋아요수를 증가.  좋아요 수는 게시물당 다르기 때문에 매개변수가 story_board_id가 되는게 맞는거 같다.
            storyLikeMapper.incrementLoveCount(story_board_id);
            return true;
        }
    }
}
