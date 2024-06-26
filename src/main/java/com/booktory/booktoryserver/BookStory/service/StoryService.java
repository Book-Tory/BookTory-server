package com.booktory.booktoryserver.BookStory.service;

import com.amazonaws.Response;
import com.booktory.booktoryserver.BookStory.domain.BookEntity;
import com.booktory.booktoryserver.BookStory.domain.StoryEntity;
import com.booktory.booktoryserver.BookStory.dto.BookDTO;
import com.booktory.booktoryserver.BookStory.dto.NaverSearchBookResponseDTO;
import com.booktory.booktoryserver.BookStory.dto.StoryDTO;
import com.booktory.booktoryserver.BookStory.mapper.StoryLikeMapper;
import com.booktory.booktoryserver.BookStory.mapper.StoryMapper;
import com.booktory.booktoryserver.Users.mapper.UserMapper;
import com.booktory.booktoryserver.Users.model.UserEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.*;


@Service
@RequiredArgsConstructor
@Slf4j
public class StoryService {
    private final StoryMapper storyMapper;

    private final UserMapper userMapper;
    //책 검색 API사용


    private final StoryLikeMapper storyLikeMapper;


    @Value("XKLrNAbeorAmb3vVPPPP")
    private String clientId;

    @Value("Kmn2tNObfC")
    private String clientSecret;

    private URI buildUri(String path, Map<String, Object> queryParams) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString("https://openapi.naver.com")
                .path(path);
        queryParams.forEach(builder::queryParam);

        return builder
                //URI 인코딩
                .encode()
                //UriComponsents 객체를 생성
                .build()
                //UriComponents 객체를 java.net.URI 객체로 변환
                .toUri();
    }

    
    //요청 헤더 설정
    //헤더에는 Client-id와 Client-Secret이 꼭 들어가야 합니다.
    private ResponseEntity<String> sendRequest(URI uri){
        RequestEntity<Void> req = RequestEntity
                .get(uri)
                .header("X-Naver-Client-Id", clientId)
                .header("X-Naver-Client-Secret", clientSecret)
                .build();
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> res = restTemplate.exchange(req, String.class);

        return res;
    }
    
    
    //입력한 값에 따른 책 전체 조회
    public List<BookDTO> searchBooks(String query, int display, int startposition, String searchresult) throws JsonProcessingException {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("query", query);
        queryParams.put("display", display);
        queryParams.put("start", startposition);
        queryParams.put("sort", searchresult);

        //네이버 도서검색 API에서 책 검색을 수행하는 엔드 포인트
        URI uri = buildUri("/v1/search/book.json", queryParams);

        //API 요청 전송
        ResponseEntity<String> res = sendRequest(uri);
        //Spring프레임워크에서 제공하는 클래스
        //uri를 담아 sendRequest함수로 응답받은 결과값을 ResponseEntity형태로 변수에 저장

        ObjectMapper mapper = new ObjectMapper();
        //JSON객체를 Java객체로 변환하는 클래스이다.
        NaverSearchBookResponseDTO naverSearchBookResponseDTO = mapper.readValue(res.getBody(), NaverSearchBookResponseDTO.class );
        //왜 JsonProcessingException을 해야 readvalue함수가 동작하는지 자세히 모르겠다.
        List<BookDTO> bookInfoList = naverSearchBookResponseDTO.getItems();

        return bookInfoList;

    }

    //isbn 값에 따른 책 상세조회
    public BookDTO getBookByIsbn(Long d_isbn) throws JsonProcessingException {
        Map<String, Object> queryParams = new HashMap<>();

        queryParams.put("d_isbn", d_isbn);

        URI uri = buildUri("/v1/search/book_adv.json", queryParams);

        ResponseEntity<String> res = sendRequest(uri);

        ObjectMapper mapper = new ObjectMapper();
        NaverSearchBookResponseDTO naverSearchBookResponseDTO = mapper.readValue(res.getBody(), NaverSearchBookResponseDTO.class);
        BookDTO bookInfo =  naverSearchBookResponseDTO.getItems().get(0);

        return bookInfo;
    }


    //책 제목에 따른 책 상세조회
    public BookDTO getBookByName(String bookName) throws JsonProcessingException{
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("query", bookName);
        queryParams.put("display", 1); //한권의 책만 응답 받기 위해 설정
        //query와 display값만 받아도 상관없다.

        URI uri = buildUri("/v1/search/book.json", queryParams);
        //API응답 요청
        ResponseEntity<String> res = sendRequest(uri);

        //mapper로 감싼다.
        ObjectMapper mapper = new ObjectMapper();
        //NavaerSearchBookResponseDTO로 변환
        NaverSearchBookResponseDTO naverSearchBookResponseDTO = mapper.readValue(res.getBody(), NaverSearchBookResponseDTO.class);
        BookDTO bookInfo =  naverSearchBookResponseDTO.getItems().get(0);

        //return naverSearchBookResponseDTO.getItems().isEmpty() ? null : naverSearchBookResponseDTO.getItems().get(0);
        //items에 값이 없으면 null을 반환하고 있으면 반환해라!
        return bookInfo;
    }




    //------------------------------------------------------
    public List<StoryDTO> getAllStory() {
        //모든 StoryEntity 목록을 가져옴
        List<StoryEntity> storyEntities = storyMapper.getAllStory();

        //StoryDTO 목록을 초기화
        List<StoryDTO> storyDTOS = new ArrayList<>();

        //각 StoryEntity에 대해 반복
        for (StoryEntity story : storyEntities){
            //storyEntity를 StoryDTO로 변환
            StoryDTO storyDTO = StoryEntity.toDTO(story);

            // 작성자 정보를 가져옴
            Optional<UserEntity> userEntity = userMapper.findById(story.getUser_id());

            // 작성자 정보가 존재할 경우 StoryDTO에 설정
            if (userEntity.isPresent()) {
                storyDTO.setUserNickname(userEntity.get().getUser_nickname());
                storyDTO.setUserImg(userEntity.get().getUser_img());
            }

            // 변환된 StoryDTO를 목록에 추가
            storyDTOS.add(storyDTO);
        }

        // 최종적으로 변환된 StoryDTO 목록을 반환
        return storyDTOS;
    }

    //독후감 등록하기
    @Transactional
    public String createStory(Long d_isbn, StoryDTO storyDTO, String useremail) throws IOException{
        //책이 DB에 있는지 확인
        Long bookId = storyMapper.getBookId(d_isbn);

        //사용자 ID 가져오기
        Long userId = storyMapper.findIdByEmail(useremail);
        storyDTO.setUserId(userId);

        //책 정보가 없다면 DB에 책 정보 저장
        if(bookId == null){
            //ISBN을 통해 책 정보 가져오기
            BookDTO book = getBookByIsbn(d_isbn);
            BookEntity bookInfo = BookDTO.toEntity(book);

            storyMapper.createBookInfo(bookInfo);
            bookId = bookInfo.getBook_id();
        }

        StoryEntity storyEntity = StoryDTO.toEntity(storyDTO);
        storyEntity.setBook_id(bookId);
        storyMapper.createStory(storyEntity);

        return "독후감이 성공적으로 등록되었습니다.";
    }

    //독후감 삭제하기
    public void deleteStory(Long story_board_id){
        storyMapper.deleteStory(story_board_id);
    }


    //독후감 수정하기
    public void updateStory(Long story_board_id, StoryDTO storyDTO) {
        log.info("storyboardid : " + story_board_id);
        log.info("storyDTO : " + storyDTO);


        //기존 데이터를 DB에서 가져옵니다.
        List<StoryEntity> storyEntities = storyMapper.getStoryById(story_board_id);

        StoryEntity fetchStory = storyEntities.get(0);
        //DTO의 updateEntity메서드를 사용하여 기존 엔티티를 업데이트
        storyDTO.updateEntity(fetchStory);
        
        //업데이트된 엔티티를 DB에 저장
        storyMapper.updateStory(fetchStory);
    }


    public StoryDTO getStoryById(Long story_board_id) {
        List<StoryEntity> myStory = storyMapper.getStoryById(story_board_id);

        if(myStory == null || myStory.isEmpty()){
            return null;
        }

        StoryEntity storyEntity = myStory.get(0);

        return StoryEntity.toDTO(storyEntity);
    }





    //isbn 값을 통한 책 검색 후, 책 정보 DB에 저장
    @Transactional
//    일관된 상태를 유지하여 책 정보가 모두 저장되어야 하며, 저장하는 도중에 오류가 발생하여 DB에 일부만 저장되는 경우
//    데이터의 무결성을 해치는 것을 막기 위한 어노테이션
    public String saveBookinfo(Long d_isbn) throws IOException{
        //수정하려는 책이 DB에 있는지 먼저 확인
        Long bookId = storyMapper.getBookId(d_isbn);

        if(bookId == null){
            BookDTO bookDTO = getBookByIsbn(d_isbn);
            //DTO를 Entity로 변환
            BookEntity bookInfo = BookDTO.toEntity(bookDTO);

            storyMapper.saveBookInfo(bookInfo);

            bookId = bookInfo.getBook_id();
        }
        return "책 정보가 저장되었습니다.";
    }


    //책 제목을 통한 책 검색 후, 책 정보 DB에 저장
    @Transactional
    public String createStoryWithBook(String bookName, StoryDTO storyDTO) throws IOException{
        BookDTO bookDTO = getBookByName(bookName);
        //bookDTO 객체에 bookName이라는 필드가 없어도 되는가,
        //있는데 getBookByName이라는 함수를 사용하려면 매개변수를 booktitle로 해야하는가

        //
        if(bookDTO == null){
            return "책 정보를 찾을 수 없습니다.";
        }

        Long bookId = storyMapper.getBookId(bookDTO.getIsbn());

        if(bookId == null){
            //책 정보가 DB에 없다면 저장
            BookEntity bookEntity = BookDTO.toEntity(bookDTO);
            storyMapper.saveBookInfo(bookEntity);
            bookId = bookEntity.getBook_id();
            //저장후 id값을 bookId필드에 저장하는건데 필요 없을 것 같다.
            System.out.println("bookId : " + bookId);
        }

        // 독후감 저장
        StoryEntity storyEntity = StoryDTO.toEntity(storyDTO);
        storyEntity.setBook_id(bookId);
        storyMapper.createStory(storyEntity);

        return "독후감이 저장되었습니다.";
    }


//    public List<StoryDTO> getAllStoryWithLikeStatus(Long currentUserId) {
//        List<StoryEntity> storyEntities = storyMapper.getAllStory();
//        List<StoryDTO> storyDTOS = new ArrayList<>();
//
//        for (StoryEntity story : storyEntities) {
//            StoryDTO storyDTO = StoryEntity.toDTO(story);
//            boolean isLiked = storyLikeMapper.isAlreadyLiked(story.getStory_board_id(),  userId);
//            storyDTO.setLiked(isLiked);
//            storyDTOs.add(storyDTO);
//        }
//        return storyDTOs;
//    }

    public List<StoryDTO> getAllStory(Long userId) {
        List<StoryEntity> storyEntities = storyMapper.getAllStory();
        List<StoryDTO> storyDTOs = new ArrayList<>();

        for (StoryEntity story : storyEntities) {
            StoryDTO storyDTO = StoryEntity.toDTO(story);
            boolean isLiked = storyLikeMapper.isAlreadyLiked(story.getStory_board_id(), userId);
            storyDTO.setLiked(isLiked);
            storyDTOs.add(storyDTO);
        }

        return storyDTOs;
    }

}
