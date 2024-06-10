package com.booktory.booktoryserver.QnABoard.mapper;

import com.booktory.booktoryserver.QnABoard.domain.QnaBoard;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QnaBoardMapper {
    int insertQnaBoard(QnaBoard qnaBoard);

}
