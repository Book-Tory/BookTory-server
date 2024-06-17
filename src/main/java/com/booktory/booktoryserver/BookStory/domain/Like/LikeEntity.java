package com.booktory.booktoryserver.BookStory.domain.Like;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class LikeEntity {
    private Long like_id;
    private LocalDateTime like_date;
    private Long user_id;
    private Long story_board_id;
}
