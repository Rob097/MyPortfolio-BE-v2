package com.myprojects.myportfolio.core.services;

import com.myprojects.myportfolio.core.BaseTest;
import com.myprojects.myportfolio.core.dao.*;
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
public class EducationServiceTest extends BaseTest {

    @Autowired
    private EducationService educationService;

    @Autowired
    private EntityManager entityManager;

    private Education education;
    private Education educationWithRelations;

    private Education getEducation() {
        Education education = new Education();
        education.setField("Test Field");
        education.setDegree("Test Degree");
        education.setSchool("Test School");
        education.setFromDate(LocalDate.now());
        education.setToDate(LocalDate.now());
        education.setGrade(105d);
        education.setDescription("Test Description");
        education.setUser(User.builder().id(1).build());
        return education;
    }

    @BeforeEach
    void setUp() {
        this.education = getEducation();
        this.educationWithRelations = getEducation();

        // Create a new object Story and add it to the education
        Set<Story> stories = new HashSet<>();
        Story story = new Story();
        story.setDiary(Diary.builder().id(1).build());
        story.setTitle("Test Story");
        story.setDescription("Test Description");
        story.setFromDate(LocalDate.now());
        story.setToDate(LocalDate.now());

        RelevantSection relevantSection = new RelevantSection();
        relevantSection.setTitle("Title");
        relevantSection.setDescription("Description");
        story.setRelevantSections(Set.of(relevantSection));

        stories.add(story);

        Set<Skill> skills = new HashSet<>();
        Skill skill = new Skill();
        skill.setId(1);
        skills.add(skill);

        educationWithRelations.setStories(stories);
        educationWithRelations.setSkills(skills);

    }

    @Test
    void save() {
        try {

            // Save the education
            this.educationService.save(this.education);

            // After saving the education, hibernate would use the first level cache to retrieve the education from memory
            // instead of hitting the database again. So we need to clear the entity manager to force the next query
            // Clear the entity manager to force the next query to hit the database
            entityManager.flush();
            entityManager.clear();

            // check if the education has been saved correctly:
            Education createdEducation = educationService.findById(this.education.getId());
            assertNotNull(createdEducation);

            assertEquals(this.education.getField(), createdEducation.getField());
            assertEquals(this.education.getGrade(), createdEducation.getGrade());
            assertEquals(this.education.getDegree(), createdEducation.getDegree());
            assertEquals(this.education.getSchool(), createdEducation.getSchool());
            assertEquals(this.education.getFromDate(), createdEducation.getFromDate());
            assertEquals(this.education.getToDate(), createdEducation.getToDate());
            assertEquals(this.education.getDescription(), createdEducation.getDescription());
            assertEquals(this.education.getUser().getId(), createdEducation.getUser().getId());

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void saveWithRelation() {
        try {

            // Save the education
            this.educationService.save(this.educationWithRelations);

            // After saving the education, hibernate would use the first level cache to retrieve the education from memory
            // instead of hitting the database again. So we need to clear the entity manager to force the next query
            // Clear the entity manager to force the next query to hit the database
            entityManager.flush();
            entityManager.clear();

            // check if the education has been saved correctly:
            Education createdEducation = educationService.findById(this.educationWithRelations.getId());
            assertNotNull(createdEducation);

            assertEquals(this.educationWithRelations.getField(), createdEducation.getField());
            assertEquals(this.educationWithRelations.getGrade(), createdEducation.getGrade());
            assertEquals(this.educationWithRelations.getDegree(), createdEducation.getDegree());
            assertEquals(this.educationWithRelations.getSchool(), createdEducation.getSchool());
            assertEquals(this.educationWithRelations.getFromDate(), createdEducation.getFromDate());
            assertEquals(this.educationWithRelations.getToDate(), createdEducation.getToDate());
            assertEquals(this.educationWithRelations.getDescription(), createdEducation.getDescription());
            assertEquals(this.educationWithRelations.getUser().getId(), createdEducation.getUser().getId());
            assertEquals(this.educationWithRelations.getStories().size(), createdEducation.getStories().size());
            assertEquals(this.educationWithRelations.getSkills().size(), createdEducation.getSkills().size());

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void update() {
        try {
            // Save the education
            this.educationService.save(this.education);

            // Change the education
            String newSchool = " School";
            this.education.setSchool(newSchool);

            // Update the education
            this.educationService.update(this.education);

            // Clear the entity manager to force the next query to hit the database
            entityManager.flush();
            entityManager.clear();

            // Check if the education has been updated correctly:
            Education updatedEducation = this.educationService.findById(this.education.getId());
            assertNotNull(updatedEducation);
            assertEquals(this.education.getSchool(), updatedEducation.getSchool());
            assertEquals(newSchool, updatedEducation.getSchool());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void updateWithRelations() {
        try {

            // Save the education
            this.educationService.save(this.educationWithRelations);
            entityManager.flush();
            entityManager.clear();

            // Change the education
            String newSchool = " School";
            this.educationWithRelations.setSchool(newSchool);

            // Change title of first story:
            String newStoryTitle = " Story Title";
            this.educationWithRelations.getStories().iterator().next().setTitle(newStoryTitle);

            // Change name of the first skill:
            String newSkillName = " Skill Name";
            this.educationWithRelations.getSkills().iterator().next().setName(newSkillName);

            // Add a new Story:
            Story newStory = new Story();
            newStory.setDiary(Diary.builder().id(1).build());
            newStory.setTitle(" Story");
            newStory.setDescription(" Description");
            newStory.setFromDate(LocalDate.now());
            newStory.setToDate(LocalDate.now());

            RelevantSection relevantSection = new RelevantSection();
            relevantSection.setTitle("Title");
            relevantSection.setDescription("Description");
            newStory.setRelevantSections(Set.of(relevantSection));

            this.educationWithRelations.getStories().add(newStory);

            // Add a new existing Skill:
            Skill existingSkill = new Skill();
            existingSkill.setId(2);
            this.educationWithRelations.getSkills().add(existingSkill);

            // Update the education
            this.educationService.update(this.educationWithRelations);
            entityManager.flush();
            entityManager.clear();

            // Check if the education has been updated correctly:
            Education updatedEducation = this.educationService.findById(this.educationWithRelations.getId());
            assertNotNull(updatedEducation);
            assertEquals(this.educationWithRelations.getSchool(), updatedEducation.getSchool());
            assertEquals(newSchool, updatedEducation.getSchool());
            assertEquals(this.educationWithRelations.getStories().size(), updatedEducation.getStories().size());
            assertEquals(this.educationWithRelations.getSkills().size(), updatedEducation.getSkills().size());
            assertEquals(newStoryTitle, updatedEducation.getStories().iterator().next().getTitle());
            assertNotEquals(newSkillName, updatedEducation.getSkills().iterator().next().getName());


        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void delete() {
        try {
            // Save the education
            this.educationService.save(this.education);

            // Delete the education
            this.educationService.delete(this.education);

            // Clear the entity manager to force the next query to hit the database
            entityManager.flush();
            entityManager.clear();

            // Check if the education has been deleted correctly:
            Education deletedEducation = this.educationService.findById(this.education.getId());
            assertNull(deletedEducation);
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
            // Save the education
            this.educationService.save(this.educationWithRelations);

            // Refresh this.educationWithRelations:
            entityManager.refresh(this.educationWithRelations);

            // Delete the education
            this.educationService.delete(this.educationWithRelations);

            // Clear the entity manager to force the next query to hit the database
            entityManager.flush();
            entityManager.clear();

            // Check if the education has been deleted correctly:
            try {
                Education deletedEducation = this.educationService.findById(this.educationWithRelations.getId());
                assertNull(deletedEducation);
            } catch (EntityNotFoundException e) {
                // The user has been deleted correctly
                assertTrue(true);
            }

            // Check if the relations have been deleted correctly:
            assertTrue(this.educationWithRelations.getStories() == null || this.educationWithRelations.getStories().isEmpty());
            assertTrue(this.educationWithRelations.getSkills() == null || this.educationWithRelations.getSkills().isEmpty());

        } catch (EntityNotFoundException e) {
            // The user has been deleted correctly
            assertTrue(true);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}