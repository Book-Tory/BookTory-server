package com.booktory.booktoryserver.BookStory.controller;

import com.booktory.booktoryserver.BookStory.domain.BookEntity;
import com.booktory.booktoryserver.BookStory.domain.StoryEntity;
import com.booktory.booktoryserver.BookStory.dto.BookDTO;
import com.booktory.booktoryserver.BookStory.dto.StoryDTO;
import com.booktory.booktoryserver.BookStory.service.StoryService;
import com.booktory.booktoryserver.common.CustomResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
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



    //입력한 값에 따른 책 전체 조회
    //네이버 API에서 제공한다는 파라미터를 추가
    @GetMapping("/books")
    public CustomResponse searchBooks(@RequestParam ("query") String query, @RequestParam ("display") int display, @RequestParam ("start") int startposition ,@RequestParam ("sort") String searchhresult) throws JsonProcessingException{
        List<BookDTO> bookInfoList = storyService.searchBooks(query, display, startposition , searchhresult);
        //다음 매개변수들을 가진 'searchBooks' 함수를 'bookInfoList'라는 이름의 Entity객체로 만든다.

        if(!bookInfoList.isEmpty()){
            return CustomResponse.ok("조회 성공", bookInfoList);
        }else{
            return CustomResponse.failure("조회 실패");
        }
    }




}
