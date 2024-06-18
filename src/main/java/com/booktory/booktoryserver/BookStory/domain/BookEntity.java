package com.booktory.booktoryserver.BookStory.domain;


import lombok.*;
import org.joda.time.DateTime;

import java.util.Date;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class BookEntity {
    private Long book_id;
    private String book_image;
    private String book_name;
    private String book_author;
    private int book_price;
    private String book_publisher;
    private Date book_publication_date;
    private Long book_isbn;
}
