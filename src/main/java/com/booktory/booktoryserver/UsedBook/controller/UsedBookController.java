package com.booktory.booktoryserver.UsedBook.controller;

import com.booktory.booktoryserver.UsedBook.domain.UsedBookPostEntity;
import com.booktory.booktoryserver.UsedBook.dto.request.UsedBookInfoDTO;
import com.booktory.booktoryserver.UsedBook.dto.response.BookDTO;
import com.booktory.booktoryserver.UsedBook.dto.response.UsedBookPostDTO;
import com.booktory.booktoryserver.UsedBook.service.UsedBookService;
import com.booktory.booktoryserver.common.CustomResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/used-books")
@RequiredArgsConstructor
@Slf4j

public class UsedBookController {
    private final UsedBookService usedBookService;

    // 책 전체 조회
    @GetMapping("/books")
    public CustomResponse searchBooks(@RequestParam ("query") String query, @RequestParam ("display") int display) throws JsonProcessingException {
        List<BookDTO> bookInfoList = usedBookService.searchBooks(query, display);

        if (!bookInfoList.isEmpty()) {
            return CustomResponse.ok("조회 완료", bookInfoList);
        } else {
            return CustomResponse.failure("조회 실패");
        }
    }

    // 선택한 책 조회
    @GetMapping("/book/{d_isbn}")
    public CustomResponse getBookByIsbn (@PathVariable ("d_isbn") Long d_isbn) throws JsonProcessingException {
        BookDTO bookInfo = usedBookService.getBookByIsbn(d_isbn);

        if (bookInfo != null) {
            return CustomResponse.ok("조회 성공", bookInfo);
        } else {
            return CustomResponse.failure("조회 실패");
        }
    }

    // 중고 서적 글 리스트 조회
    @GetMapping("/list")
    public CustomResponse getList () {
        List<UsedBookPostDTO> list = usedBookService.getList();

        if (!list.isEmpty()) {
            return CustomResponse.ok("중고 서적 글 리스트 조회 성공", list);
        } else {
            return CustomResponse.failure("중고 서적 글 리스트 조회 실패");
        }
    }

    // 중고 서적 글 상세보기
    @GetMapping("/{used_book_id}")
    public CustomResponse getPostById (@PathVariable ("used_book_id") Long used_book_id) {
        UsedBookPostDTO usedBookPost = usedBookService.getPostById(used_book_id);

        if (usedBookPost != null) {
            return CustomResponse.ok("조회 성공", usedBookPost);
        } else {
            return CustomResponse.failure("조회 실패");
        }
    }

    // 중고 서적 글 삭제
    @DeleteMapping("/{used_book_id}")
    public CustomResponse deletePostById (@PathVariable ("used_book_id") Long used_book_id) {
        int result = usedBookService.deletePostById(used_book_id);

        if (result > 0) {
            return CustomResponse.ok("삭제되었습니다." , result);
        } else {
            return CustomResponse.failure("삭제 실패하였습니다.");
        }
    }

    // 중고 서적 글 수정
    @PutMapping("/{used_book_id}/{d_isbn}")
    public CustomResponse updatePost (@PathVariable ("used_book_id") Long used_book_id, @PathVariable ("d_isbn") Long d_isbn, @ModelAttribute UsedBookInfoDTO usedBookInfoDTO) throws IOException {
        int result = usedBookService.updatePost(used_book_id, d_isbn, usedBookInfoDTO);

        if (result > 0) {
            return CustomResponse.ok("수정되었습니다.", result);
        } else {
            return CustomResponse.failure("수정에 실패하였습니다.");
        }
    }

    // 중고 서적 글 등록
    @PostMapping("/{d_isbn}")
    public CustomResponse createPost (@PathVariable ("d_isbn") Long d_isbn, @ModelAttribute UsedBookInfoDTO usedBookInfoDTO) throws IOException {
        String result = usedBookService.createPost(d_isbn, usedBookInfoDTO);

        if (result != null) {
            return CustomResponse.ok(result, null);
        } else {
            return CustomResponse.failure("등록에 실패하였습니다.");
        }
    }

}
