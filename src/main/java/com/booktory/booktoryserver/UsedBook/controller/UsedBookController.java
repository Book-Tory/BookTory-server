package com.booktory.booktoryserver.UsedBook.controller;

import com.booktory.booktoryserver.UsedBook.domain.UsedBookPostEntity;
import com.booktory.booktoryserver.UsedBook.dto.response.BookDTO;
import com.booktory.booktoryserver.UsedBook.service.UsedBookService;
import com.booktory.booktoryserver.common.CustomResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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

        if (bookInfoList != null) {
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
        List<UsedBookPostEntity> list = usedBookService.getList();

        if (list != null) {
            return CustomResponse.ok("중고 서적 글 리스트 조회 성공", list);
        } else {
            return CustomResponse.failure("중고 서적 글 리스트 조회 실패");
        }
    }

    // 중고 서적 글 상세보기
    @GetMapping("/{used_book_id}")
    public CustomResponse getPostById (@PathVariable ("used_book_id") Long used_book_id) {
        UsedBookPostEntity usedBookPost = usedBookService.getPostById(used_book_id);

        if (usedBookPost != null) {
            return CustomResponse.ok("조회 성공", usedBookPost);
        } else {
            return CustomResponse.failure("조회 실패");
        }
    }

}
