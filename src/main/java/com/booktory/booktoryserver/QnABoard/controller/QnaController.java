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

        int result = 0;

        // 익명 게시글이고 비밀번호가 입력된 경우
        if ("Anonymous".equals(qnaRequestDTO.getLockStatus()) && qnaRequestDTO.getQnaPassword() != null && !qnaRequestDTO.getQnaPassword().isEmpty()) {
            qnaRequestDTO.setIsLocked(true);
        } else if ("Public".equals(qnaRequestDTO.getLockStatus()) && (qnaRequestDTO.getQnaPassword() == null || qnaRequestDTO.getQnaPassword().isEmpty())) {
            // 공개 게시글이고 비밀번호가 입력되지 않은 경우
            qnaRequestDTO.setIsLocked(false);
        } else {
            // 그 외의 경우는 유효하지 않음
            return CustomResponse.failure("유효하지 않은 요청입니다. 공개 게시글에는 비밀번호가 없어야 하고, 익명 게시글에는 비밀번호가 필요합니다.");
        }

        result = qnaService.createQna(qnaRequestDTO);

        if(result > 0) {
            return CustomResponse.ok("문의 게시글 작성 완료", null);
        } else {
            return CustomResponse.failure("문의 게시글 작성 실패");
        }
    }


    @GetMapping("/list")
    public CustomResponse getQnaList() {
        List<QnaResponseDTO> qnaList = qnaService.findAllQna();

        return CustomResponse.ok("문의 게시글 전체 조회 성공", qnaList);
    }


}
