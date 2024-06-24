package com.booktory.booktoryserver.BookStory.dto;

import com.booktory.booktoryserver.BookStory.domain.StoryEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoryDTO {
    private Long storyBoardId;
    private Long mystoryTypeId;
    private String mystoryName;
    private String mystoryContent;
    private LocalDateTime mystoryDate;
    private LocalDateTime readDate;

    //
    private String bookName;
    private String bookAuthor;
    private String bookPublisher;
    private Date bookPudbdate;
    private String bookImage;
    private int bookPrice;
    private Long bookIsbn;

    private int loveCount;
    private Long userId;
    private Long bookId;

    private String userNickname;
    private String userImg;

    private boolean isLiked;

    //DTO를 Entity로 변환하는 메서드
    public static StoryEntity toEntity(StoryDTO storyDTO) {
        return StoryEntity.builder()
                .story_board_id(storyDTO.getStoryBoardId())
                .mystory_type_id(storyDTO.getMystoryTypeId())
                .mystory_name(storyDTO.getMystoryName())
                .mystory_content(storyDTO.getMystoryContent())
                .love_count(storyDTO.getLoveCount())
                .user_id(storyDTO.getUserId())
                .book_id(storyDTO.getBookId())
                .build();
    }


    public void updateEntity(StoryEntity storyEntity) {
        storyEntity.setMystory_type_id(this.mystoryTypeId);
        storyEntity.setMystory_name(this.mystoryName);
        storyEntity.setMystory_content(this.mystoryContent);
//        storyEntity.setLove_count(this.loveCount);
//        storyEntity.setUser_id(this.userId);
//        storyEntity.setBook_id(this.bookId);
//        storyEntity.setBook_name(this.bookName);
//        storyEntity.setBook_author(this.bookAuthor);
//        storyEntity.setBook_publisher(this.bookPublisher);
//        storyEntity.setBook_isbn(this.bookIsbn);
//        storyEntity.setBook_image(this.bookImage);
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    //update를 위한 DTO와 id 둘 다를 매개변수로 받는 객체가 따로 필요하다
    //그러나 이미 DTO객체가 있으므로 id만 따로 받아주는 객체를 만들었다.

    // 업데이트를 위한 DTO 객체와 id를 매개변수로 받아 Entity 객체를 생성하는 메서드
//    public static StoryEntity updateToEntity(Long id, StoryDTO storyDTO) {
//        return StoryEntity.builder()
//                .story_board_id(id)
//                .mystory_type_id(storyDTO.getMystoryTypeId())
//                .mystory_name(storyDTO.getMystoryName())
//                .mystory_content(storyDTO.getMystoryContent())
//                .love_count(storyDTO.getLoveCount())
//                .user_id(storyDTO.getUserId())
//                .book_id(storyDTO.getBookId())
//                .build();
//    }
    //오류~!! 이 메서드는 그럼 Entity클래스에서 존재해야 한다.

//    public void setStory_board_id(long story_board_id){
//        this.storyBoardId = story_board_id;
//    }
        //Entity클래스에 해당 메소드를 수정하여 구현

}
