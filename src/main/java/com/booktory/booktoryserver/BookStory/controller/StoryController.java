package com.booktory.booktoryserver.BookStory.controller;

import com.booktory.booktoryserver.BookStory.domain.StoryEntity;
import com.booktory.booktoryserver.BookStory.dto.StoryDTO;
import com.booktory.booktoryserver.BookStory.service.StoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stories")
@RequiredArgsConstructor
public class StoryController {
    private final StoryService storyService;

    @GetMapping("/mystories")//독후감(스토리) 전체 조회
    public List<StoryEntity> getAllStory(){
        return storyService.getAllStory();
    }


    @GetMapping("/{story_board_id}")
    public StoryEntity getStoryById (@PathVariable("story_board_id") Long story_board_id){
        return storyService.getStoryById(story_board_id);
    }



    @PostMapping//독후감(스토리) 등록(작성)
    public void createStory(@RequestBody StoryDTO storyDTO){
        System.out.println("storyDTO = " + storyDTO);
        storyService.createStory(storyDTO);
    }

    @DeleteMapping("/{id}") //독후감(스토리) 게시물 id를 통한 삭제
    public void deleteStory(@PathVariable Long id){
        storyService.deleteStory(id);
    }

    @PutMapping("/{id}") //독후감(스토리) 게시물 id를 통한 업데이트
    public void updateStory(@PathVariable Long id,@RequestBody StoryDTO storyDTO){
        System.out.println("book_id = " + id);
        storyService.updateStory(id, storyDTO);
    }

}
