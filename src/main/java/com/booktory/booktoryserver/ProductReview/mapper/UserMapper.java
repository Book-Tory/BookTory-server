package com.booktory.booktoryserver.ProductReview.mapper;

import com.booktory.booktoryserver.ProductReview.domain.UserData;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    UserData findUserByEmail(String userEmail);
}
