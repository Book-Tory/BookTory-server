package com.booktory.booktoryserver.UsedBook.service;

import com.booktory.booktoryserver.UsedBook.dto.response.BookDTO;
import com.booktory.booktoryserver.UsedBook.dto.response.BookResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsedBookService {

    @Value("${X-Naver-Client-Id}")
    private String client_id;

    @Value("${X-Naver-Client-Secret}")
    private String client_secret;

    public List<BookDTO> searchBooks(String query, int display) throws JsonProcessingException {
        // UriComponentsBuilder -> URI를 동적으로 생성하고 조작하는 데 사용
        // 스키마, 호스트, 경로, 쿼리 파라미터 등 다양한 URI 구성 요소를 쉽게 설정 가능
        URI uri = UriComponentsBuilder
                .fromUriString("https://openapi.naver.com")
                .path("/v1/search/book.json")
                .queryParam("query", query)
                .queryParam("display", display)
                .encode() // URI 인코딩
                .build() // UriComponents 객체를 생성
                .toUri(); // UriComponents 객체를 java.net.URI 객체로 변환

        // 요청 헤더 설정
        RequestEntity<Void> req = RequestEntity
                .get(uri)
                .header("X-Naver-Client-Id", client_id)
                .header("X-Naver-Client-Secret", client_secret)
                .build();

        // REST 요청을 보내기 위해 RestTemplate 생성
        RestTemplate restTemplate = new RestTemplate();

        // API 호출 및 응답 받기
        ResponseEntity<String> res = restTemplate.exchange(req, String.class);

        ObjectMapper mapper = new ObjectMapper();
        BookResponseDTO bookResponseDTO = mapper.readValue(res.getBody(), BookResponseDTO.class);

        // DTO에 있는 items 가져오기 (우리가 원하는건 items니까)
        List<BookDTO> bookInfoList = bookResponseDTO.getItems();

        return bookInfoList;
    }
}
