package com.myprojects.myportfolio.core.story.mappers;

import com.myprojects.myportfolio.clients.general.IController;
import com.myprojects.myportfolio.clients.general.Mapper;
import com.myprojects.myportfolio.clients.general.views.IView;
import com.myprojects.myportfolio.clients.general.views.Verbose;
import com.myprojects.myportfolio.clients.story.StoryR;
import com.myprojects.myportfolio.core.diary.mappers.SyntheticDiaryRMapper;
import com.myprojects.myportfolio.core.education.mappers.SyntheticEducationRMapper;
import com.myprojects.myportfolio.core.experience.mappers.SyntheticExperienceRMapper;
import com.myprojects.myportfolio.core.skill.mappers.SkillRMapper;
import com.myprojects.myportfolio.core.story.Story;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

@Component
public class StoryRMapper implements Mapper<StoryR, Story> {

    @Autowired
    private SyntheticStoryRMapper syntheticMapper;

    @Autowired
    private SyntheticDiaryRMapper diaryRMapper;

    @Autowired
    private SyntheticEducationRMapper educationRMapper;

    @Autowired
    private SyntheticExperienceRMapper experienceRMapper;

    @Autowired
    private SkillRMapper skillRMapper;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Override
    public StoryR map(Story input, StoryR output) {

        output = this.syntheticMapper.map(input, output);

        if(output==null){
            return null;
        }

        IView view = (IView) this.httpServletRequest.getAttribute(IController.VIEW);

        if(view != null && view.isAtLeast(Verbose.value)) {
            if (input.getDiary() != null) {
                output.setDiary(this.diaryRMapper.map(input.getDiary()));
            }
            if (input.getEducations() != null && !input.getEducations().isEmpty()) {
                output.setEducations(input.getEducations().stream().map(el -> this.educationRMapper.map(el)).collect(Collectors.toSet()));
            }
            if (input.getExperiences() != null && !input.getExperiences().isEmpty()) {
                output.setExperiences(input.getExperiences().stream().map(el -> this.experienceRMapper.map(el)).collect(Collectors.toSet()));
            }
            if (input.getSkills() != null && !input.getSkills().isEmpty()) {
                output.setSkills(input.getSkills().stream().map(el -> this.skillRMapper.map(el)).collect(Collectors.toSet()));
            }
        }

        return output;
    }
}
