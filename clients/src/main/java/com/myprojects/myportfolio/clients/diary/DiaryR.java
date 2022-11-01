package com.myprojects.myportfolio.clients.diary;

import com.myprojects.myportfolio.clients.story.StoryR;
import com.myprojects.myportfolio.clients.user.UserR;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class DiaryR {

    private Integer id;
    private LocalDateTime entryDateTime;
    private UserR user;
    private Set<StoryR> stories;

}
