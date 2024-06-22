package com.booktory.booktoryserver.UsedBook.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsedBookStatusDTO {
    private Long used_book_id; // 중고서적 id
    private Long status; // 중고서적 상태
}
