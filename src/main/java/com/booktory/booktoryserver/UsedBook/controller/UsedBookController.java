package com.booktory.booktoryserver.UsedBook.controller;

import com.booktory.booktoryserver.UsedBook.dto.response.BookDTO;
import com.booktory.booktoryserver.UsedBook.service.UsedBookService;
import com.booktory.booktoryserver.common.CustomResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/used-books")
@RequiredArgsConstructor
@Slf4j

public class UsedBookController {
    private final UsedBookService usedBookService;

    @GetMapping("/search-books")
    public CustomResponse searchBooks(@RequestParam ("query") String query, @RequestParam ("display") int display) throws JsonProcessingException {
        List<BookDTO> bookInfoList = usedBookService.searchBooks(query, display);

        if (bookInfoList == null) {
            return CustomResponse.failure("조회 실패");
        } else {
            return CustomResponse.ok("조회 완료", bookInfoList);
        }

    }

}
