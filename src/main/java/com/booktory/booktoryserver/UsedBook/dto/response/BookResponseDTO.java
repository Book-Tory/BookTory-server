package com.booktory.booktoryserver.UsedBook.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookResponseDTO {
    private String lastBuildDate;
    private int total;
    private int start;
    private int display;
    private List<BookDTO> items;
}
