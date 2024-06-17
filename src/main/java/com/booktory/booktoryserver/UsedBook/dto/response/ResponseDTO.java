package com.booktory.booktoryserver.UsedBook.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResponseDTO {
    private UsedBookPostDTO usedBookPost;
    private Long userId;
}
