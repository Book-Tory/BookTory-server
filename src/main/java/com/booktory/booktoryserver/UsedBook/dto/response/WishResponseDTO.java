package com.booktory.booktoryserver.UsedBook.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WishResponseDTO {
    private boolean isWished;
    private Long userId;
}
