package com.booktory.booktoryserver.BookStory.controller;

import com.booktory.booktoryserver.BookStory.domain.StoryEntity;
import com.booktory.booktoryserver.BookStory.dto.BookDTO;
import com.booktory.booktoryserver.BookStory.dto.StoryDTO;
import com.booktory.booktoryserver.BookStory.service.StoryService;
import com.booktory.booktoryserver.Users.mapper.UserMapper;
import com.booktory.booktoryserver.Users.model.UserEntity;
import com.booktory.booktoryserver.common.CustomResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
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

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/stories")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Stories", description = "독후감(스토리) 관련 API")
public class StoryController {
    private final StoryService storyService;
    private final UserMapper userMapper;

    @GetMapping("/mystories")
    @Operation(summary = "독후감 전체 조회", description = "모든 독후감을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "독후감 전체 조회 성공", content = @Content(schema = @Schema(implementation = StoryEntity.class)))
    public CustomResponse getAllStory() {
        List<StoryEntity> stories = storyService.getAllStory();

        if (!stories.isEmpty()) {
            return CustomResponse.ok("독후감(스토리) 전체 조회 성공", stories);
        } else {
            return CustomResponse.failure("독후감(스토리) 전체 조회 실패");
        }
    }

      
    @GetMapping("/{story_board_id}")
    @Operation(summary = "독후감 상세보기", description = "ID로 독후감을 상세 조회합니다.")
    @ApiResponse(responseCode = "200", description = "독후감 상세 조회 성공", content = @Content(schema = @Schema(implementation = StoryEntity.class)))
    public CustomResponse getStoryById (@PathVariable ("story_board_id") Long story_board_id, @AuthenticationPrincipal UserDetails useremail){
        StoryDTO myStory = storyService.getStoryById(story_board_id);

        String userEmail = useremail.getUsername();
        Optional<UserEntity> userEntity = userMapper.findByEmail(userEmail);
        Long userId = userEntity.get().getUser_id();

        myStory.setUserId(userId);

        System.out.println("myStory1 = " + myStory);

        if(myStory != null){
            //StoryDTO의 인자가 없어도 되는가??

            System.out.println("response2 = " + myStory);
            return CustomResponse.ok("독후감 선택 성공", myStory);
        }else{
            return CustomResponse.failure("독후감 선택 실패");
        }
        //return storyService.getStoryById(story_board_id);
    }


    //독후감(스토리) 등록(작성)
    @PostMapping
    @Operation(summary = "독후감 등록", description = "새로운 독후감을 등록합니다.")
    @ApiResponse(responseCode = "200", description = "독후감 등록 성공", content = @Content(schema = @Schema(implementation = String.class)))
    public CustomResponse createStory(@PathVariable ("d_isbn") Long d_isbn, @RequestBody StoryDTO storyDTO, @AuthenticationPrincipal UserDetails userDetails) throws IOException {
        String useremail = userDetails.getUsername();

        String result = storyService.createStory(d_isbn, storyDTO, useremail);

        if (result != null) {
            return CustomResponse.ok(result, null);
        } else {
            return CustomResponse.failure("독후감 등록에 실패하였습니다");
        }
    }
  
  
    @DeleteMapping("/{story_board_id}")
    @Operation(summary = "독후감 삭제", description = "ID로 독후감을 삭제합니다.")
    @ApiResponse(responseCode = "200", description = "독후감 삭제 성공", content = @Content(schema = @Schema(implementation = String.class)))
    public void deleteStory(@PathVariable Long story_board_id){
        storyService.deleteStory(story_board_id);
    }

    @PutMapping("/{story_board_id}")
    @Operation(summary = "독후감 수정", description = "ID로 독후감을 수정합니다.")
    @ApiResponse(responseCode = "200", description = "독후감 수정 성공", content = @Content(schema = @Schema(implementation = String.class)))
    public CustomResponse updateStory(@PathVariable("story_board_id") @Parameter(description = "수정할 독후감 ID") Long id,
                                      @RequestBody @Parameter(description = "독후감 수정 정보") StoryDTO storyDTO) {
        storyService.updateStory(id, storyDTO);
        return CustomResponse.ok("독후감 수정 성공", null);
    }

    @GetMapping("/books")
    @Operation(summary = "책 검색", description = "책 이름으로 책을 검색합니다.")
    @ApiResponse(responseCode = "200", description = "책 검색 성공", content = @Content(schema = @Schema(implementation = BookDTO.class)))
    public CustomResponse searchBooks(@RequestParam("query") @Parameter(description = "검색할 책 이름") String query,
                                      @RequestParam("display") @Parameter(description = "표시할 결과 수") int display,
                                      @RequestParam("start") @Parameter(description = "검색 시작 위치") int startposition,
                                      @RequestParam("sort") @Parameter(description = "검색 정렬 순서") String searchhresult) throws JsonProcessingException {
        List<BookDTO> bookInfoList = storyService.searchBooks(query, display, startposition, searchhresult);

        if (!bookInfoList.isEmpty()) {
            return CustomResponse.ok("조회 성공", bookInfoList);
        } else {
            return CustomResponse.failure("조회 실패");
        }
    }


    //isbn값에 따른 책 상세 조회 => isbn 값으로 책 정보 조회
    //모달창에서 하나의 책 선택
    @GetMapping("/book/{d_isbn}")
    @Operation(summary = "책 상세 조회", description = "ISBN으로 책을 상세 조회합니다.")
    @ApiResponse(responseCode = "200", description = "책 상세 조회 성공", content = @Content(schema = @Schema(implementation = BookDTO.class)))
    public CustomResponse getBookByIsbn(@PathVariable("d_isbn") @Parameter(description = "조회할 책의 ISBN") Long d_isbn) throws JsonProcessingException {
        BookDTO bookInfo = storyService.getBookByIsbn(d_isbn);

        if (bookInfo != null) {
            return CustomResponse.ok("조회 성공", bookInfo);
        } else {
            return CustomResponse.failure("조회 실패");
        }
    }

    @GetMapping("/book/name")
    @Operation(summary = "책 이름으로 상세 조회", description = "책 이름으로 책을 상세 조회합니다.")
    @ApiResponse(responseCode = "200", description = "책 이름으로 조회 성공", content = @Content(schema = @Schema(implementation = BookDTO.class)))
    public CustomResponse getBookByname(@RequestParam("book_name") @Parameter(description = "조회할 책의 이름") String bookName) throws JsonProcessingException {
        BookDTO bookInfo = storyService.getBookByName(bookName);

        if (bookInfo != null) {
            return CustomResponse.ok("조회 성공", bookInfo);
        } else {
            return CustomResponse.failure("조회 실패");
        }
    }
}
