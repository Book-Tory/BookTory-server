package com.booktory.booktoryserver.BookStory.domain;

import com.booktory.booktoryserver.Users.model.UserEntity;
import lombok.*;
import org.joda.time.DateTime;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class CommentEntity {
    private Long comment_id;
    private String comment_content;


    private Date createdDate;
    private Date modifiedDate;


    private Long comment_author_user_id; //작성자
    private Long story_board_id; //독후감 게시물

    private String author_profile_image; // 작성자 프로필 이미지
    private String author_nickname; // 작성자 닉네임
}
