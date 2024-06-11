package com.booktory.booktoryserver.QnAComment.model;

import com.booktory.booktoryserver.QnAComment.dto.QnaCommentDTO;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class QnaComment {

    private Long qna_comment_id;
    private String comment_author;
    private String comment_content;
    private LocalDateTime created_at;
    private Long qna_id;


    public static QnaComment toQnaComment(QnaCommentDTO qnaCommentDTO, String username, Long qnaId){
        return QnaComment.builder()
                .comment_author(username)
                .comment_content(qnaCommentDTO.getCommentContent())
                .created_at(LocalDateTime.now())
                .qna_id(qnaId)
                .build();
    }
}
