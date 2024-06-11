package com.booktory.booktoryserver.QnAComment.controller;

import com.booktory.booktoryserver.QnAComment.dto.QnaCommentDTO;
import com.booktory.booktoryserver.QnAComment.service.QnaCommentService;
import com.booktory.booktoryserver.common.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/qna/comment")
@RequiredArgsConstructor
public class QnaCommentController {

    private final QnaCommentService qnaCommentService;

    @PostMapping("/{qnaId}")
    public CustomResponse createComment(
            @RequestBody QnaCommentDTO qnaCommentDTO,
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable("qnaId") Long qnaId){

        String email = userDetails.getUsername();

        int result = qnaCommentService.createComment(qnaCommentDTO, email, qnaId);

        if(result > 0) {
            return CustomResponse.ok("댓글 작성 완료", null);
        } else {
            return CustomResponse.failure("댓글 작성 실패");
        }

    }


    @DeleteMapping("/{qnaCommentId}")
    public CustomResponse deleteComment(@PathVariable("qnaCommentId") Long qnaCommentId){
        int result = qnaCommentService.deleteQnaComment(qnaCommentId);

        if(result > 0) {
            return CustomResponse.ok("댓글 삭제 완료", null);
        } else {
            return CustomResponse.failure("댓글 삭제 실패");
        }
    }



}
