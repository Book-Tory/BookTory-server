package com.booktory.booktoryserver.UsedBook.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class BookDTO {
    private String title;
    private String link;
    private String image;
    private String author;
    private int discount;
    private String publisher;
    private Date pubdate;
    private Long isbn;
    private String description;
}
