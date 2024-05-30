package com.booktory.booktoryserver.UsedBook.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsedBookInfoDTO {
    private Long used_book_id;
    private String title;
    private int discount;
    private String description;
    private Date created_at;
    private Long book_condition_id;
    private Long book_id;
}
