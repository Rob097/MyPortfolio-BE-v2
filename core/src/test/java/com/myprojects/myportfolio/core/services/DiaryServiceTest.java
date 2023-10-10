package com.myprojects.myportfolio.core.services;

import com.myprojects.myportfolio.core.BaseTest;
import com.myprojects.myportfolio.core.dao.NewDiary;
import com.myprojects.myportfolio.core.dao.NewStory;
import com.myprojects.myportfolio.core.dao.NewUser;
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
public class DiaryServiceTest extends BaseTest {

    @Autowired
    private DiaryServiceI diaryService;

    @Autowired
    private EntityManager entityManager;

    private NewDiary diary;
    private NewDiary diaryWithRelations;

    private NewDiary getNewDiary() {
        NewDiary diary = new NewDiary();
        diary.setUser(NewUser.builder().id(1).build());
        diary.setTitle("Test Diary");
        diary.setDescription("Test Diary Description");
        diary.setIsMain(true);
        return diary;
    }

    @BeforeEach
    void setUp() {
        this.diary = getNewDiary();
        this.diaryWithRelations = getNewDiary();

        // Create a new object NewStory and add it to the experience
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

        this.diaryWithRelations.setStories(stories);
    }

    @Test
    void save() {
        try {

            // Save the diary
            this.diaryService.save(this.diary);

            // After saving the diary, hibernate would use the first level cache to retrieve the diary from memory
            // instead of hitting the database again. So we need to clear the entity manager to force the next query
            // Clear the entity manager to force the next query to hit the database
            entityManager.flush();
            entityManager.clear();

            // check if the diary has been saved correctly:
            NewDiary createdDiary = diaryService.findById(this.diary.getId());
            assertNotNull(createdDiary);

            assertEquals(this.diary.getTitle(), createdDiary.getTitle());
            assertEquals(this.diary.getDescription(), createdDiary.getDescription());
            assertEquals(this.diary.getIsMain(), createdDiary.getIsMain());
            assertEquals(this.diary.getUser().getId(), createdDiary.getUser().getId());

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void saveWithRelation() {
        try {

            // Save the diary
            this.diaryService.save(this.diaryWithRelations);

            // After saving the diary, hibernate would use the first level cache to retrieve the diary from memory
            // instead of hitting the database again. So we need to clear the entity manager to force the next query
            // Clear the entity manager to force the next query to hit the database
            entityManager.flush();
            entityManager.clear();

            // check if the diary has been saved correctly:
            NewDiary createdDiary = diaryService.findById(this.diaryWithRelations.getId());
            assertNotNull(createdDiary);

            assertEquals(this.diaryWithRelations.getTitle(), createdDiary.getTitle());
            assertEquals(this.diaryWithRelations.getDescription(), createdDiary.getDescription());
            assertEquals(this.diaryWithRelations.getIsMain(), createdDiary.getIsMain());
            assertEquals(this.diaryWithRelations.getUser().getId(), createdDiary.getUser().getId());
            assertEquals(this.diaryWithRelations.getStories().size(), createdDiary.getStories().size());

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void update() {
        try {
            // Save the diary
            this.diaryService.save(this.diary);

            // Change the diary
            String newTitle = "New Title";
            this.diary.setTitle(newTitle);

            // Update the diary
            this.diaryService.update(this.diary);

            // Clear the entity manager to force the next query to hit the database
            entityManager.flush();
            entityManager.clear();

            // Check if the diary has been updated correctly:
            NewDiary updatedDiary = this.diaryService.findById(this.diary.getId());
            assertNotNull(updatedDiary);
            assertEquals(this.diary.getTitle(), updatedDiary.getTitle());
            assertEquals(newTitle, updatedDiary.getTitle());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void updateWithRelations() {
        try {

            // Save the diary
            this.diaryService.save(this.diaryWithRelations);
            entityManager.flush();
            entityManager.clear();

            // Change the diary
            String newTitle = "New Title";
            this.diaryWithRelations.setTitle(newTitle);

            // Change title of first story:
            String newStoryTitle = "New Story Title";
            this.diaryWithRelations.getStories().iterator().next().setTitle(newStoryTitle);

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
            this.diaryWithRelations.getStories().add(newStory);

            // Update the diary
            this.diaryService.update(this.diaryWithRelations);
            entityManager.flush();
            entityManager.clear();

            // Check if the diary has been updated correctly:
            NewDiary updatedDiary = this.diaryService.findById(this.diaryWithRelations.getId());
            assertNotNull(updatedDiary);
            assertEquals(this.diaryWithRelations.getTitle(), updatedDiary.getTitle());
            assertEquals(newTitle, updatedDiary.getTitle());
            assertEquals(this.diaryWithRelations.getStories().size(), updatedDiary.getStories().size());
            assertNotEquals(newStoryTitle, updatedDiary.getStories().iterator().next().getTitle());

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void delete() {
        try {
            // Save the diary
            this.diaryService.save(this.diary);

            // Delete the diary
            this.diaryService.delete(this.diary);

            // Clear the entity manager to force the next query to hit the database
            entityManager.flush();
            entityManager.clear();

            // Check if the diary has been deleted correctly:
            NewDiary deletedDiary = this.diaryService.findById(this.diary.getId());
            assertNull(deletedDiary);
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
            // Save the diary
            this.diaryService.save(this.diaryWithRelations);

            // Refresh this.diaryWithRelations:
            entityManager.refresh(this.diaryWithRelations);

            // Delete the diary
            this.diaryService.delete(this.diaryWithRelations);

            // Clear the entity manager to force the next query to hit the database
            entityManager.flush();
            entityManager.clear();

            // Check if the diary has been deleted correctly:
            try {
                NewDiary deletedDiary = this.diaryService.findById(this.diaryWithRelations.getId());
                assertNull(deletedDiary);
            } catch (EntityNotFoundException e) {
                // The user has been deleted correctly
                assertTrue(true);
            }

            // Check if the relations have been deleted correctly:
            assertTrue(this.diaryWithRelations.getStories() == null || this.diaryWithRelations.getStories().isEmpty());

        } catch (EntityNotFoundException e) {
            // The user has been deleted correctly
            assertTrue(true);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void removeStories() {
        try {

            // Save the diary
            this.diaryService.save(this.diary);
            this.diaryService.save(this.diaryWithRelations);

            // Remove the stories
            NewStory story = this.diaryWithRelations.getStories().iterator().next();
            this.diaryService.removeStoriesFromDiary(this.diaryWithRelations.getId(), new Integer[]{1});
            this.diary.getStories().add(story);
            story.setDiary(this.diary);
            this.diaryService.update(this.diary);

            // Clear the entity manager to force the next query to hit the database
            entityManager.flush();
            entityManager.clear();

            // Check if the stories have been deleted correctly:
            NewDiary updatedDiary = this.diaryService.findById(this.diaryWithRelations.getId());
            assertNotNull(updatedDiary);
            assertTrue(updatedDiary.getStories().isEmpty());

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
