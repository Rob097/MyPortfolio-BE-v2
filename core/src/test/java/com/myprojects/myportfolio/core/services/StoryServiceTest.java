package com.myprojects.myportfolio.core.services;

import com.myprojects.myportfolio.core.BaseTest;
import com.myprojects.myportfolio.core.newDataModel.dao.*;
import com.myprojects.myportfolio.core.newDataModel.dao.skills.NewSkill;
import com.myprojects.myportfolio.core.newDataModel.services.StoryServiceI;
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
public class StoryServiceTest extends BaseTest {

    @Autowired
    private StoryServiceI storyService;

    @Autowired
    private EntityManager entityManager;

    private NewStory story;
    private NewStory storyWithRelations;

    private NewStory getNewStory() {
        NewStory newStory = new NewStory();
        newStory.setDiary(NewDiary.builder().id(1).build());
        newStory.setTitle("Title");
        newStory.setDescription("Description");
        newStory.setFromDate(LocalDate.now());
        newStory.setToDate(LocalDate.now());
        newStory.setIsPrimaryStory(true);
        newStory.setFirstRelevantSection("First Relevant Section");
        newStory.setSecondRelevantSection("Second Relevant Section");
        return newStory;
    }

    @BeforeEach
    void setUp() {
        this.story = getNewStory();
        this.storyWithRelations = getNewStory();

        Set<NewProject> projects = new HashSet<>();
        /*NewProject project = new NewProject();
        project.setTitle("Test Project");
        project.setDescription("Test Description");*/
        projects.add(NewProject.builder().id(1).build());

        Set<NewEducation> educations = new HashSet<>();
        /*NewEducation education = new NewEducation();
        education.setField("Test Field");
        education.setSchool("Test School");
        education.setDegree("Test Degree");
        education.setDescription("Test Description");*/
        educations.add(NewEducation.builder().id(1).build());

        Set<NewExperience> experiences = new HashSet<>();
        /*NewExperience experience = new NewExperience();
        experience.setTitle("Test Title");
        experience.setCompanyName("Test Company");
        experience.setDescription("Test Description");
        experience.setEmploymentType(EmploymentTypeEnum.FREELANCE);*/
        experiences.add(NewExperience.builder().id(1).build());

        Set<NewSkill> skills = new HashSet<>();
        /*NewSkill newSkill = new NewSkill();
        newSkill.setCategory(NewSkillCategory.builder().id(1).build());
        newSkill.setName("Test Skill");
        skills.add(newSkill);*/
        NewSkill existingSkill = NewSkill.builder().id(1).build();
        skills.add(existingSkill);

        this.storyWithRelations.setProjects(projects);
        this.storyWithRelations.setEducations(educations);
        this.storyWithRelations.setExperiences(experiences);
        this.storyWithRelations.setSkills(skills);
    }

    @Test
    void save() {
        try {
            // Save the story
            this.storyService.save(this.story);

            // After saving the story, hibernate would use the first level cache to retrieve the story from memory
            // instead of hitting the database again. So we need to clear the entity manager to force the next query
            // Clear the entity manager to force the next query to hit the database
            entityManager.flush();
            entityManager.clear();

            // Check if the story has been saved correctly:
            NewStory createdStory = this.storyService.findById(this.story.getId());
            assertNotNull(createdStory);

            // Check if the story fields are correct
            assertEquals(this.story.getTitle(), createdStory.getTitle());
            assertEquals(this.story.getDescription(), createdStory.getDescription());
            assertEquals(this.story.getFromDate(), createdStory.getFromDate());
            assertEquals(this.story.getToDate(), createdStory.getToDate());
            assertEquals(this.story.getIsPrimaryStory(), createdStory.getIsPrimaryStory());
            assertEquals(this.story.getFirstRelevantSection(), createdStory.getFirstRelevantSection());
            assertEquals(this.story.getSecondRelevantSection(), createdStory.getSecondRelevantSection());

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void saveWithRelations() {
        try {
            // Save the story
            this.storyService.save(this.storyWithRelations);

            // After saving the story, hibernate would use the first level cache to retrieve the story from memory
            // instead of hitting the database again. So we need to clear the entity manager to force the next query
            // Clear the entity manager to force the next query to hit the database
            entityManager.flush();
            entityManager.clear();

            // Check if the story has been saved correctly:
            NewStory createdStory = this.storyService.findById(this.storyWithRelations.getId());
            assertNotNull(createdStory);

            // Check if the story fields are correct
            assertEquals(this.storyWithRelations.getTitle(), createdStory.getTitle());
            assertEquals(this.storyWithRelations.getDescription(), createdStory.getDescription());
            assertEquals(this.storyWithRelations.getFromDate(), createdStory.getFromDate());
            assertEquals(this.storyWithRelations.getToDate(), createdStory.getToDate());
            assertEquals(this.storyWithRelations.getIsPrimaryStory(), createdStory.getIsPrimaryStory());
            assertEquals(this.storyWithRelations.getFirstRelevantSection(), createdStory.getFirstRelevantSection());
            assertEquals(this.storyWithRelations.getSecondRelevantSection(), createdStory.getSecondRelevantSection());
            assertEquals(this.storyWithRelations.getProjects().size(), createdStory.getProjects().size());
            assertEquals(this.storyWithRelations.getEducations().size(), createdStory.getEducations().size());
            assertEquals(this.storyWithRelations.getExperiences().size(), createdStory.getExperiences().size());
            assertEquals(this.storyWithRelations.getSkills().size(), createdStory.getSkills().size());

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void update() {
        try {

            // Save the story
            this.storyService.save(this.story);

            // Change the story
            String newTitle = "New Title";
            this.story.setTitle(newTitle);

            // Update the story
            this.storyService.update(this.story);

            // Clear the entity manager to force the next query to hit the database
            entityManager.flush();
            entityManager.clear();

            // Check if the story has been updated correctly:
            NewStory updatedStory = this.storyService.findById(this.story.getId());
            assertNotNull(updatedStory);
            assertEquals(this.story.getTitle(), updatedStory.getTitle());
            assertEquals(newTitle, updatedStory.getTitle());

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void updateWithRelations() {
        try {

            // Save the story
            this.storyService.save(this.storyWithRelations);

            // Change the story
            String newTitle = "New Title";
            this.storyWithRelations.setTitle(newTitle);
            this.storyWithRelations.setDiary(NewDiary.builder().id(2).build());
            this.storyWithRelations.getProjects().add(NewProject.builder().id(2).build());
            this.storyWithRelations.getEducations().add(NewEducation.builder().id(2).build());
            this.storyWithRelations.getExperiences().add(NewExperience.builder().id(2).build());
            this.storyWithRelations.getSkills().add(NewSkill.builder().id(2).build());

            // Update the story
            this.storyService.update(this.storyWithRelations);

            // Clear the entity manager to force the next query to hit the database
            entityManager.flush();
            entityManager.clear();

            // Check if the story has been updated correctly:
            NewStory updatedStory = this.storyService.findById(this.storyWithRelations.getId());
            assertNotNull(updatedStory);
            assertEquals(this.storyWithRelations.getTitle(), updatedStory.getTitle());
            assertEquals(newTitle, updatedStory.getTitle());
            assertEquals(this.storyWithRelations.getDiary().getId(), updatedStory.getDiary().getId());
            assertEquals(1, updatedStory.getProjects().size());
            assertEquals(1, updatedStory.getEducations().size());
            assertEquals(1, updatedStory.getExperiences().size());
            assertEquals(this.storyWithRelations.getSkills().size(), updatedStory.getSkills().size());

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void delete() {

        try {
            // Save the story
            this.storyService.save(this.story);

            // Delete the story
            this.storyService.delete(this.story);

            // Flush the entity manager to force the next query to hit the database
            entityManager.flush();
            entityManager.clear();

            // Check if the story has been deleted correctly:
            NewStory deletedStory = this.storyService.findById(this.story.getId());
            assertNull(deletedStory);
        } catch (EntityNotFoundException e) {
            // The story has been deleted correctly
            assertTrue(true);
        } catch (Exception e) {
            fail(e.getMessage());
        }

    }

    @Test
    void deleteWithRelations() {

        try {
            // Save the story with relations
            storyService.save(this.storyWithRelations);

            // Flush the entity manager to force the next query to hit the database
            entityManager.flush();

            // Refresh this.storyWithRelations:
            entityManager.refresh(this.storyWithRelations);

            // Delete the story
            this.storyService.delete(this.storyWithRelations);

            // Flush the entity manager to force the next query to hit the database
            entityManager.flush();
            entityManager.clear();

            // Check if the story has been deleted correctly:
            try {
                NewStory deletedStory = this.storyService.findById(this.storyWithRelations.getId());
                assertNull(deletedStory);
            } catch (EntityNotFoundException e) {
                // The story has been deleted correctly
                assertTrue(true);
            }

        } catch (Exception e) {
            fail(e.getMessage());
        }

    }
}
