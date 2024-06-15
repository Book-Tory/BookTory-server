package com.booktory.booktoryserver.BookStory.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NaverSearchBookResponseDTO {
    private String lastBuildDate;
    private int total;
    private int start;
    private int display;
    private List<BookDTO> items;
}
