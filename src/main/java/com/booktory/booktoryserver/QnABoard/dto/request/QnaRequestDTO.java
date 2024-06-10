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

    private String lockStatus; // Public, Anonymous

    private Boolean isLocked;  // Anonymous == true, Public == false
}
