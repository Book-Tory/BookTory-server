package com.booktory.booktoryserver.BookStory.domain;

import com.booktory.booktoryserver.BookStory.dto.StoryDTO;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class StoryEntity {
    //독후감 정보
    private Long story_board_id;
    private Long mystory_type_id;
    private String mystory_name;
    private String mystory_content;
    private LocalDateTime mystory_date;
    private LocalDateTime read_date;
    private int love_count;

    //독후감 등록 사용자
    private Long user_id;
    private String user_nickname;

    //책 정보
    private Long book_id;
    private String book_name;
    private String book_author;
    private String book_publisher;
    private Long book_isbn;
    private String book_image;


    //Entity를 DTO로 변환하는 정적 메서드
    //정적 메서드로 선언하였습니다.
    public static StoryDTO toDTO(StoryEntity storyEntity) {
        return StoryDTO.builder()
                .storyBoardId(storyEntity.getStory_board_id())
                .mystoryTypeId(storyEntity.getMystory_type_id())
                .mystoryName(storyEntity.getMystory_name())
                .mystoryContent(storyEntity.getMystory_content())
                .loveCount(storyEntity.getLove_count())
                .userId(storyEntity.getUser_id())
                .bookId(storyEntity.getBook_id())
                .build();
    }

    //update에 필요한 id를 받아와서 설정해주는 메서드가 필요하다.
    public void setStory_board_id(long story_board_id){
        this.story_board_id = story_board_id;
    }


}