package com.myprojects.myportfolio.core.story.mappers;

import com.myprojects.myportfolio.clients.general.Mapper;
import com.myprojects.myportfolio.clients.story.StoryR;
import com.myprojects.myportfolio.core.diary.Diary;
import com.myprojects.myportfolio.core.education.Education;
import com.myprojects.myportfolio.core.experience.Experience;
import com.myprojects.myportfolio.core.skill.Skill;
import com.myprojects.myportfolio.core.story.Story;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class StoryMapper implements Mapper<Story, StoryR> {

    @Override
    public Story map(StoryR input) {
        return this.map(input, new Story());
    }

    @Override
    public Story map(StoryR input, Story output) {
        if(input==null){
            return null;
        }
        if(output==null){
            output = new Story();
        }

        output.setId(input.getId());
        if(input.getSlug()!=null) {
            output.setSlug(input.getSlug());
        }
        output.setTitle(input.getTitle());
        output.setDescription(input.getDescription());
        output.setEntryDateTime(input.getEntryDateTime());
        output.setIsPrimaryStory(input.getIsPrimaryStory());
        output.setFromDate(input.getFromDate());
        output.setToDate(input.getToDate());
        if(input.getDiary()!=null) {
            output.setDiary(Diary.builder().id(input.getDiary().getId()).build());
        }
        if(input.getEducations()!=null && !input.getEducations().isEmpty()) {
            output.setEducations(input.getEducations().stream().map(el -> Education.builder().id(el.getId()).build()).collect(Collectors.toSet()));
        }
        if(input.getExperiences()!=null && !input.getExperiences().isEmpty()) {
            output.setExperiences(input.getExperiences().stream().map(el -> Experience.builder().id(el.getId()).build()).collect(Collectors.toSet()));
        }
        if(input.getSkills()!=null && !input.getSkills().isEmpty()) {
            output.setSkills(input.getSkills().stream().map(el -> Skill.builder().id(el.getId()).build()).collect(Collectors.toSet()));
        }

        return output;
    }
}
