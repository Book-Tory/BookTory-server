package com.booktory.booktoryserver.BookStory.dto.response;

import com.booktory.booktoryserver.BookStory.dto.StoryDTO;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseDTO {
    private StoryDTO story;
    private Long currentUserId;
    private String currentUserNickname;
    private String currentUserImg;

    public ResponseDTO(StoryDTO story, Long currentUserId){
        this.story = story;
        this.currentUserId = currentUserId;
        this.currentUserNickname = currentUserNickname;
        this.currentUserImg = currentUserImg;
    }
}
