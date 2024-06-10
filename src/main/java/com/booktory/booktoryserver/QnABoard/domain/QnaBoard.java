package com.booktory.booktoryserver.QnABoard.domain;

import com.booktory.booktoryserver.QnABoard.dto.request.QnaRequestDTO;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class QnaBoard {

    private Long qnaId;

    private String qnaTitle;

    private String qnaPassword;

    private String qnaAuthor;

    private String qnaContent;

    private LocalDateTime createdAt;

    private Boolean isLock;

    public static QnaBoard covertToDTO(QnaRequestDTO qnaRequestDTO){
        return QnaBoard.builder()
               .qnaTitle(qnaRequestDTO.getQnaTitle())
               .qnaPassword(qnaRequestDTO.getQnaPassword())
               .qnaAuthor(qnaRequestDTO.getQnaAuthor())
               .qnaContent(qnaRequestDTO.getQnaContent())
               .createdAt(LocalDateTime.now())
               .isLock(false)
               .build();
    }
}
