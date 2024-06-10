package com.booktory.booktoryserver.QnABoard.controller;


import com.booktory.booktoryserver.QnABoard.dto.request.QnaRequestDTO;
import com.booktory.booktoryserver.QnABoard.service.QnaService;
import com.booktory.booktoryserver.common.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/qna")
@RequiredArgsConstructor
public class QnaController {

    private final QnaService qnaService;

    @PostMapping("/")
    public CustomResponse createQna(@RequestBody QnaRequestDTO qnaRequestDTO) {
        int result = qnaService.createQna(qnaRequestDTO);

        if(result > 0) {
            return CustomResponse.ok("문의 게시글 작성 완료", null);
        } else {
            return CustomResponse.failure("문의 게시글 실패");
        }
    }
}
