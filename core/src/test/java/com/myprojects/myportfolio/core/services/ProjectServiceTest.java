package com.myprojects.myportfolio.core.services;

import com.myprojects.myportfolio.core.BaseTest;
import com.myprojects.myportfolio.core.dao.Diary;
import com.myprojects.myportfolio.core.dao.Project;
import com.myprojects.myportfolio.core.dao.Story;
import com.myprojects.myportfolio.core.dao.User;
import com.myprojects.myportfolio.core.dao.skills.Skill;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
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

    private Project project;
    private Project projectWithRelations;

    private Project getProject() {
        Project project = new Project();
        project.setTitle("Test Project");
        project.setDescription("Test Description");
        project.setUser(User.builder().id(1).build());
        return project;
    }

    @BeforeEach
    void setUp() {
        this.project = getProject();
        this.projectWithRelations = getProject();

        // Create a new object Story and add it to the project
        Set<Story> stories = new HashSet<>();
        Story story = new Story();
        story.setDiary(Diary.builder().id(1).build());
        story.setTitle("Test Story");
        story.setDescription("Test Description");
        story.setFromDate(LocalDate.now());
        story.setToDate(LocalDate.now());
        story.setFirstRelevantSection("Test Section");
        story.setSecondRelevantSection("Test Section");
        stories.add(story);

        Set<Skill> skills = new HashSet<>();
        Skill skill = new Skill();
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
            Project createdProject = projectService.findById(this.project.getId());
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
            Project createdProject = projectService.findById(this.projectWithRelations.getId());
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
            String newTitle = " Title";
            this.project.setTitle(newTitle);

            // Update the project
            this.projectService.update(this.project);

            // Clear the entity manager to force the next query to hit the database
            entityManager.flush();
            entityManager.clear();

            // Check if the project has been updated correctly:
            Project updatedProject = this.projectService.findById(this.project.getId());
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
            String newTitle = " Title";
            this.projectWithRelations.setTitle(newTitle);

            // Change title of first story:
            String newStoryTitle = " Story Title";
            this.projectWithRelations.getStories().iterator().next().setTitle(newStoryTitle);

            // Change name of the first skill:
            String newSkillName = " Skill Name";
            this.projectWithRelations.getSkills().iterator().next().setName(newSkillName);

            // Add a new Story:
            Story newStory = new Story();
            newStory.setDiary(Diary.builder().id(1).build());
            newStory.setTitle(" Story");
            newStory.setDescription(" Description");
            newStory.setFromDate(LocalDate.now());
            newStory.setToDate(LocalDate.now());
            newStory.setFirstRelevantSection(" Section");
            newStory.setSecondRelevantSection(" Section");
            this.projectWithRelations.getStories().add(newStory);

            // Add a new existing Skill:
            Skill existingSkill = new Skill();
            existingSkill.setId(2);
            this.projectWithRelations.getSkills().add(existingSkill);

            // Update the project
            this.projectService.update(this.projectWithRelations);
            entityManager.flush();
            entityManager.clear();

            // Check if the project has been updated correctly:
            Project updatedProject = this.projectService.findById(this.projectWithRelations.getId());
            assertNotNull(updatedProject);
            assertEquals(this.projectWithRelations.getTitle(), updatedProject.getTitle());
            assertEquals(newTitle, updatedProject.getTitle());
            assertEquals(this.projectWithRelations.getStories().size(), updatedProject.getStories().size());
            assertEquals(this.projectWithRelations.getSkills().size(), updatedProject.getSkills().size());
            assertEquals(newStoryTitle, updatedProject.getStories().iterator().next().getTitle());
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
            Project deletedProject = this.projectService.findById(this.project.getId());
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
                Project deletedProject = this.projectService.findById(this.projectWithRelations.getId());
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
