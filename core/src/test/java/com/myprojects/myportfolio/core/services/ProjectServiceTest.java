package com.myprojects.myportfolio.core.services;

import com.myprojects.myportfolio.core.BaseTest;
import com.myprojects.myportfolio.core.newDataModel.dao.NewDiary;
import com.myprojects.myportfolio.core.newDataModel.dao.NewProject;
import com.myprojects.myportfolio.core.newDataModel.dao.NewStory;
import com.myprojects.myportfolio.core.newDataModel.dao.NewUser;
import com.myprojects.myportfolio.core.newDataModel.dao.skills.NewSkill;
import com.myprojects.myportfolio.core.newDataModel.services.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional // For the rollback
public class ProjectServiceTest extends BaseTest {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private EntityManager entityManager;

    private NewProject project;
    private NewProject projectWithRelations;

    private NewProject getNewProject() {
        NewProject project = new NewProject();
        project.setTitle("Test Project");
        project.setDescription("Test Description");
        project.setUser(NewUser.builder().id(1).build());
        return project;
    }

    @BeforeEach
    void setUp() {
        this.project = getNewProject();
        this.projectWithRelations = getNewProject();

        // Create a new object NewStory and add it to the project
        Set<NewStory> stories = new HashSet<>();
        NewStory story = new NewStory();
        story.setDiary(NewDiary.builder().id(1).build());
        story.setTitle("Test Story");
        story.setDescription("Test Description");
        story.setFromDate(LocalDate.now());
        story.setToDate(LocalDate.now());
        story.setIsPrimaryStory(true);
        story.setFirstRelevantSection("Test Section");
        story.setSecondRelevantSection("Test Section");
        stories.add(story);

        Set<NewSkill> skills = new HashSet<>();
        NewSkill skill = new NewSkill();
        skill.setId(1);
        skills.add(skill);

        projectWithRelations.setStories(stories);
        projectWithRelations.setSkills(skills);

    }

    @Test
    void save() {
        try {

            // Save the project
            this.projectService.save(this.project);

            // After saving the project, hibernate would use the first level cache to retrieve the project from memory
            // instead of hitting the database again. So we need to clear the entity manager to force the next query
            // Clear the entity manager to force the next query to hit the database
            entityManager.flush();
            entityManager.clear();

            // check if the project has been saved correctly:
            NewProject createdProject = projectService.findById(this.project.getId());
            assertNotNull(createdProject);

            assertEquals(this.project.getTitle(), createdProject.getTitle());
            assertEquals(this.project.getDescription(), createdProject.getDescription());
            assertEquals(this.project.getUser().getId(), createdProject.getUser().getId());

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void saveWithRelation() {
        try {

            // Save the project
            this.projectService.save(this.projectWithRelations);

            // After saving the project, hibernate would use the first level cache to retrieve the project from memory
            // instead of hitting the database again. So we need to clear the entity manager to force the next query
            // Clear the entity manager to force the next query to hit the database
            entityManager.flush();
            entityManager.clear();

            // check if the project has been saved correctly:
            NewProject createdProject = projectService.findById(this.projectWithRelations.getId());
            assertNotNull(createdProject);

            assertEquals(this.projectWithRelations.getTitle(), createdProject.getTitle());
            assertEquals(this.projectWithRelations.getDescription(), createdProject.getDescription());
            assertEquals(this.projectWithRelations.getUser().getId(), createdProject.getUser().getId());
            assertEquals(this.projectWithRelations.getStories().size(), createdProject.getStories().size());
            assertEquals(this.projectWithRelations.getSkills().size(), createdProject.getSkills().size());

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void update() {
        try {
            // Save the project
            this.projectService.save(this.project);

            // Change the project
            String newTitle = "New Title";
            this.project.setTitle(newTitle);

            // Update the project
            this.projectService.update(this.project);

            // Clear the entity manager to force the next query to hit the database
            entityManager.flush();
            entityManager.clear();

            // Check if the project has been updated correctly:
            NewProject updatedProject = this.projectService.findById(this.project.getId());
            assertNotNull(updatedProject);
            assertEquals(this.project.getTitle(), updatedProject.getTitle());
            assertEquals(newTitle, updatedProject.getTitle());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void updateWithRelations() {
        try {

            // Save the project
            this.projectService.save(this.projectWithRelations);
            entityManager.flush();
            entityManager.clear();

            // Change the project
            String newTitle = "New Title";
            this.projectWithRelations.setTitle(newTitle);

            // Change title of first story:
            String newStoryTitle = "New Story Title";
            this.projectWithRelations.getStories().iterator().next().setTitle(newStoryTitle);

            // Change name of the first skill:
            String newSkillName = "New Skill Name";
            this.projectWithRelations.getSkills().iterator().next().setName(newSkillName);

            // Add a new Story:
            NewStory newStory = new NewStory();
            newStory.setDiary(NewDiary.builder().id(1).build());
            newStory.setTitle("New Story");
            newStory.setDescription("New Description");
            newStory.setFromDate(LocalDate.now());
            newStory.setToDate(LocalDate.now());
            newStory.setIsPrimaryStory(true);
            newStory.setFirstRelevantSection("New Section");
            newStory.setSecondRelevantSection("New Section");
            this.projectWithRelations.getStories().add(newStory);

            // Add a new existing Skill:
            NewSkill existingSkill = new NewSkill();
            existingSkill.setId(2);
            this.projectWithRelations.getSkills().add(existingSkill);

            // Update the project
            this.projectService.update(this.projectWithRelations);
            entityManager.flush();
            entityManager.clear();

            // Check if the project has been updated correctly:
            NewProject updatedProject = this.projectService.findById(this.projectWithRelations.getId());
            assertNotNull(updatedProject);
            assertEquals(this.projectWithRelations.getTitle(), updatedProject.getTitle());
            assertEquals(newTitle, updatedProject.getTitle());
            assertEquals(this.projectWithRelations.getStories().size(), updatedProject.getStories().size());
            assertEquals(this.projectWithRelations.getSkills().size(), updatedProject.getSkills().size());
            assertNotEquals(newStoryTitle, updatedProject.getStories().iterator().next().getTitle());
            assertNotEquals(newSkillName, updatedProject.getSkills().iterator().next().getName());


        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void delete() {
        try {
            // Save the project
            this.projectService.save(this.project);

            // Delete the project
            this.projectService.delete(this.project);

            // Clear the entity manager to force the next query to hit the database
            entityManager.flush();
            entityManager.clear();

            // Check if the project has been deleted correctly:
            NewProject deletedProject = this.projectService.findById(this.project.getId());
            assertNull(deletedProject);
        } catch (EntityNotFoundException e) {
            // The user has been deleted correctly
            assertTrue(true);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void deleteWithRelations() {
        try {
            // Save the project
            this.projectService.save(this.projectWithRelations);

            // Refresh this.projectWithRelations:
            entityManager.refresh(this.projectWithRelations);

            // Delete the project
            this.projectService.delete(this.projectWithRelations);

            // Clear the entity manager to force the next query to hit the database
            entityManager.flush();
            entityManager.clear();

            // Check if the project has been deleted correctly:
            try {
                NewProject deletedProject = this.projectService.findById(this.projectWithRelations.getId());
                assertNull(deletedProject);
            } catch (EntityNotFoundException e) {
                // The user has been deleted correctly
                assertTrue(true);
            }

            // Check if the relations have been deleted correctly:
            assertTrue(this.projectWithRelations.getStories()==null || this.projectWithRelations.getStories().isEmpty());
            assertTrue(this.projectWithRelations.getSkills()==null || this.projectWithRelations.getSkills().isEmpty());

        } catch (EntityNotFoundException e) {
            // The user has been deleted correctly
            assertTrue(true);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
