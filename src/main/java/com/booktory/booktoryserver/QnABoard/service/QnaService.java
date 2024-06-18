package com.booktory.booktoryserver.QnABoard.service;

import com.booktory.booktoryserver.QnABoard.domain.QnaBoard;
import com.booktory.booktoryserver.QnABoard.dto.request.QnaRequestDTO;
import com.booktory.booktoryserver.QnABoard.dto.response.QnaResponseDTO;
import com.booktory.booktoryserver.QnABoard.mapper.QnaBoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QnaService {
    private final PasswordEncoder passwordEncoder;

    private final QnaBoardMapper qnaBoardMapper;
    public int createQna(QnaRequestDTO qnaRequestDTO) {

        qnaRequestDTO.setQnaPassword(passwordEncoder.encode(qnaRequestDTO.getQnaPassword()));

        QnaBoard qnaBoard = QnaBoard.covertToDTO(qnaRequestDTO);

        return qnaBoardMapper.insertQnaBoard(qnaBoard);
    }

    public List<QnaResponseDTO> findAllQna() {

        List<QnaBoard> qnaBoards = qnaBoardMapper.findAllQna();

        return qnaBoards.stream().map(QnaResponseDTO::toQnaResponse).collect(Collectors.toList());
    }

    public int updateQna(QnaRequestDTO qnaRequestDTO) {

        qnaRequestDTO.setQnaPassword(passwordEncoder.encode(qnaRequestDTO.getQnaPassword()));

        QnaBoard qnaBoard = QnaBoard.covertToDTO(qnaRequestDTO);

        return qnaBoardMapper.updateQnaBoard(qnaBoard);
    }

    public QnaResponseDTO findByQnaId(Long qnaId, String rawPassword) {
        QnaBoard qnaBoard = qnaBoardMapper.findByQnaId(qnaId);
        if (qnaBoard == null) {
            throw new IllegalArgumentException("Invalid Qna ID");
        }
        if (qnaBoard.getIs_lock() && (rawPassword == null || rawPassword.isEmpty() || !passwordEncoder.matches(rawPassword, qnaBoard.getQna_password()))) {
            throw new IllegalArgumentException("Invalid Password");
        }
        return QnaResponseDTO.toQnaResponse(qnaBoard);
    }

    @Transactional
    public int deleteQnaBoard(Long qnaId) {
        qnaBoardMapper.moveToDeleteTable(qnaId);
        int result = qnaBoardMapper.softDeleteQnaBoard(qnaId);
        return result;
    }

    public QnaResponseDTO findByContent(Long qnaId) {
        QnaBoard qnaBoard = qnaBoardMapper.findByContent(qnaId);

        return QnaResponseDTO.toQnaResponse(qnaBoard);
    }
}
