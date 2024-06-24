package com.booktory.booktoryserver.UsedBook.domain;


import com.booktory.booktoryserver.UsedBook.dto.response.BookDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
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
//    private Date book_publication_date;
    private Long book_isbn;

    public static BookEntity toEntity(BookDTO book) {
        return BookEntity.builder()
                .book_image(book.getImage())
                .book_name(book.getTitle())
                .book_author(book.getAuthor())
                .book_price(book.getDiscount())
                .book_publisher(book.getPublisher())
//                .book_publication_date(book.getPubdate())
                .book_isbn(book.getIsbn())
                .build();
    }
}
