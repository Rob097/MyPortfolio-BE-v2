package com.myprojects.myportfolio.core.diary.mappers;

import com.myprojects.myportfolio.clients.diary.DiaryR;
import com.myprojects.myportfolio.clients.general.IController;
import com.myprojects.myportfolio.clients.general.Mapper;
import com.myprojects.myportfolio.clients.general.views.IView;
import com.myprojects.myportfolio.clients.general.views.Verbose;
import com.myprojects.myportfolio.core.diary.Diary;
import com.myprojects.myportfolio.core.story.mappers.SyntheticStoryRMapper;
import com.myprojects.myportfolio.core.user.mappers.SyntheticUserRMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

@Component
public class DiaryRMapper implements Mapper<DiaryR, Diary> {

    @Autowired
    private SyntheticDiaryRMapper syntheticMapper;

    @Autowired
    private SyntheticUserRMapper userRMapper;

    @Autowired
    private SyntheticStoryRMapper storyRMapper;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Override
    public DiaryR map(Diary input) {
        return this.map(input, new DiaryR());
    }

    @Override
    public DiaryR map(Diary input, DiaryR output) {

        output = this.syntheticMapper.map(input, output);

        if(output==null){
            return null;
        }

        IView view = (IView) this.httpServletRequest.getAttribute(IController.VIEW);

        if(view != null && view.isAtLeast(Verbose.value)) {
            output.setUser(this.userRMapper.map(input.getUser()));
            output.setStories(input.getStories().stream().map(el -> this.storyRMapper.map(el)).collect(Collectors.toSet()));
        }

        return output;
    }
}
