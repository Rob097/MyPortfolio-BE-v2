package com.myprojects.myportfolio.core.services;

import com.myprojects.myportfolio.core.BaseTest;
import com.myprojects.myportfolio.core.dao.*;
import com.myprojects.myportfolio.core.dao.enums.EmploymentTypeEnum;
import com.myprojects.myportfolio.core.dao.enums.EntitiesStatusEnum;
import com.myprojects.myportfolio.core.dao.skills.Skill;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.cache.CacheManager;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional // For the rollback
public class StoryServiceTest extends BaseTest {

    @Autowired
    private StoryServiceI storyService;

    @Autowired
    private ExperienceServiceI experienceService;

    @Autowired
    private EducationServiceI educationService;

    @Autowired
    private ProjectServiceI projectService;

    @Autowired
    private EntityManager entityManager;

    private Story story;
    private Story storyWithRelations;
    private final Integer userId = 25;

    private Story getStory() {
        Story newStory = new Story();
        newStory.setDiary(Diary.builder().id(5).build());
        newStory.setTitle("Title");
        newStory.setDescription("Description");
        newStory.setFromDate(LocalDate.now());
        newStory.setToDate(LocalDate.now());
        newStory.setStatus(EntitiesStatusEnum.DRAFT);

        RelevantSection relevantSection = new RelevantSection();
        relevantSection.setTitle("Title");
        relevantSection.setDescription("Description");

        newStory.setRelevantSections(Set.of(relevantSection));

        return newStory;
    }

    @BeforeEach
    void setUp() {
        this.story = getStory();
        this.storyWithRelations = getStory();

        Project project = new Project();
        project.setUser(User.builder().id(userId).build());
        project.setTitle("Test Title");
        project.setDescription("Test Description");
        project.setFromDate(LocalDate.now());
        project.setStatus(EntitiesStatusEnum.DRAFT);
        projectService.save(project);

        Education education = new Education();
        education.setUser(User.builder().id(userId).build());
        education.setField("Test Field");
        education.setSchool("Test School");
        education.setDegree("Test Degree");
        education.setDescription("Test Description");
        education.setFromDate(LocalDate.now());
        education.setStatus(EntitiesStatusEnum.DRAFT);
        educationService.save(education);

        Experience experience = new Experience();
        experience.setUser(User.builder().id(userId).build());
        experience.setTitle("Test Title");
        experience.setCompanyName("Test Company");
        experience.setDescription("Test Description");
        experience.setEmploymentType(EmploymentTypeEnum.FREELANCE);
        experience.setFromDate(LocalDate.now());
        experience.setStatus(EntitiesStatusEnum.DRAFT);
        experienceService.save(experience);

        Set<Skill> skills = new HashSet<>();
        /*Skill newSkill = new Skill();
        newSkill.setCategory(SkillCategory.builder().id(1).build());
        newSkill.setName("Test Skill");
        skills.add(newSkill);*/
        Skill existingSkill = Skill.builder().id(1).build();
        skills.add(existingSkill);

        this.storyWithRelations.setProject(project);
        this.storyWithRelations.setEducation(education);
        this.storyWithRelations.setExperience(experience);
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
            Story createdStory = this.storyService.findById(this.story.getId());
            assertNotNull(createdStory);

            // Check if the story fields are correct
            assertEquals(this.story.getTitle(), createdStory.getTitle());
            assertEquals(this.story.getDescription(), createdStory.getDescription());
            assertEquals(this.story.getFromDate(), createdStory.getFromDate());
            assertEquals(this.story.getToDate(), createdStory.getToDate());
            assertEquals(this.story.getRelevantSections(), createdStory.getRelevantSections());

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
            Story createdStory = this.storyService.findById(this.storyWithRelations.getId());
            assertNotNull(createdStory);

            // Check if the story fields are correct
            assertEquals(this.storyWithRelations.getTitle(), createdStory.getTitle());
            assertEquals(this.storyWithRelations.getDescription(), createdStory.getDescription());
            assertEquals(this.storyWithRelations.getFromDate(), createdStory.getFromDate());
            assertEquals(this.storyWithRelations.getToDate(), createdStory.getToDate());
            assertEquals(this.storyWithRelations.getRelevantSections(), createdStory.getRelevantSections());
            assertEquals(this.storyWithRelations.getProject(), createdStory.getProject());
            assertEquals(this.storyWithRelations.getEducation(), createdStory.getEducation());
            assertEquals(this.storyWithRelations.getExperience(), createdStory.getExperience());
            assertEquals(this.storyWithRelations.getSkills().size(), createdStory.getSkills().size());

            Experience experience = this.experienceService.findById(this.storyWithRelations.getExperience().getId());
            assertNotNull(experience, "Experience is null");
            Story experienceStory = experience.getStories().stream().filter(story -> Objects.equals(story.getId(), this.storyWithRelations.getId())).findFirst().orElse(null);
            assertNotNull(experienceStory, "Experience story is null");

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
            String newTitle = " Title";
            this.story.setTitle(newTitle);

            // Update the story
            this.storyService.update(this.story);

            // Clear the entity manager to force the next query to hit the database
            entityManager.flush();
            entityManager.clear();

            // Check if the story has been updated correctly:
            Story updatedStory = this.storyService.findById(this.story.getId());
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
            String newTitle = " Title";
            this.storyWithRelations.setTitle(newTitle);
            this.storyWithRelations.setDiary(Diary.builder().id(2).build());
            this.storyWithRelations.setProject(Project.builder().id(2).build());
            this.storyWithRelations.setEducation(Education.builder().id(2).build());
            this.storyWithRelations.setExperience(Experience.builder().id(2).build());
            this.storyWithRelations.getSkills().add(Skill.builder().id(2).build());

            // Update the story
            this.storyService.update(this.storyWithRelations);

            // Clear the entity manager to force the next query to hit the database
            entityManager.flush();
            entityManager.clear();

            // Check if the story has been updated correctly:
            Story updatedStory = this.storyService.findById(this.storyWithRelations.getId());
            assertNotNull(updatedStory);
            assertEquals(this.storyWithRelations.getTitle(), updatedStory.getTitle());
            assertEquals(newTitle, updatedStory.getTitle());
            assertEquals(this.storyWithRelations.getDiary().getId(), updatedStory.getDiary().getId());
            assertEquals(this.storyWithRelations.getProject().getId(), updatedStory.getProject().getId());
            assertEquals(this.storyWithRelations.getEducation().getId(), updatedStory.getEducation().getId());
            assertEquals(this.storyWithRelations.getExperience().getId(), updatedStory.getExperience().getId());
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
            Story deletedStory = this.storyService.findById(this.story.getId());
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
                Story deletedStory = this.storyService.findById(this.storyWithRelations.getId());
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
