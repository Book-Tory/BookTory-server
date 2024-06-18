package com.booktory.booktoryserver.UsedBook.controller;

import com.booktory.booktoryserver.UsedBook.dto.request.UsedBookInfoDTO;
import com.booktory.booktoryserver.UsedBook.dto.response.BookDTO;
import com.booktory.booktoryserver.UsedBook.dto.response.ResponseDTO;
import com.booktory.booktoryserver.UsedBook.dto.response.UsedBookPostDTO;
import com.booktory.booktoryserver.UsedBook.page.PageRequest;
import com.booktory.booktoryserver.UsedBook.page.PageResponse;
import com.booktory.booktoryserver.UsedBook.service.UsedBookService;
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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/used-books")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Used Books", description = "중고 서적 관련 API")
public class UsedBookController {
    private final UsedBookService usedBookService;
    private final UserMapper userMapper;

    @GetMapping("/books")
    @Operation(summary = "책 검색", description = "책 이름으로 책을 검색합니다.")
    @ApiResponse(responseCode = "200", description = "조회 완료", content = @Content(schema = @Schema(implementation = BookDTO.class)))
    public CustomResponse searchBooks(@RequestParam("query") @Parameter(description = "검색할 책 이름") String query,
                                      @RequestParam("display") @Parameter(description = "표시할 결과 수") int display) throws JsonProcessingException {
        List<BookDTO> bookInfoList = usedBookService.searchBooks(query, display);

        if (!bookInfoList.isEmpty()) {
            return CustomResponse.ok("조회 완료", bookInfoList);
        } else {
            return CustomResponse.failure("조회 실패");
        }
    }

    @GetMapping("/book/{d_isbn}")
    @Operation(summary = "책 조회", description = "ISBN으로 책을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = BookDTO.class)))
    public CustomResponse getBookByIsbn(@PathVariable("d_isbn") @Parameter(description = "조회할 책의 ISBN") Long d_isbn) throws JsonProcessingException {
        BookDTO bookInfo = usedBookService.getBookByIsbn(d_isbn);

        if (bookInfo != null) {
            return CustomResponse.ok("조회 성공", bookInfo);
        } else {
            return CustomResponse.failure("조회 실패");
        }
    }

    @GetMapping("/list")
    @Operation(summary = "중고 서적 글 리스트 조회", description = "중고 서적 글 리스트를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = PageResponse.class)))
    public CustomResponse getList(@Valid @Parameter(description = "페이지 요청 정보") PageRequest pageRequest,
                                  BindingResult bindingResult,
                                  @RequestParam(value = "searchKey", required = false) @Parameter(description = "검색 키워드") String searchKey) {
        if (bindingResult.hasErrors()) {
            pageRequest = PageRequest.builder().build();
        }

        PageResponse<UsedBookPostDTO> pageResponse = usedBookService.getList(searchKey, pageRequest);

        if (!pageResponse.getList().isEmpty()) {
            return CustomResponse.ok("중고 서적 글 리스트 조회 성공", pageResponse);
        } else {
            return CustomResponse.failure("중고 서적 글 리스트 조회 실패");
        }
    }

    @GetMapping("/{used_book_id}")
    @Operation(summary = "중고 서적 글 상세보기", description = "중고 서적 글을 ID로 조회합니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))
    public CustomResponse getPostById(@PathVariable("used_book_id") @Parameter(description = "조회할 중고 서적 글의 ID") Long used_book_id,
                                      @AuthenticationPrincipal @Parameter(hidden = true) UserDetails user) {
        UsedBookPostDTO usedBookPost = usedBookService.getPostById(used_book_id);

        String userEmail = user.getUsername();

        Optional<UserEntity> userEntity = userMapper.findByEmail(userEmail);

        Long userId = userEntity.get().getUser_id();

        if (usedBookPost != null) {
            ResponseDTO response = new ResponseDTO(usedBookPost, userId);
            return CustomResponse.ok("조회 성공", response);
        } else {
            return CustomResponse.failure("조회 실패");
        }
    }

    @DeleteMapping("/{used_book_id}")
    @Operation(summary = "중고 서적 글 삭제", description = "중고 서적 글을 ID로 삭제합니다.")
    @ApiResponse(responseCode = "200", description = "삭제 성공", content = @Content(schema = @Schema(implementation = String.class)))
    public CustomResponse deletePostById(@PathVariable("used_book_id") @Parameter(description = "삭제할 중고 서적 글의 ID") Long used_book_id) {
        int result = usedBookService.deletePostById(used_book_id);

        if (result > 0) {
            return CustomResponse.ok("삭제되었습니다.", result);
        } else {
            return CustomResponse.failure("삭제 실패하였습니다.");
        }
    }

    @PutMapping("/{used_book_id}/{d_isbn}")
    @Operation(summary = "중고 서적 글 수정", description = "중고 서적 글을 ID와 ISBN으로 수정합니다.")
    @ApiResponse(responseCode = "200", description = "수정 성공", content = @Content(schema = @Schema(implementation = String.class)))
    public CustomResponse updatePost(@PathVariable("used_book_id") @Parameter(description = "수정할 중고 서적 글의 ID") Long used_book_id,
                                     @PathVariable("d_isbn") @Parameter(description = "수정할 책의 ISBN") Long d_isbn,
                                     @ModelAttribute @Parameter(description = "수정할 중고 서적 정보") UsedBookInfoDTO usedBookInfoDTO) throws IOException {
        int result = usedBookService.updatePost(used_book_id, d_isbn, usedBookInfoDTO);

        if (result > 0) {
            return CustomResponse.ok("수정되었습니다.", result);
        } else {
            return CustomResponse.failure("수정에 실패하였습니다.");
        }
    }

    @PostMapping("/{d_isbn}")
    @Operation(summary = "중고 서적 글 등록", description = "중고 서적 글을 ISBN으로 등록합니다.")
    @ApiResponse(responseCode = "200", description = "등록 성공", content = @Content(schema = @Schema(implementation = String.class)))
    public CustomResponse createPost(@PathVariable("d_isbn") @Parameter(description = "등록할 책의 ISBN") Long d_isbn,
                                     @ModelAttribute @Parameter(description = "등록할 중고 서적 정보") UsedBookInfoDTO usedBookInfoDTO,
                                     @AuthenticationPrincipal @Parameter(hidden = true) UserDetails user) throws IOException {
        String username = user.getUsername();

        String result = usedBookService.createPost(d_isbn, usedBookInfoDTO, username);

        if (result != null) {
            return CustomResponse.ok(result, null);
        } else {
            return CustomResponse.failure("등록에 실패하였습니다.");
        }
    }

    @DeleteMapping("/image/{used_book_image_id}")
    @Operation(summary = "중고 서적 이미지 삭제", description = "중고 서적의 이미지를 ID로 삭제합니다.")
    @ApiResponse(responseCode = "200", description = "삭제 성공", content = @Content(schema = @Schema(implementation = String.class)))
    public CustomResponse deleteImageByImageId(@PathVariable("used_book_image_id") @Parameter(description = "삭제할 이미지의 ID") Long used_book_image_id) {
        int result = usedBookService.deleteImageByImageId(used_book_image_id);

        if (result > 0) {
            return CustomResponse.ok("선택한 이미지가 삭제되었습니다.", null);
        } else {
            return CustomResponse.failure("이미지 삭제에 실패하였습니다.");
        }
    }
}
