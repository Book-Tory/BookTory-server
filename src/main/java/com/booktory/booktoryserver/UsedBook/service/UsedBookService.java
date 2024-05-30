package com.booktory.booktoryserver.UsedBook.service;

import com.booktory.booktoryserver.UsedBook.domain.UsedBookPostEntity;
import com.booktory.booktoryserver.UsedBook.dto.response.BookDTO;
import com.booktory.booktoryserver.UsedBook.dto.response.BookResponseDTO;
import com.booktory.booktoryserver.UsedBook.mapper.UsedBookMapper;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsedBookService {
    private final UsedBookMapper usedBookMapper;

    @Value("${X-Naver-Client-Id}")
    private String client_id;

    @Value("${X-Naver-Client-Secret}")
    private String client_secret;

    private URI buildUri (String path, Map<String, Object> queryParams) {
        // 1. 기본 URI 설정

        // UriComponentsBuilder -> URI를 동적으로 생성하고 조작하는 데 사용
        // 스키마, 호스트, 경로, 쿼리 파라미터 등 다양한 URI 구성 요소를 쉽게 설정 가능
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString("https://openapi.naver.com")
                .path(path);

        // 2. queryParams 맵의 각 엔트리를 반복 -> queryParam 추가
        queryParams.forEach(builder::queryParam);

        return builder
                .encode() // URI 인코딩
                .build() // UriComponents 객체를 생성
                .toUri(); // UriComponents 객체를 java.net.URI 객체로 변환
    }

    private ResponseEntity<String> sendRequest(URI uri) {
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

        return res;
    }

    public List<BookDTO> searchBooks(String query, int display) throws JsonProcessingException {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("query", query);
        queryParams.put("display", display);

        URI uri = buildUri("/v1/search/book.json", queryParams);

        ResponseEntity<String> res = sendRequest(uri);

        ObjectMapper mapper = new ObjectMapper();
        BookResponseDTO bookResponseDTO = mapper.readValue(res.getBody(), BookResponseDTO.class);

        // DTO에 있는 items 가져오기 (우리가 원하는건 items니까)
        List<BookDTO> bookInfoList = bookResponseDTO.getItems();

        return bookInfoList;
    }



    public BookDTO getBookByIsbn(Long d_isbn) throws JsonProcessingException {
        Map<String, Object> queryParams = new HashMap<>();

        queryParams.put("d_isbn", d_isbn);

        URI uri = buildUri("/v1/search/book_adv.json", queryParams);

        ResponseEntity<String> res = sendRequest(uri);

        ObjectMapper mapper = new ObjectMapper();
        BookResponseDTO bookResponseDTO = mapper.readValue(res.getBody(), BookResponseDTO.class);

        BookDTO bookInfo = bookResponseDTO.getItems().get(0);

        return bookInfo;
    }


    public List<UsedBookPostEntity> getList() {
        return usedBookMapper.getList();
    }

    public UsedBookPostEntity getPostById(Long used_book_id) {
        return usedBookMapper.getPostById(used_book_id);
    }

    public int deletePostById(Long used_book_id) {
        return usedBookMapper.deletePostById(used_book_id);
    }
}
