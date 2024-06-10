package com.booktory.booktoryserver.QnABoard.controller;


import com.booktory.booktoryserver.QnABoard.dto.request.QnaRequestDTO;
import com.booktory.booktoryserver.QnABoard.dto.response.QnaResponseDTO;
import com.booktory.booktoryserver.QnABoard.service.QnaService;
import com.booktory.booktoryserver.common.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/qna")
@RequiredArgsConstructor
public class QnaController {

    private final QnaService qnaService;

    @PostMapping("")
    public CustomResponse createQna(@RequestBody QnaRequestDTO qnaRequestDTO) {
        int result = qnaService.createQna(qnaRequestDTO);

        if(result > 0) {
            return CustomResponse.ok("문의 게시글 작성 완료", null);
        } else {
            return CustomResponse.failure("문의 게시글 실패");
        }
    }


    @GetMapping("/list")
    public CustomResponse getQnaList() {
        List<QnaResponseDTO> qnaList = qnaService.findAllQna();

        return CustomResponse.ok("문의 게시글 전체 조회 성공", qnaList);
    }
}
