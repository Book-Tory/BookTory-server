package com.booktory.booktoryserver.BookStory.dto.response;

import com.booktory.booktoryserver.BookStory.domain.CommentEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CommentViewDTO {
    private Long commentId;
    private String commentContent;
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;
    private Long userId;
    private Long boardId;

    //Entity객체를 DTO객체로 변환
    public static CommentViewDTO fromEntity(CommentEntity commentEntity) {
        return CommentViewDTO.builder()
                .commentId(commentEntity.getComment_id())
                .commentContent(commentEntity.getComment_content())
                .createDate(commentEntity.getCreateDate())
                .modifiedDate(commentEntity.getModifiedDate())
                .userId(commentEntity.getComment_author_user_id())
                .boardId(commentEntity.getStory_board_id())
                .build();
    }
}
