package com.booktory.booktoryserver.ProductReview.mapper;

import com.booktory.booktoryserver.ProductReview.domain.UserData;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMappers {
    UserData findUserByEmail(String userEmail);
}
