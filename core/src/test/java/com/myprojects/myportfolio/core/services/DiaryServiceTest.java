package com.myprojects.myportfolio.core.services;

import com.myprojects.myportfolio.core.BaseTest;
import com.myprojects.myportfolio.core.dao.Diary;
import com.myprojects.myportfolio.core.dao.RelevantSection;
import com.myprojects.myportfolio.core.dao.Story;
import com.myprojects.myportfolio.core.dao.User;
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
public class DiaryServiceTest extends BaseTest {

    @Autowired
    private DiaryServiceI diaryService;

    @Autowired
    private EntityManager entityManager;

    private Diary diary;
    private Diary diaryWithRelations;

    private Diary getDiary() {
        Diary diary = new Diary();
        diary.setUser(User.builder().id(1).build());
        diary.setTitle("Test Diary");
        diary.setDescription("Test Diary Description");
        diary.setIsMain(true);
        return diary;
    }

    @BeforeEach
    void setUp() {
        this.diary = getDiary();
        this.diaryWithRelations = getDiary();

        // Create a new object Story and add it to the experience
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
            Diary createdDiary = diaryService.findById(this.diary.getId());
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
            Diary createdDiary = diaryService.findById(this.diaryWithRelations.getId());
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
            String newTitle = " Title";
            this.diary.setTitle(newTitle);

            // Update the diary
            this.diaryService.update(this.diary);

            // Clear the entity manager to force the next query to hit the database
            entityManager.flush();
            entityManager.clear();

            // Check if the diary has been updated correctly:
            Diary updatedDiary = this.diaryService.findById(this.diary.getId());
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
            String newTitle = " Title";
            this.diaryWithRelations.setTitle(newTitle);

            // Change title of first story:
            String newStoryTitle = " Story Title";
            this.diaryWithRelations.getStories().iterator().next().setTitle(newStoryTitle);

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

            this.diaryWithRelations.getStories().add(newStory);

            // Update the diary
            this.diaryService.update(this.diaryWithRelations);
            entityManager.flush();
            entityManager.clear();

            // Check if the diary has been updated correctly:
            Diary updatedDiary = this.diaryService.findById(this.diaryWithRelations.getId());
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
            Diary deletedDiary = this.diaryService.findById(this.diary.getId());
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
                Diary deletedDiary = this.diaryService.findById(this.diaryWithRelations.getId());
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
            Story story = this.diaryWithRelations.getStories().iterator().next();
            this.diaryService.removeStoriesFromEntity(this.diaryWithRelations.getId(), new Integer[]{1});
            this.diary.getStories().add(story);
            story.setDiary(this.diary);
            this.diaryService.update(this.diary);

            // Clear the entity manager to force the next query to hit the database
            entityManager.flush();
            entityManager.clear();

            // Check if the stories have been deleted correctly:
            Diary updatedDiary = this.diaryService.findById(this.diaryWithRelations.getId());
            assertNotNull(updatedDiary);
            assertTrue(updatedDiary.getStories().isEmpty());

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
