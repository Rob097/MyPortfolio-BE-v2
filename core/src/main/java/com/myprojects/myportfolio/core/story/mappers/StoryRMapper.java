package com.myprojects.myportfolio.core.story.mappers;

import com.myprojects.myportfolio.clients.general.Mapper;
import com.myprojects.myportfolio.clients.story.StoryR;
import com.myprojects.myportfolio.core.education.mappers.EducationRMapper;
import com.myprojects.myportfolio.core.experience.mappers.ExperienceRMapper;
import com.myprojects.myportfolio.core.skill.mappers.SkillRMapper;
import com.myprojects.myportfolio.core.story.Story;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class StoryRMapper implements Mapper<StoryR, Story> {

    @Autowired
    private EducationRMapper educationRMapper;

    @Autowired
    private ExperienceRMapper experienceRMapper;

    @Autowired
    private SkillRMapper skillRMapper;

    @Override
    public StoryR map(Story input) {
        return this.map(input, new StoryR());
    }

    @Override
    public StoryR map(Story input, StoryR output) {
        if(input==null){
            return null;
        }
        if(output==null){
            output = new StoryR();
        }

        output.setId(input.getId());
        output.setDiaryId(input.getDiary().getId());
        output.setTitle(input.getTitle());
        output.setDescription(input.getDescription());
        output.setEntryDateTime(input.getEntryDateTime());
        output.setIsPrimaryStory(input.getIsPrimaryStory());
        output.setFromDate(input.getFromDate());
        output.setToDate(input.getToDate());
        if(input.getEducationList()!=null && !input.getEducationList().isEmpty()) {
            output.setEducationList(input.getEducationList().stream().map(el -> this.educationRMapper.map(el)).collect(Collectors.toSet()));
        }
        if(input.getExperienceList()!=null && !input.getExperienceList().isEmpty()) {
            output.setExperienceList(input.getExperienceList().stream().map(el -> this.experienceRMapper.map(el)).collect(Collectors.toSet()));
        }
        if(input.getSkillList()!=null && !input.getSkillList().isEmpty()) {
            output.setSkillList(input.getSkillList().stream().map(el -> this.skillRMapper.map(el)).collect(Collectors.toSet()));
        }

        return output;
    }
}
