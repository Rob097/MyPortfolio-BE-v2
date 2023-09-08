package com.myprojects.myportfolio.clients.education;

import com.myprojects.myportfolio.clients.story.StoryR;
import com.myprojects.myportfolio.clients.user.UserR;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class EducationR {

    private Integer id;
    private String slug;
    private UserR user;
    private String school;
    private String degree;
    private String field;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double grade;
    private String description;
    private Set<StoryR> stories;

}
