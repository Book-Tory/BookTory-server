package com.booktory.booktoryserver.Users.mapper;

import com.booktory.booktoryserver.Users.model.RefreshEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

@Mapper
public interface RefreshMapper {

    Boolean existsByRefresh(String refresh);

    @Transactional
    void deleteByRefresh(String refresh);

    void save(RefreshEntity refreshEntity);
}
