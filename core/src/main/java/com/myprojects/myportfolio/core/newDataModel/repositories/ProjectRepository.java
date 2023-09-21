package com.myprojects.myportfolio.core.newDataModel.repositories;

import com.myprojects.myportfolio.core.newDataModel.dao.NewProject;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository(value="newProjectRepository")
public interface ProjectRepository extends BaseRepository<NewProject, Integer> {

    Optional<NewProject> findBySlug(String slug);

}
