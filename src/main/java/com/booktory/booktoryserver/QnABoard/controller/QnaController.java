package com.booktory.booktoryserver.QnABoard.controller;

import com.booktory.booktoryserver.QnABoard.dto.request.QnaDetailRequestDTO;
import com.booktory.booktoryserver.QnABoard.dto.request.QnaRequestDTO;
import com.booktory.booktoryserver.QnABoard.dto.response.QnaResponseDTO;
import com.booktory.booktoryserver.QnABoard.service.QnaService;
import com.booktory.booktoryserver.common.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/qna")
@RequiredArgsConstructor
@Tag(name = "QnA", description = "QnA 게시판 관련 API")
public class QnaController {

    private final QnaService qnaService;

    @PostMapping("")
    @Operation(summary = "문의 게시글 작성", description = "새로운 문의 게시글을 작성합니다.")
    @ApiResponse(responseCode = "200", description = "문의 게시글 작성 완료", content = @Content(schema = @Schema(implementation = String.class)))
    public CustomResponse createQna(@RequestBody QnaRequestDTO qnaRequestDTO) {
        int result = 0;

        if ("Anonymous".equals(qnaRequestDTO.getLockStatus()) && qnaRequestDTO.getQnaPassword() != null && !qnaRequestDTO.getQnaPassword().isEmpty()) {
            qnaRequestDTO.setIsLocked(true);
        } else if ("Public".equals(qnaRequestDTO.getLockStatus()) && (qnaRequestDTO.getQnaPassword() == null || qnaRequestDTO.getQnaPassword().isEmpty())) {
            qnaRequestDTO.setIsLocked(false);
        } else {
            return CustomResponse.failure("유효하지 않은 요청입니다. 공개 게시글에는 비밀번호가 없어야 하고, 익명 게시글에는 비밀번호가 필요합니다.");
        }

        result = qnaService.createQna(qnaRequestDTO);

        if (result > 0) {
            return CustomResponse.ok("문의 게시글 작성 완료", null);
        } else {
            return CustomResponse.failure("문의 게시글 작성 실패");
        }
    }

    @GetMapping("/list")
    @Operation(summary = "문의 게시글 목록 조회", description = "모든 문의 게시글을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "문의 게시글 전체 조회 성공", content = @Content(schema = @Schema(implementation = QnaResponseDTO.class)))
    public CustomResponse getQnaList() {
        List<QnaResponseDTO> qnaList = qnaService.findAllQna();
        return CustomResponse.ok("문의 게시글 전체 조회 성공", qnaList);
    }

    @PutMapping("/{qnaId}")
    @Operation(summary = "문의 게시글 수정", description = "특정 ID의 문의 게시글을 수정합니다.")
    @ApiResponse(responseCode = "200", description = "문의 게시글 수정 완료", content = @Content(schema = @Schema(implementation = String.class)))
    public CustomResponse updateQna(
            @PathVariable("qnaId") @Parameter(description = "수정할 문의 게시글의 ID") Long qnaId,
            @RequestBody QnaRequestDTO qnaRequestDTO) {
        qnaRequestDTO.setQnaId(qnaId);

        int result = 0;

        if ("Anonymous".equals(qnaRequestDTO.getLockStatus()) && qnaRequestDTO.getQnaPassword() != null && !qnaRequestDTO.getQnaPassword().isEmpty()) {
            qnaRequestDTO.setIsLocked(true);
        } else if ("Public".equals(qnaRequestDTO.getLockStatus()) && (qnaRequestDTO.getQnaPassword() == null || qnaRequestDTO.getQnaPassword().isEmpty())) {
            qnaRequestDTO.setIsLocked(false);
        } else {
            return CustomResponse.failure("유효하지 않은 요청입니다. 공개 게시글에는 비밀번호가 없어야 하고, 익명 게시글에는 비밀번호가 필요합니다.");
        }

        result = qnaService.updateQna(qnaRequestDTO);

        if (result > 0) {
            return CustomResponse.ok("문의 게시글 수정 완료", null);
        } else {
            return CustomResponse.failure("문의 게시글 수정 실패");
        }
    }

    @PostMapping("/detail/{qnaId}")
    @Operation(summary = "문의 게시글 상세 조회", description = "특정 ID의 문의 게시글을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "문의 게시글 조회 성공", content = @Content(schema = @Schema(implementation = QnaResponseDTO.class)))
    public CustomResponse searchQna(
            @RequestBody QnaDetailRequestDTO qnaRequestDTO,
            @PathVariable @Parameter(description = "조회할 문의 게시글의 ID") Long qnaId) {
        qnaRequestDTO.setQnaId(qnaId);

        QnaResponseDTO qnaResponseDTO = qnaService.findByQnaId(qnaRequestDTO.getQnaId(), qnaRequestDTO.getQnaPassword());
        return CustomResponse.ok("문의 게시글 조회 성공", qnaResponseDTO);
    }

    @GetMapping("/detail/{qnaId}")
    @Operation(summary = "문의 게시글 상세 조회 (비밀번호 필요 없음)", description = "특정 ID의 문의 게시글을 비밀번호 없이 조회합니다.")
    @ApiResponse(responseCode = "200", description = "게시글 상세 조회 성공", content = @Content(schema = @Schema(implementation = QnaResponseDTO.class)))
    public CustomResponse getQna(
            @PathVariable @Parameter(description = "조회할 문의 게시글의 ID") Long qnaId) {
        QnaResponseDTO qnaResponseDTO = qnaService.findByContent(qnaId);
        return CustomResponse.ok("게시글 상세 조회 성공", qnaResponseDTO);
    }

    @DeleteMapping("/{qnaId}")
    @Operation(summary = "문의 게시글 삭제", description = "특정 ID의 문의 게시글을 삭제합니다.")
    @ApiResponse(responseCode = "200", description = "문의 게시글 삭제 완료", content = @Content(schema = @Schema(implementation = String.class)))
    public CustomResponse deleteQnaBoard(
            @PathVariable @Parameter(description = "삭제할 문의 게시글의 ID") Long qnaId) {
        int result = qnaService.deleteQnaBoard(qnaId);

        if (result > 0) {
            return CustomResponse.ok("문의 게시글 삭제 완료", null);
        } else {
            return CustomResponse.failure("문의 게시글 삭제 실패");
        }
    }
}
