package com.myprojects.myportfolio.core.education.mappers;

import com.myprojects.myportfolio.clients.education.EducationR;
import com.myprojects.myportfolio.clients.general.IController;
import com.myprojects.myportfolio.clients.general.Mapper;
import com.myprojects.myportfolio.clients.general.views.IView;
import com.myprojects.myportfolio.clients.general.views.Verbose;
import com.myprojects.myportfolio.core.education.Education;
import com.myprojects.myportfolio.core.story.mappers.SyntheticStoryRMapper;
import com.myprojects.myportfolio.core.user.mappers.SyntheticUserRMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

@Component
public class EducationRMapper implements Mapper<EducationR, Education> {

    @Autowired
    private SyntheticEducationRMapper syntheticMapper;

    @Autowired
    private SyntheticUserRMapper userRMapper;

    @Autowired
    private SyntheticStoryRMapper storyRMapper;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Override
    public EducationR map(Education input, EducationR output) {

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
        }

        return output;
    }

}
