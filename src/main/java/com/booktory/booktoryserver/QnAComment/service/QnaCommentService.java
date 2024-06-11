package com.booktory.booktoryserver.QnAComment.service;

import com.booktory.booktoryserver.QnAComment.dto.QnaCommentDTO;
import com.booktory.booktoryserver.QnAComment.mapper.QnaCommentMapper;
import com.booktory.booktoryserver.QnAComment.model.QnaComment;
import com.booktory.booktoryserver.Users.mapper.UserMapper;
import com.booktory.booktoryserver.Users.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QnaCommentService {

    private final UserMapper userMapper;

    private final QnaCommentMapper commentMapper;

    public int createComment(QnaCommentDTO qnaCommentDTO, String email, Long qnaId) {

        Optional<UserEntity> user = userMapper.findByEmail(email);

        if(user.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 유저입니다.");
        }

        String username = user.get().getUser_nickname();

        QnaComment qnaComment = QnaComment.toQnaComment(qnaCommentDTO,username, qnaId);

        return commentMapper.insertQnaComment(qnaComment);
    }
}
