package com.booktory.booktoryserver.BookStory.domain;

import com.booktory.booktoryserver.BookStory.dto.request.CommentCreateDTO;
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

    // CommentCreateDTO를 CommentEntity로 변환하는 메서드
    public static CommentEntity fromDTO(CommentCreateDTO commentCreateDTO, UserEntity userEntity) {
        return CommentEntity.builder()
                .comment_content(commentCreateDTO.getCommentContent())
                .createdDate(new Date())
                .modifiedDate(new Date())
                .comment_author_user_id(commentCreateDTO.getUserId())
                .story_board_id(commentCreateDTO.getBoardId())
                //.author_profile_image()
                .author_nickname(userEntity.getUser_nickname())
                .build();
    }




}
