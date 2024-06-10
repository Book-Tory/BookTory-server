package com.booktory.booktoryserver.QnABoard.dto.response;

import com.booktory.booktoryserver.QnABoard.domain.QnaBoard;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QnaResponseDTO {

    private Long qnaId;

    private String qnaTitle;

    private String qnaContent;

    private String qnaAuthor;

    private LocalDateTime createAt;

    private  Boolean isLock;


    public static QnaResponseDTO toQnaResponse(QnaBoard qnaBoard){
        return QnaResponseDTO.builder()
               .qnaId(qnaBoard.getQna_id())
               .qnaTitle(qnaBoard.getQna_title())
               .qnaContent(qnaBoard.getQna_content())
               .qnaAuthor(qnaBoard.getQna_author())
               .createAt(qnaBoard.getCreated_at())
               .isLock(qnaBoard.getIs_lock())
               .build();
    }


}
