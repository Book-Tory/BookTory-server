package com.booktory.booktoryserver.BookStory.controller;

import com.booktory.booktoryserver.BookStory.domain.BookEntity;
import com.booktory.booktoryserver.BookStory.domain.StoryEntity;
import com.booktory.booktoryserver.BookStory.dto.BookDTO;
import com.booktory.booktoryserver.BookStory.dto.StoryDTO;
import com.booktory.booktoryserver.BookStory.service.StoryService;
import com.booktory.booktoryserver.common.CustomResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/stories")
@RequiredArgsConstructor
@Slf4j
public class StoryController {
    private final StoryService storyService;

    //독후감(스토리) 전체 조회
    @GetMapping("/mystories")
    public CustomResponse getAllStory(){
        List<StoryEntity> stories = storyService.getAllStory();

        if(!stories.isEmpty()){
            return CustomResponse.ok("독후감(스토리) 전체 조회 성공", stories);
        }else{
            return CustomResponse.failure("독후감(스토리) 전체 조회 실패");
        }
    }


    //독후감 상세보기
    @GetMapping("/{story_board_id}")
    public StoryEntity getStoryById (@PathVariable("story_board_id") Long story_board_id){
        return storyService.getStoryById(story_board_id);
    }


    //독후감(스토리) 등록(작성)
    @PostMapping("/{d_isbn}")
    public CustomResponse createStory(@PathVariable ("d_isbn") Long d_isbn, @RequestBody StoryDTO storyDTO, @AuthenticationPrincipal UserDetails userDetails) throws IOException {
        String useremail = userDetails.getUsername();

        String result = storyService.createStory(d_isbn, storyDTO, useremail);

        if(result != null){
            return CustomResponse.ok(result, null);
        }else{
            return CustomResponse.failure("독후감 등록에 실패하였습니다");
        }
    }

    //독후감(스토리) 게시물 id를 통한 삭제
    @DeleteMapping("/{story_board_id}")
    public void deleteStory(@PathVariable Long id){
        storyService.deleteStory(id);
    }


    //독후감(스토리) 게시물 id를 통한 업데이트
    @PutMapping("/{story_board_id}")
    public void updateStory(@PathVariable Long id,@RequestBody StoryDTO storyDTO){
        System.out.println("book_id = " + id);
        storyService.updateStory(id, storyDTO);
    }



//----------------------------------------------------------------------------------


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

    //isbn값에 따른 책 상세 조회 => isbn 값으로 책 정보 조회
    //모달창에서 하나의 책 선택
    @GetMapping("/book/{d_isbn}")
    public CustomResponse getBookByIsbn(@PathVariable ("d_isbn") Long d_isbn) throws JsonProcessingException {
        BookDTO bookInfo = storyService.getBookByIsbn(d_isbn);

        if (bookInfo != null) {
            return CustomResponse.ok("조회 성공", bookInfo);
        } else {
            return CustomResponse.failure("조회 실패");
        }
    }

    //필요한지는 모르겠지만 이름을 통한 책 상세보기 요청
    public CustomResponse getBookByname(@RequestParam("/book/book_name") String bookName) throws JsonProcessingException {
        BookDTO bookInfo = storyService.getBookByName(bookName);

        if(bookInfo != null){
            return CustomResponse.ok("조회 성공", bookInfo);
        }else{
            return CustomResponse.failure("조회 실패");
        }
    }


}
