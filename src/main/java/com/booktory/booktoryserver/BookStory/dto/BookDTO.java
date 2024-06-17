package com.booktory.booktoryserver.BookStory.dto;


import com.booktory.booktoryserver.BookStory.domain.BookEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
//@ToString

public class BookDTO {
    //응답되고 요청되는 데이터의 형태
    //독후감의 대상이 되는 책 정보
    private String title; //책 제목
    private String link; //네이버 도서 정보 URL
    private String image; //섬네일 이미지의 URL
    private String author; //저자이름
    private int discount; //판매가격, 절판 등의 이유로 가격이 없으면 값을 반환하지 않음
    private String publisher; //출판사
    private Long isbn; //ISBN
    private String description; //네이버 도서의 책 소개
    private Date pubdate; //출간일


    //BookDTO를 BookEntity로 만드는 메서드
    public static BookEntity toEntity(BookDTO bookDTO){
        return BookEntity.builder()
                //.book_id(bookDTO.getid())
                //BookDTO에는 책id를 담는 변수 필드가 없다.
                .book_image(bookDTO.getImage())
                .book_name(bookDTO.getTitle())
                .book_author(bookDTO.getAuthor())
                .book_price(bookDTO.getDiscount())
                .book_publisher(bookDTO.getPublisher())
                .book_publication_date(bookDTO.getPubdate())
                //왜 .toDate()를 해야하는지 알아야 한다.
                //이것도 book_publication_date 타입이 timestamp라서 그러나?
                .book_isbn((long) bookDTO.getIsbn())
                //왜 갑자기 isbn의 타입을 강제 형변환 하는지 알아야한다.
                //DB에 isbn의 데이터 타입이 bigint라서 그런것 같다. 확인 필요!
                .build();


    }



}
