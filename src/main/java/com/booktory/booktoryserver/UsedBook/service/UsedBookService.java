package com.booktory.booktoryserver.UsedBook.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.booktory.booktoryserver.UsedBook.domain.BookEntity;
import com.booktory.booktoryserver.UsedBook.domain.UsedBookImage;
import com.booktory.booktoryserver.UsedBook.domain.UsedBookPostEntity;
import com.booktory.booktoryserver.UsedBook.dto.request.UsedBookInfoDTO;
import com.booktory.booktoryserver.UsedBook.dto.response.BookDTO;
import com.booktory.booktoryserver.UsedBook.dto.response.BookResponseDTO;
import com.booktory.booktoryserver.UsedBook.dto.response.UsedBookPostDTO;
import com.booktory.booktoryserver.UsedBook.mapper.UsedBookMapper;
import com.booktory.booktoryserver.products_shop.domain.ProductImageFile;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsedBookService {
    private final UsedBookMapper usedBookMapper;

    private final AmazonS3 amazonS3;

    @Value("${spring.s3.bucket}")
    private String bucketName;

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

    // 검색어에 대한 전체 조회
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

    // 특정 isbn 값을 통한 책 상세조회
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

    // 데이터베이스에 있는 중고서적 목록
    public List<UsedBookPostDTO> getList() {
        // 중고서적 list 가져오기
        List<UsedBookPostEntity> usedBookPostList = usedBookMapper.getList();

        Map<Long, UsedBookPostEntity> usedBookMap = new HashMap<>();

        for (UsedBookPostEntity usedBookPost : usedBookPostList) {

            if (!usedBookMap.containsKey(usedBookPost.getUsed_book_id())) {
                usedBookPost.setImageUrls(new ArrayList<>());
                usedBookMap.put(usedBookPost.getUsed_book_id(), usedBookPost);
            }

            // 이미지가 있으면
            if (usedBookPost.getStored_image_name() != null) {
                String url = amazonS3.getUrl(bucketName, "profile/" + usedBookPost.getStored_image_name()).toString();

                Map<Long, String> imageMap = new HashMap<>();
                imageMap.put(usedBookPost.getUsed_book_image_id(), url);

                usedBookMap.get(usedBookPost.getUsed_book_id()).getImageUrls().add(imageMap);
            }
        }

        return usedBookMap.values().stream().map(usedBook -> {
            return UsedBookPostDTO.toDTO(usedBook, usedBook.getImageUrls());
        }).collect(Collectors.toList());
    }

    // 특정 중고서적 글 가져오기
    public UsedBookPostDTO getPostById(Long used_book_id) {
        List<UsedBookPostEntity> usedBookPost = usedBookMapper.getPostById(used_book_id);

        if (usedBookPost == null || usedBookPost.isEmpty()) {
            return null;
        }

        UsedBookPostEntity firstUsedBook = usedBookPost.get(0);

        Map<Long, String> imageMap = new HashMap<>();

        List<Map<Long, String>> imageUrls = new ArrayList<>();

        for (UsedBookPostEntity usedBook : usedBookPost) {
            if (usedBook.getStored_image_name() != null) {
                String url = amazonS3.getUrl(bucketName, "profile/" + usedBook.getStored_image_name()).toString();
                imageMap.put(usedBook.getUsed_book_image_id(), url);
            }
        }

        imageUrls.add(imageMap);

        return UsedBookPostDTO.toDTO(firstUsedBook, imageUrls);
    }

    // 중고서적 글 삭제
    public int deletePostById(Long used_book_id) {
        List<UsedBookImage> usedBookImages = usedBookMapper.getUsedBookImageById(used_book_id);

        for (UsedBookImage usedBookImage : usedBookImages) {
            String key = "profile/" + usedBookImage.getStored_image_name();
            amazonS3.deleteObject(new DeleteObjectRequest(bucketName, key));
        }

        return usedBookMapper.deletePostById(used_book_id);
    }

    // 중고서적 글 업데이트
    @Transactional
    public int updatePost(Long used_book_id, Long d_isbn, UsedBookInfoDTO usedBookInfoDTO) throws IOException {
        // 수정하려는 책이 DB에 있는지 먼저 확인
        Long bookId = usedBookMapper.getBookId(d_isbn);

        // 책 정보가 존재하지 않는다면
        if (bookId == null) {
            // 현재 책 정보 얻어오고
            BookDTO book = getBookByIsbn(d_isbn);

            BookEntity bookInfo = BookEntity.toEntity(book);

            // 책 정보 삽입
            usedBookMapper.createBookInfo(bookInfo);

            bookId = bookInfo.getBook_id();
        }

        List<String> urls = new ArrayList<>();

        // 수정할 이미지
        List<MultipartFile> updateImages = usedBookInfoDTO.getUpdate_images();

        // 그리고 추가할 이미지가 있으면 추가
        if (updateImages != null && !updateImages.isEmpty()) {
            usedBookInfoDTO.setImage_check(1);

            log.info("여기로 오니 ? {} update");

            // 그리고 추가
            for (MultipartFile updateImage : updateImages) {
                String originalFilename = updateImage.getOriginalFilename();
                String storedFilename = System.currentTimeMillis() + originalFilename;

                UsedBookImage usedBookImage = UsedBookImage.builder()
                        .used_book_id(used_book_id)
                        .original_image_name(originalFilename)
                        .stored_image_name(storedFilename)
                        .build();

                // 파일 메타데이터 설정
                ObjectMetadata objectMetadata = new ObjectMetadata();
                objectMetadata.setContentLength(updateImage.getSize());
                objectMetadata.setContentType(updateImage.getContentType());

                // 저장될 위치 + 파일명
                String key = "profile" + "/" + storedFilename;

                // 클라우드에 파일 저장
                amazonS3.putObject(bucketName, key, updateImage.getInputStream(), objectMetadata);
                amazonS3.setObjectAcl(bucketName, key, CannedAccessControlList.PublicRead);

                // 데이터베이스에 저장
                usedBookMapper.saveFile(usedBookImage);

                String url = amazonS3.getUrl(bucketName, key).toString();
                urls.add(url);
            }
        }

        List<UsedBookImage> imageList = usedBookMapper.getUsedBookImageById(used_book_id);
        if (imageList == null || imageList.isEmpty()) {
            usedBookInfoDTO.setImage_check(0);
        }

        // 그리고 내가 수정한 내용 적용
        return usedBookMapper.updatePost(UsedBookPostEntity.toUpdateEntity(used_book_id, usedBookInfoDTO, bookId));
    }

    @Transactional
    public String createPost(Long d_isbn, UsedBookInfoDTO usedBookInfoDTO) throws IOException {
        // 수정하려는 책이 DB에 있는지 먼저 확인
        Long bookId = usedBookMapper.getBookId(d_isbn);

        // 책 정보가 존재하지 않는다면
        if (bookId == null) {
            // isbn 값을 통해 현재 선택한 책 정보 얻어오고
            BookDTO book = getBookByIsbn(d_isbn);

            BookEntity bookInfo = BookEntity.toEntity(book);

            // 책 정보 삽입
            usedBookMapper.createBookInfo(bookInfo);

            bookId = bookInfo.getBook_id();
        }

        // 이미지 등록 처리
        List<String> urls = new ArrayList<>();

        // 등록할 이미지
        List<MultipartFile> newImages = usedBookInfoDTO.getUsed_book_image();

        // 중고서적 글에 등록한 이미지가 없으면
        if (newImages == null || newImages.isEmpty()) {
            usedBookInfoDTO.setImage_check(0);

            // 바로 글 등록
            UsedBookPostEntity usedBookPost = UsedBookPostEntity.toCreateEntity(usedBookInfoDTO, bookId);
            usedBookMapper.createPost(usedBookPost);
        } else {
            usedBookInfoDTO.setImage_check(1);
            UsedBookPostEntity usedBookPost = UsedBookPostEntity.toCreateEntity(usedBookInfoDTO, bookId);

            usedBookMapper.createPost(usedBookPost);

            // 이미지가 있으면 일단 글 등록하고 그 id값을 가져옴
            Long usedBookId = usedBookPost.getUsed_book_id();

            // 이미지가 여러 장일 때
            for (MultipartFile usedBookFile : newImages) {
                String originalFilename = usedBookFile.getOriginalFilename();
                String storedFilename = System.currentTimeMillis() + originalFilename; // 중복에 대비한 고유한 파일명 생성

                UsedBookImage usedBookImage = UsedBookImage.builder()
                        .used_book_id(usedBookId)
                        .original_image_name(originalFilename)
                        .stored_image_name(storedFilename)
                        .build();

                // 파일 메타데이터 설정
                ObjectMetadata objectMetadata = new ObjectMetadata();
                objectMetadata.setContentLength(usedBookFile.getSize());
                objectMetadata.setContentType(usedBookFile.getContentType());

                // 저장될 위치 + 파일명
                String key = "profile" + "/" + storedFilename;

                // 클라우드에 파일 저장
                amazonS3.putObject(bucketName, key, usedBookFile.getInputStream(), objectMetadata);
                amazonS3.setObjectAcl(bucketName, key, CannedAccessControlList.PublicRead);

                // 데이터베이스에 저장
                usedBookMapper.saveFile(usedBookImage);

                String url = amazonS3.getUrl(bucketName, key).toString();
                urls.add(url);
            }
        }

        return "중고서적이 등록되었습니다.";
    }

    // 선택한 중고 서적 책 사진 삭제
    public int deleteImageByImageId(Long used_book_image_id) {
        UsedBookImage usedBookImage = usedBookMapper.getUsedBookImageByImageId(used_book_image_id);

        // S3에서 삭제
        String key = "profile/" + usedBookImage.getStored_image_name();
        amazonS3.deleteObject(new DeleteObjectRequest(bucketName, key));

        return usedBookMapper.deleteImageByImageId(used_book_image_id);
    }
}
