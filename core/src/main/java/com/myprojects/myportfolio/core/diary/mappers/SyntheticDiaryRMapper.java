package com.myprojects.myportfolio.core.diary.mappers;

import com.myprojects.myportfolio.clients.diary.DiaryR;
import com.myprojects.myportfolio.clients.general.Mapper;
import com.myprojects.myportfolio.clients.user.UserR;
import com.myprojects.myportfolio.core.diary.Diary;
import org.springframework.stereotype.Component;

@Component
public class SyntheticDiaryRMapper implements Mapper<DiaryR, Diary> {
    @Override
    public DiaryR map(Diary input, DiaryR output) {
        if(input==null){
            return null;
        }
        if(output==null){
            output = new DiaryR();
        }

        output.setId(input.getId());
        output.setEntryDateTime(input.getEntryDateTime());
        if(input.getUser()!=null) {
            output.setUser(UserR.builder().id(input.getUser().getId()).build());
        }

        return output;
    }
}
