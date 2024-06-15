package com.booktory.booktoryserver.BookStory.dto.request;

import com.booktory.booktoryserver.BookStory.domain.CommentEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CommentCreateDTO {
    //private Long commentId;
    private String commentContent;
    //private LocalDateTime createDate;
    //private LocalDateTime modifiedDate;
    private Long userId;
    private Long boardId;

    //DTO객체를 Entity객체로 변환, 클라이언트에서 전송된 데이터를 데이터 베이스에 저장
    public static CommentEntity toEntity(CommentCreateDTO commentCreateDTO) {
        return CommentEntity.builder()
                //.comment_id(commentCreateDTO.getCommentId())
                .comment_content(commentCreateDTO.getCommentContent())
                //.createDate(commentCreateDTO.getCreateDate())
                //.modifiedDate(commentCreateDTO.getModifiedDate())
                .comment_author_user_id(commentCreateDTO.getUserId())
                .story_board_id(commentCreateDTO.getBoardId())
                .build();
    }
}