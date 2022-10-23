package com.myprojects.myportfolio.core.story.mappers;

import com.myprojects.myportfolio.clients.general.Mapper;
import com.myprojects.myportfolio.clients.story.StoryR;
import com.myprojects.myportfolio.core.diary.Diary;
import com.myprojects.myportfolio.core.education.mappers.EducationMapper;
import com.myprojects.myportfolio.core.experience.mappers.ExperienceMapper;
import com.myprojects.myportfolio.core.experience.mappers.ExperienceRMapper;
import com.myprojects.myportfolio.core.skill.mappers.SkillMapper;
import com.myprojects.myportfolio.core.skill.mappers.SkillRMapper;
import com.myprojects.myportfolio.core.story.Story;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class StoryMapper implements Mapper<Story, StoryR> {

    @Autowired
    private EducationMapper educationMapper;

    @Autowired
    private ExperienceMapper experienceMapper;

    @Autowired
    private SkillMapper skillMapper;

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
        output.setDiary(Diary.builder().id(input.getDiaryId()).build());
        output.setTitle(input.getTitle());
        output.setDescription(input.getDescription());
        output.setEntryDateTime(input.getEntryDateTime());
        output.setIsPrimaryStory(input.getIsPrimaryStory());
        output.setFromDate(input.getFromDate());
        output.setToDate(input.getToDate());
        if(input.getEducationList()!=null && !input.getEducationList().isEmpty()) {
            output.setEducationList(input.getEducationList().stream().map(el -> this.educationMapper.map(el)).collect(Collectors.toSet()));
        }
        if(input.getExperienceList()!=null && !input.getExperienceList().isEmpty()) {
            output.setExperienceList(input.getExperienceList().stream().map(el -> this.experienceMapper.map(el)).collect(Collectors.toSet()));
        }
        if(input.getSkillList()!=null && !input.getSkillList().isEmpty()) {
            output.setSkillList(input.getSkillList().stream().map(el -> this.skillMapper.map(el)).collect(Collectors.toSet()));
        }

        return output;
    }
}
