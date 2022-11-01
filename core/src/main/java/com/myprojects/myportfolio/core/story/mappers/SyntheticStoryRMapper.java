package com.myprojects.myportfolio.core.story.mappers;

import com.myprojects.myportfolio.clients.general.Mapper;
import com.myprojects.myportfolio.clients.story.StoryR;
import com.myprojects.myportfolio.core.story.Story;
import org.springframework.stereotype.Component;

@Component
public class SyntheticStoryRMapper implements Mapper<StoryR, Story> {

    @Override
    public StoryR map(Story input, StoryR output) {
        if(input==null){
            return null;
        }
        if(output==null){
            output = new StoryR();
        }

        output.setId(input.getId());
        output.setTitle(input.getTitle());
        output.setDescription(input.getDescription());
        output.setEntryDateTime(input.getEntryDateTime());
        output.setIsPrimaryStory(input.getIsPrimaryStory());
        output.setFromDate(input.getFromDate());
        output.setToDate(input.getToDate());

        return output;
    }

}
