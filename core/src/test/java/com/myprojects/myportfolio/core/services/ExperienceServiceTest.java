package com.myprojects.myportfolio.core.services;

import com.myprojects.myportfolio.core.BaseTest;
import com.myprojects.myportfolio.core.dao.Diary;
import com.myprojects.myportfolio.core.dao.Experience;
import com.myprojects.myportfolio.core.dao.Story;
import com.myprojects.myportfolio.core.dao.User;
import com.myprojects.myportfolio.core.dao.enums.EmploymentTypeEnum;
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
public class ExperienceServiceTest extends BaseTest {

    @Autowired
    private ExperienceService experienceService;

    @Autowired
    private EntityManager entityManager;

    private Experience experience;
    private Experience experienceWithRelations;

    private Experience getExperience() {
        Experience experience = new Experience();
        experience.setEmploymentType(EmploymentTypeEnum.FREELANCE);
        experience.setCompanyName("Test Company");
        experience.setTitle("Test Title");
        experience.setLocation("Test Location");
        experience.setFromDate(LocalDate.now());
        experience.setToDate(LocalDate.now());
        experience.setDescription("Test Description");
        experience.setUser(User.builder().id(1).build());
        return experience;
    }

    @BeforeEach
    void setUp() {
        this.experience = getExperience();
        this.experienceWithRelations = getExperience();

        // Create a new object Story and add it to the experience
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

        experienceWithRelations.setStories(stories);
        experienceWithRelations.setSkills(skills);

    }

    @Test
    void save() {
        try {

            // Save the experience
            this.experienceService.save(this.experience);

            // After saving the experience, hibernate would use the first level cache to retrieve the experience from memory
            // instead of hitting the database again. So we need to clear the entity manager to force the next query
            // Clear the entity manager to force the next query to hit the database
            entityManager.flush();
            entityManager.clear();

            // check if the experience has been saved correctly:
            Experience createdExperience = experienceService.findById(this.experience.getId());
            assertNotNull(createdExperience);

            assertEquals(this.experience.getTitle(), createdExperience.getTitle());
            assertEquals(this.experience.getLocation(), createdExperience.getLocation());
            assertEquals(this.experience.getCompanyName(), createdExperience.getCompanyName());
            assertEquals(this.experience.getEmploymentType(), createdExperience.getEmploymentType());
            assertEquals(this.experience.getFromDate(), createdExperience.getFromDate());
            assertEquals(this.experience.getToDate(), createdExperience.getToDate());
            assertEquals(this.experience.getDescription(), createdExperience.getDescription());
            assertEquals(this.experience.getUser().getId(), createdExperience.getUser().getId());

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void saveWithRelation() {
        try {

            // Save the experience
            this.experienceService.save(this.experienceWithRelations);

            // After saving the experience, hibernate would use the first level cache to retrieve the experience from memory
            // instead of hitting the database again. So we need to clear the entity manager to force the next query
            // Clear the entity manager to force the next query to hit the database
            entityManager.flush();
            entityManager.clear();

            // check if the experience has been saved correctly:
            Experience createdExperience = experienceService.findById(this.experienceWithRelations.getId());
            assertNotNull(createdExperience);

            assertEquals(this.experienceWithRelations.getTitle(), createdExperience.getTitle());
            assertEquals(this.experienceWithRelations.getLocation(), createdExperience.getLocation());
            assertEquals(this.experienceWithRelations.getCompanyName(), createdExperience.getCompanyName());
            assertEquals(this.experienceWithRelations.getEmploymentType(), createdExperience.getEmploymentType());
            assertEquals(this.experienceWithRelations.getFromDate(), createdExperience.getFromDate());
            assertEquals(this.experienceWithRelations.getToDate(), createdExperience.getToDate());
            assertEquals(this.experienceWithRelations.getDescription(), createdExperience.getDescription());
            assertEquals(this.experienceWithRelations.getUser().getId(), createdExperience.getUser().getId());
            assertEquals(this.experienceWithRelations.getStories().size(), createdExperience.getStories().size());
            assertEquals(this.experienceWithRelations.getSkills().size(), createdExperience.getSkills().size());

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void update() {
        try {
            // Save the experience
            this.experienceService.save(this.experience);

            // Change the experience
            String newTitle = " Title";
            this.experience.setTitle(newTitle);

            // Update the experience
            this.experienceService.update(this.experience);

            // Clear the entity manager to force the next query to hit the database
            entityManager.flush();
            entityManager.clear();

            // Check if the experience has been updated correctly:
            Experience updatedExperience = this.experienceService.findById(this.experience.getId());
            assertNotNull(updatedExperience);
            assertEquals(this.experience.getTitle(), updatedExperience.getTitle());
            assertEquals(newTitle, updatedExperience.getTitle());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void updateWithRelations() {
        try {

            // Save the experience
            this.experienceService.save(this.experienceWithRelations);
            entityManager.flush();
            entityManager.clear();

            // Change the experience
            String newTitle = " Title";
            this.experienceWithRelations.setTitle(newTitle);

            // Change title of first story:
            String newStoryTitle = " Story Title";
            this.experienceWithRelations.getStories().iterator().next().setTitle(newStoryTitle);

            // Change name of the first skill:
            String newSkillName = " Skill Name";
            this.experienceWithRelations.getSkills().iterator().next().setName(newSkillName);

            // Add a new Story:
            Story newStory = new Story();
            newStory.setDiary(Diary.builder().id(1).build());
            newStory.setTitle(" Story");
            newStory.setDescription(" Description");
            newStory.setFromDate(LocalDate.now());
            newStory.setToDate(LocalDate.now());
            newStory.setFirstRelevantSection(" Section");
            newStory.setSecondRelevantSection(" Section");
            this.experienceWithRelations.getStories().add(newStory);

            // Add a new existing Skill:
            Skill existingSkill = new Skill();
            existingSkill.setId(2);
            this.experienceWithRelations.getSkills().add(existingSkill);

            // Update the experience
            this.experienceService.update(this.experienceWithRelations);
            entityManager.flush();
            entityManager.clear();

            // Check if the experience has been updated correctly:
            Experience updatedExperience = this.experienceService.findById(this.experienceWithRelations.getId());
            assertNotNull(updatedExperience);
            assertEquals(this.experienceWithRelations.getTitle(), updatedExperience.getTitle());
            assertEquals(newTitle, updatedExperience.getTitle());
            assertEquals(this.experienceWithRelations.getStories().size(), updatedExperience.getStories().size());
            assertEquals(this.experienceWithRelations.getSkills().size(), updatedExperience.getSkills().size());
            assertEquals(newStoryTitle, updatedExperience.getStories().iterator().next().getTitle());
            assertNotEquals(newSkillName, updatedExperience.getSkills().iterator().next().getName());


        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void delete() {
        try {
            // Save the experience
            this.experienceService.save(this.experience);

            // Delete the experience
            this.experienceService.delete(this.experience);

            // Clear the entity manager to force the next query to hit the database
            entityManager.flush();
            entityManager.clear();

            // Check if the experience has been deleted correctly:
            Experience deletedExperience = this.experienceService.findById(this.experience.getId());
            assertNull(deletedExperience);
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
            // Save the experience
            this.experienceService.save(this.experienceWithRelations);

            // Refresh this.experienceWithRelations:
            entityManager.refresh(this.experienceWithRelations);

            // Delete the experience
            this.experienceService.delete(this.experienceWithRelations);

            // Clear the entity manager to force the next query to hit the database
            entityManager.flush();
            entityManager.clear();

            // Check if the experience has been deleted correctly:
            try {
                Experience deletedExperience = this.experienceService.findById(this.experienceWithRelations.getId());
                assertNull(deletedExperience);
            } catch (EntityNotFoundException e) {
                // The user has been deleted correctly
                assertTrue(true);
            }

            // Check if the relations have been deleted correctly:
            assertTrue(this.experienceWithRelations.getStories() == null || this.experienceWithRelations.getStories().isEmpty());
            assertTrue(this.experienceWithRelations.getSkills() == null || this.experienceWithRelations.getSkills().isEmpty());

        } catch (EntityNotFoundException e) {
            // The user has been deleted correctly
            assertTrue(true);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}