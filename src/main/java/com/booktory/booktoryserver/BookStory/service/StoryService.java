package com.booktory.booktoryserver.BookStory.service;

import com.booktory.booktoryserver.BookStory.domain.StoryEntity;
import com.booktory.booktoryserver.BookStory.dto.StoryDTO;
import com.booktory.booktoryserver.BookStory.mapper.StoryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class StoryService {
    private final StoryMapper storyMapper;
    public List<StoryEntity> getAllStory() {
        return storyMapper.getAllStory();
    }

    public void createStory(StoryDTO storyDTO) {
        StoryEntity storyEntity = StoryDTO.toEntity(storyDTO);
        storyMapper.createStory(storyEntity);
    }

    public void deleteStory(Long id){
        storyMapper.deleteStory(id);
    }

}
