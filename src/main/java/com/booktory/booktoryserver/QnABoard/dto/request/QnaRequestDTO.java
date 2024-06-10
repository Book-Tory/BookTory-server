package com.booktory.booktoryserver.QnABoard.dto.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class QnaRequestDTO {

    private Long qnaId;

    private String qnaTitle;

    private String qnaPassword;

    private String qnaAuthor;

    private String qnaContent;

    private Boolean isLocked;
}
