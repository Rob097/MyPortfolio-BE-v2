package com.myprojects.myportfolio.clients.project;

import com.myprojects.myportfolio.clients.skill.SkillR;
import com.myprojects.myportfolio.clients.story.StoryR;
import com.myprojects.myportfolio.clients.user.UserR;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectR {

    private Integer id;
    private String slug;
    private String title;
    private String description;
    private LocalDateTime entryDateTime;
    private UserR user;
    private Set<StoryR> stories;
    private Set<SkillR> skills;

}
