package com.booktory.booktoryserver.QnABoard.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class QnaDetailRequestDTO {

    private Long qnaId;
    private String qnaPassword;

}
