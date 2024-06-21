package com.booktory.booktoryserver.BookStory.dto.response;

import com.booktory.booktoryserver.BookStory.dto.StoryDTO;

public class ResponseDTO {
    private StoryDTO story;
    private Long currentUserId;

    public ResponseDTO(StoryDTO story, Long currentUserId){
        this.story = story;
        this.currentUserId = currentUserId;
    }
}
