package com.booktory.booktoryserver.QnABoard.mapper;

import com.booktory.booktoryserver.QnABoard.domain.QnaBoard;
import com.booktory.booktoryserver.QnABoard.dto.response.QnaResponseDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QnaBoardMapper {
    int insertQnaBoard(QnaBoard qnaBoard);

    List<QnaBoard> findAllQna();

    int updateQnaBoard(QnaBoard qnaBoard);

    QnaBoard findByQnaId(Long qnaId);

    int softDeleteQnaBoard(Long qnaId);

    void moveToDeleteTable(Long qnaId);

    QnaBoard findByContent(Long qnaId);
}
