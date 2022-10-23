package com.myprojects.myportfolio.core.diary.mappers;

import com.myprojects.myportfolio.clients.diary.DiaryR;
import com.myprojects.myportfolio.clients.general.Mapper;
import com.myprojects.myportfolio.core.diary.Diary;
import com.myprojects.myportfolio.core.story.mappers.StoryRMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class DiaryRMapper implements Mapper<DiaryR, Diary> {

    @Autowired
    private StoryRMapper storyRMapper;

    @Override
    public DiaryR map(Diary input) {
        return this.map(input, new DiaryR());
    }

    @Override
    public DiaryR map(Diary input, DiaryR output) {
        if(input==null){
            return null;
        }
        if(output==null){
            output = new DiaryR();
        }

        output.setId(input.getId());
        output.setUserId(input.getUser().getId());
        output.setEntryDateTime(input.getEntryDateTime());
        output.setStories(input.getStories().stream().map(el -> this.storyRMapper.map(el)).collect(Collectors.toSet()));

        return output;
    }
}
