package com.booktory.booktoryserver.QnAComment.controller;

import com.booktory.booktoryserver.QnAComment.dto.QnaCommentDTO;
import com.booktory.booktoryserver.QnAComment.service.QnaCommentService;
import com.booktory.booktoryserver.common.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/qna/comment")
@RequiredArgsConstructor
@Tag(name = "QnaComment API", description = "Q&A 댓글 관련 API")
public class QnaCommentController {

    private final QnaCommentService qnaCommentService;

    @Operation(summary = "댓글 작성", description = "Q&A 게시글에 댓글을 작성합니다.")
    @PostMapping("/{qnaId}")
    public CustomResponse createComment(
            @RequestBody @Parameter(description = "댓글 정보") QnaCommentDTO qnaCommentDTO,
            @AuthenticationPrincipal UserDetails userDetails,
            @Parameter(description = "Q&A 게시글 ID") @PathVariable("qnaId") Long qnaId){

        String email = userDetails.getUsername();
        int result = qnaCommentService.createComment(qnaCommentDTO, email, qnaId);
        if(result > 0) {
            return CustomResponse.ok("댓글 작성 완료", null);
        } else {
            return CustomResponse.failure("댓글 작성 실패");
        }
    }

    @Operation(summary = "댓글 삭제", description = "특정 댓글을 삭제합니다.")
    @DeleteMapping("/{qnaCommentId}")
    public CustomResponse deleteComment(@Parameter(description = "댓글 ID") @PathVariable("qnaCommentId") Long qnaCommentId){
        int result = qnaCommentService.deleteQnaComment(qnaCommentId);
        if(result > 0) {
            return CustomResponse.ok("댓글 삭제 완료", null);
        } else {
            return CustomResponse.failure("댓글 삭제 실패");
        }
    }
}
