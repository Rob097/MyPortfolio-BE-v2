package com.myprojects.myportfolio.core.project.mappers;

import com.myprojects.myportfolio.clients.general.IController;
import com.myprojects.myportfolio.clients.general.Mapper;
import com.myprojects.myportfolio.clients.general.views.IView;
import com.myprojects.myportfolio.clients.general.views.Verbose;
import com.myprojects.myportfolio.clients.project.ProjectR;
import com.myprojects.myportfolio.core.project.Project;
import com.myprojects.myportfolio.core.skill.mappers.SkillRMapper;
import com.myprojects.myportfolio.core.story.mappers.SyntheticStoryRMapper;
import com.myprojects.myportfolio.core.user.mappers.SyntheticUserRMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

@Component
public class ProjectRMapper implements Mapper<ProjectR, Project> {

    @Autowired
    private SyntheticProjectRMapper syntheticMapper;

    @Autowired
    private SyntheticUserRMapper userRMapper;

    @Autowired
    private SyntheticStoryRMapper storyRMapper;

    @Autowired
    private SkillRMapper skillRMapper;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Override
    public ProjectR map(Project input) {
        return this.map(input, new ProjectR());
    }

    @Override
    public ProjectR map(Project input, ProjectR output) {

        output = this.syntheticMapper.map(input, output);

        if(output==null){
            return null;
        }

        IView view = (IView) this.httpServletRequest.getAttribute(IController.VIEW);

        if(view != null && view.isAtLeast(Verbose.value)) {
            if (input.getUser() != null) {
                output.setUser(this.userRMapper.map(input.getUser()));
            }
            if (input.getStories() != null && !input.getStories().isEmpty()) {
                output.setStories(input.getStories().stream().map(el -> this.storyRMapper.map(el)).collect(Collectors.toSet()));
            }
            if(input.getSkills()!=null && !input.getSkills().isEmpty()) {
                output.setSkills(input.getSkills().stream().map(el -> this.skillRMapper.map(el)).collect(Collectors.toSet()));
            }
        }

        return output;
    }
}
