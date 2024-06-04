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

    public void updateStory(Long id, StoryDTO storyDTO) {
        log.info("id : " + id);
        log.info("storyDTO : " + storyDTO);
        StoryEntity storyEntity = StoryDTO.toEntity(storyDTO);
        //+ toEntity가 static으로 선언되었으므로 클래스명을 사용하여 호출하는 것이 맞다.
        //StoryDTO클래스의 toEntity 메서드를 사용하여 Entity객체를 선언
        //매개변수로 받은 DTO를 DB에 저장하기 위해 Entity객체로 변환

        storyEntity.setStory_board_id(id);

        storyMapper.updateStory(storyEntity);

    }

    public StoryEntity getStoryById(Long story_board_id) {
        return storyMapper.getStoryById(story_board_id);
    }
}
