package com.myprojects.myportfolio.core.project;

import com.myprojects.myportfolio.core.user.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

//    public List<Project> getProjectsByUserId(Integer userId) {
//        List<ProjectProjection> projects = this.projectRepository.findAllByUserId(userId);
//        return projects;
//    }

}
