package com.myprojects.myportfolio.core.user.mappers;

import com.myprojects.myportfolio.clients.general.IController;
import com.myprojects.myportfolio.clients.general.Mapper;
import com.myprojects.myportfolio.clients.general.views.IView;
import com.myprojects.myportfolio.clients.general.views.Verbose;
import com.myprojects.myportfolio.clients.user.UserR;
import com.myprojects.myportfolio.core.diary.mappers.SyntheticDiaryRMapper;
import com.myprojects.myportfolio.core.education.mappers.SyntheticEducationRMapper;
import com.myprojects.myportfolio.core.experience.mappers.SyntheticExperienceRMapper;
import com.myprojects.myportfolio.core.project.mappers.SyntheticProjectRMapper;
import com.myprojects.myportfolio.core.skill.mappers.SkillRMapper;
import com.myprojects.myportfolio.core.user.User;
import com.myprojects.myportfolio.core.user.UserSkill;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

@Component
@Slf4j
public class UserRMapper implements Mapper<UserR, User> {

    @Autowired
    private SyntheticUserRMapper syntheticMapper;

    @Autowired
    private SyntheticDiaryRMapper diaryRMapper;

    @Autowired
    private SyntheticProjectRMapper projectRMapper;

    @Autowired
    private SyntheticEducationRMapper educationRMapper;

    @Autowired
    private SyntheticExperienceRMapper experienceRMapper;

    @Autowired
    private SkillRMapper skillRMapper;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Override
    public UserR map(User input){
        return this.map(input, new UserR());
    }

    @Override
    public UserR map(User input, UserR output){

        output = this.syntheticMapper.map(input, output);

        if(output==null){
            return null;
        }

        IView view = (IView) this.httpServletRequest.getAttribute(IController.VIEW);

        if(view != null && view.isAtLeast(Verbose.value)) {
            if (input.getDiaries() != null && !input.getDiaries().isEmpty()) {
                output.setDiaries(input.getDiaries().stream().map(el -> this.diaryRMapper.map(el)).collect(Collectors.toList()));
            }
            if (input.getProjects() != null && !input.getProjects().isEmpty()) {
                output.setProjects(input.getProjects().stream().map(el -> this.projectRMapper.map(el)).collect(Collectors.toList()));
            }
            if (input.getSkills() != null && !input.getSkills().isEmpty()) {
                output.setSkills(input.getSkills().stream().map(this::userSkillMap).collect(Collectors.toList()));
            }
            if (input.getEducations() != null && !input.getEducations().isEmpty()) {
                output.setEducations(input.getEducations().stream().map(el -> this.educationRMapper.map(el)).collect(Collectors.toList()));
            }
            if (input.getExperiences() != null && !input.getExperiences().isEmpty()) {
                output.setExperiences(input.getExperiences().stream().map(el -> this.experienceRMapper.map(el)).collect(Collectors.toList()));
            }
        }

        return output;

    }

    private UserR.UserSkillR userSkillMap(UserSkill userSkill) {
        UserR.UserSkillR userSkillR = new UserR.UserSkillR();

        userSkillR.setUserId(userSkill.getUser().getId());
        userSkillR.setSkill(skillRMapper.map(userSkill.getSkill()));
        userSkillR.setIsMain(userSkill.isMain());
        userSkillR.setOrderId(userSkill.getOrderId());

        return userSkillR;
    }

}
