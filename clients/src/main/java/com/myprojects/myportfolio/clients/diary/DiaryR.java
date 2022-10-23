package com.myprojects.myportfolio.clients.diary;

import com.myprojects.myportfolio.clients.story.StoryR;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DiaryR {

    private Integer id;
    private LocalDateTime entryDateTime;
    private Integer userId;
    private Set<StoryR> stories;

}
