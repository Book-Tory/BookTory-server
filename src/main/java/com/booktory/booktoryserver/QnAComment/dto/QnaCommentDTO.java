package com.booktory.booktoryserver.QnAComment.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class QnaCommentDTO {

    private Long qnaCommentId;
    private String commentContent;
}
