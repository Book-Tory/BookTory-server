package com.booktory.booktoryserver.BookStory.service;

import com.booktory.booktoryserver.BookStory.domain.StoryEntity;
import com.booktory.booktoryserver.BookStory.mapper.StoryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
}
