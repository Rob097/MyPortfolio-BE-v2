package com.myprojects.myportfolio.core.diary.mappers;

import com.myprojects.myportfolio.clients.diary.DiaryR;
import com.myprojects.myportfolio.clients.general.Mapper;
import com.myprojects.myportfolio.clients.general.views.IView;
import com.myprojects.myportfolio.core.diary.Diary;
import com.myprojects.myportfolio.core.story.mappers.StoryMapper;
import com.myprojects.myportfolio.core.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class DiaryMapper implements Mapper<Diary, DiaryR> {

    @Autowired
    private StoryMapper storyMapper;

    @Override
    public Diary map(DiaryR input) {
        return this.map(input, new Diary());
    }

    @Override
    public Diary map(DiaryR input, Diary output) {
        if(input==null){
            return null;
        }
        if(output==null){
            output = new Diary();
        }

        output.setId(input.getId());
        output.setEntryDateTime(input.getEntryDateTime());
        if(input.getUser()!=null) {
            output.setUser(User.builder().id(input.getUser().getId()).build());
        }
        if(input.getStories()!=null && !input.getStories().isEmpty()) {
            output.setStories(input.getStories().stream().map(el -> this.storyMapper.map(el)).collect(Collectors.toSet()));
        }

        return output;
    }
}
