package com.booktory.booktoryserver.QnAComment.mapper;

import com.booktory.booktoryserver.QnAComment.model.QnaComment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QnaCommentMapper {
    int insertQnaComment(QnaComment qnaComment);

}
