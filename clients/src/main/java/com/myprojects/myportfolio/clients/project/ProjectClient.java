package com.myprojects.myportfolio.clients.project;

import com.myprojects.myportfolio.clients.user.UserR;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(
        value = "project",
        path = "api/v1/projects"
)
public interface ProjectClient {

    @GetMapping(path = "/{ownerId}")
    List<ProjectR> getProjectsByUserId(@PathVariable("ownerId") Integer ownerId);

}
