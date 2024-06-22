package com.booktory.booktoryserver.UsedBook.domain;

import com.booktory.booktoryserver.UsedBook.dto.request.UsedBookStatusDTO;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsedBookStatusEntity {
    private Long used_book_id; // 중고서적 id
    private Long status; // 중고서적 상태

    public static UsedBookStatusEntity toStatusEntity(Long used_book_id, UsedBookStatusDTO usedBookStatusDTO) {
        return UsedBookStatusEntity.builder()
                .used_book_id(usedBookStatusDTO.getUsed_book_id())
                .status(usedBookStatusDTO.getStatus())
                .build();
    }
}
