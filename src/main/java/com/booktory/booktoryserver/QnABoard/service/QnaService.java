package com.booktory.booktoryserver.QnABoard.service;

import com.booktory.booktoryserver.QnABoard.domain.QnaBoard;
import com.booktory.booktoryserver.QnABoard.dto.request.QnaRequestDTO;
import com.booktory.booktoryserver.QnABoard.mapper.QnaBoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QnaService {
    private PasswordEncoder passwordEncoder;

    private final QnaBoardMapper qnaBoardMapper;
    public int createQna(QnaRequestDTO qnaRequestDTO) {

        QnaBoard qnaBoard = QnaBoard.covertToDTO(qnaRequestDTO);

        return qnaBoardMapper.insertQnaBoard(qnaBoard);
    }
}
