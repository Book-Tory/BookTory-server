package com.booktory.booktoryserver.BookStory.dto;

import com.booktory.booktoryserver.BookStory.domain.StoryEntity;
import lombok.*;

import java.time.LocalDateTime;

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
    private int loveCount;
    private Long userId;
    private Long bookId;

    
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
}
