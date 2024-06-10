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

    private Long qna_id;

    private String qna_title;

    private String qna_password;

    private String qna_author;

    private String qna_content;

    private LocalDateTime created_at;

    private Boolean is_lock;

    public static QnaBoard covertToDTO(QnaRequestDTO qnaRequestDTO){
        return QnaBoard.builder()
               .qna_title(qnaRequestDTO.getQnaTitle())
               .qna_password(qnaRequestDTO.getQnaPassword())
               .qna_author(qnaRequestDTO.getQnaAuthor())
               .qna_content(qnaRequestDTO.getQnaContent())
               .created_at(LocalDateTime.now())
               .is_lock(false)
               .build();
    }
}
