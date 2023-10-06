package com.myprojects.myportfolio.core.services;

import com.myprojects.myportfolio.core.BaseTest;
import com.myprojects.myportfolio.core.newDataModel.dao.*;
import com.myprojects.myportfolio.core.newDataModel.dao.enums.EmploymentTypeEnum;
import com.myprojects.myportfolio.core.newDataModel.dao.enums.Sex;
import com.myprojects.myportfolio.core.newDataModel.dao.skills.NewUserSkill;
import com.myprojects.myportfolio.core.newDataModel.services.UserServiceI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional // For the rollback
class UserServiceTest extends BaseTest {

    @Autowired
    private UserServiceI userService;

    @Autowired
    private EntityManager entityManager;

    private NewUser user;
    private NewUser userWithRelations;

    private static NewUser getNewUser() {
        NewUser newUser = new NewUser();
        newUser.setFirstName("John");
        newUser.setLastName("Doe");
        newUser.setEmail("john.doe7@fake.com");
        newUser.setPhone("+391111111111");
        newUser.setAge(26);
        newUser.setSex(Sex.FEMALE);
        newUser.setTitle("Test Title");
        newUser.setDescription("Test Description");
        newUser.setAddress("Test Address");
        newUser.setCity("Test City");
        newUser.setNationality("Italian");
        newUser.setNation("Italy");
        newUser.setProvince("Test Province");
        newUser.setCap("11111");
        return newUser;
    }

    @BeforeEach
    void setUp() {
        this.user = getNewUser();
        this.userWithRelations = getNewUser();

        Set<NewStory> stories = new HashSet<>();
        NewStory story = new NewStory();
        story.setTitle("Test Story");
        story.setDescription("Test Description");
        story.setIsPrimaryStory(true);
        stories.add(story);

        Set<NewDiary> diaries = new HashSet<>();
        NewDiary diary = new NewDiary();
        diary.setTitle("Test Diary");
        diary.setDescription("Test Description");
        diary.setIsMain(true);
        diary.setStories(stories);
        diaries.add(diary);

        Set<NewProject> projects = new HashSet<>();
        NewProject project = new NewProject();
        project.setTitle("Test Project");
        project.setDescription("Test Description");
        projects.add(project);

        Set<NewEducation> educations = new HashSet<>();
        NewEducation education = new NewEducation();
        education.setField("Test Field");
        education.setSchool("Test School");
        education.setDegree("Test Degree");
        education.setDescription("Test Description");
        educations.add(education);

        Set<NewExperience> experiences = new HashSet<>();
        NewExperience experience = new NewExperience();
        experience.setTitle("Test Title");
        experience.setCompanyName("Test Company");
        experience.setDescription("Test Description");
        experience.setEmploymentType(EmploymentTypeEnum.FREELANCE);
        experiences.add(experience);

        Set<NewUserSkill> skills = new HashSet<>();
        NewUserSkill userSkill = new NewUserSkill();
        userSkill.setSkillId(1);
        userSkill.setUser(this.userWithRelations);
        userSkill.setIsMain(true);
        userSkill.setOrderId(1);
        skills.add(userSkill);

        this.userWithRelations.setDiaries(diaries);
        this.userWithRelations.setProjects(projects);
        this.userWithRelations.setEducations(educations);
        this.userWithRelations.setExperiences(experiences);
        this.userWithRelations.setSkills(skills);

    }

    @Test
    void save() {

        try {

            // Save the user
            this.userService.save(this.user);

            // After saving the user, hibernate would use the first level cache to retrieve the user from memory
            // instead of hitting the database again. So we need to clear the entity manager to force the next query
            // Clear the entity manager to force the next query to hit the database
            entityManager.flush();
            entityManager.clear();

            // Check if the user has been saved correctly:
            NewUser createdUser = this.userService.findById(this.user.getId());
            assertNotNull(createdUser);

            assertEquals(this.user.getFirstName(), createdUser.getFirstName());
            assertEquals(this.user.getLastName(), createdUser.getLastName());
            assertEquals(this.user.getEmail(), createdUser.getEmail());
            assertEquals(this.user.getPhone(), createdUser.getPhone());
            assertEquals(this.user.getAge(), createdUser.getAge());
            assertEquals(this.user.getSex(), createdUser.getSex());
            assertEquals(this.user.getTitle(), createdUser.getTitle());
            assertEquals(this.user.getDescription(), createdUser.getDescription());
            assertEquals(this.user.getAddress(), createdUser.getAddress());
            assertEquals(this.user.getCity(), createdUser.getCity());
            assertEquals(this.user.getNationality(), createdUser.getNationality());
            assertEquals(this.user.getNation(), createdUser.getNation());
            assertEquals(this.user.getProvince(), createdUser.getProvince());
            assertEquals(this.user.getCap(), createdUser.getCap());

        } catch (Exception e) {
            fail(e.getMessage());
        }

    }

    @Test
    void saveWithRelations() {

        try {

            // Save the user with relations
            userService.save(this.userWithRelations);

            // After saving the user, hibernate would use the first level cache to retrieve the user from memory
            // instead of hitting the database again. So we need to clear the entity manager to force the next query
            // Clear the entity manager to force the next query to hit the database
            entityManager.flush();
            entityManager.clear();

            // check if the user has been saved correctly:
            NewUser createdUser = userService.findById(this.userWithRelations.getId());
            assertNotNull(createdUser);

            assertEquals(this.userWithRelations.getFirstName(), createdUser.getFirstName());
            assertEquals(this.userWithRelations.getLastName(), createdUser.getLastName());
            assertEquals(this.userWithRelations.getEmail(), createdUser.getEmail());
            assertEquals(this.userWithRelations.getPhone(), createdUser.getPhone());
            assertEquals(this.userWithRelations.getAge(), createdUser.getAge());
            assertEquals(this.userWithRelations.getSex(), createdUser.getSex());
            assertEquals(this.userWithRelations.getTitle(), createdUser.getTitle());
            assertEquals(this.userWithRelations.getDescription(), createdUser.getDescription());
            assertEquals(this.userWithRelations.getAddress(), createdUser.getAddress());
            assertEquals(this.userWithRelations.getCity(), createdUser.getCity());
            assertEquals(this.userWithRelations.getNationality(), createdUser.getNationality());
            assertEquals(this.userWithRelations.getNation(), createdUser.getNation());
            assertEquals(this.userWithRelations.getProvince(), createdUser.getProvince());
            assertEquals(this.userWithRelations.getCap(), createdUser.getCap());
            assertEquals(this.userWithRelations.getDiaries().size(), createdUser.getDiaries().size());
            assertEquals(this.userWithRelations.getSkills().size(), createdUser.getSkills().size());

            assertEquals(0, createdUser.getProjects().size());
            assertEquals(0, createdUser.getEducations().size());
            assertEquals(0, createdUser.getExperiences().size());

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void update() {

        try {
            // Save the user
            this.userService.save(this.user);

            // Change the user
            String newFirstName = "Roberto";
            this.user.setFirstName(newFirstName);

            // Update the user
            this.userService.update(this.user);

            // Clear the entity manager to force the next query to hit the database
            entityManager.flush();
            entityManager.clear();

            // Check if the user has been updated correctly:
            NewUser updatedUser = this.userService.findById(this.user.getId());
            assertNotNull(updatedUser);
            assertEquals(this.user.getFirstName(), updatedUser.getFirstName());
            assertEquals(newFirstName, updatedUser.getFirstName());
        } catch (Exception e) {
            fail(e.getMessage());
        }

    }

    @Test
    void updateWithRelations() {

        try {

            // Save the user with relations
            userService.save(this.userWithRelations);
            entityManager.flush();
            entityManager.clear();

            // Change the user
            String newFirstName = "Roberto";
            this.userWithRelations.setFirstName(newFirstName);
            NewDiary newDiary = new NewDiary();
            newDiary.setTitle("Test Diary 2");
            newDiary.setDescription("Test Description 2");
            newDiary.setIsMain(false);
            this.userWithRelations.getDiaries().add(newDiary);
            NewProject newProject = new NewProject();
            newProject.setTitle("Test Project 2");
            newProject.setDescription("Test Description 2");
            this.userWithRelations.getProjects().add(newProject);
            NewEducation newEducation = new NewEducation();
            newEducation.setField("Test Field 2");
            newEducation.setSchool("Test School 2");
            newEducation.setDegree("Test Degree 2");
            newEducation.setDescription("Test Description 2");
            this.userWithRelations.getEducations().add(newEducation);
            NewExperience newExperience = new NewExperience();
            newExperience.setTitle("Test Title 2");
            newExperience.setCompanyName("Test Company 2");
            newExperience.setDescription("Test Description 2");
            newExperience.setEmploymentType(EmploymentTypeEnum.FULL_TIME);
            this.userWithRelations.getExperiences().add(newExperience);
            NewUserSkill newUserSkill = new NewUserSkill();
            newUserSkill.setSkillId(2);
            newUserSkill.setUser(this.userWithRelations);
            newUserSkill.setIsMain(false);
            newUserSkill.setOrderId(2);
            this.userWithRelations.getSkills().add(newUserSkill);

            // Update the user
            this.userService.update(this.userWithRelations);
            entityManager.flush();
            entityManager.clear();

            // Check if the user has been updated correctly:
            NewUser updatedUser = this.userService.findById(this.userWithRelations.getId());
            assertNotNull(updatedUser);
            assertEquals(this.userWithRelations.getFirstName(), updatedUser.getFirstName());
            assertEquals(newFirstName, updatedUser.getFirstName());
            assertEquals(1, updatedUser.getDiaries().size());
            assertEquals(0, updatedUser.getProjects().size());
            assertEquals(0, updatedUser.getEducations().size());
            assertEquals(0, updatedUser.getExperiences().size());
            assertEquals(this.userWithRelations.getSkills().size(), updatedUser.getSkills().size());

        } catch (Exception e) {
            fail(e.getMessage());
        }

    }

    @Test
    void delete() {

        try {
            // Save the user
            this.userService.save(this.user);

            // Delete the user
            this.userService.delete(this.user);

            // Flush the entity manager to force the next query to hit the database
            entityManager.flush();
            entityManager.clear();

            // Check if the user has been deleted correctly:
            NewUser deletedUser = this.userService.findById(this.user.getId());
            assertNull(deletedUser);
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
            // Save the user with relations
            userService.save(this.userWithRelations);

            // Refresh this.userWithRelations:
            entityManager.refresh(this.userWithRelations);

            // Delete the user
            this.userService.delete(this.userWithRelations);

            // Flush the entity manager to force the next query to hit the database
            entityManager.flush();
            entityManager.clear();

            // Check if the user has been deleted correctly:
            try {
                NewUser deletedUser = this.userService.findById(this.userWithRelations.getId());
                assertNull(deletedUser);
            } catch (EntityNotFoundException e) {
                // The user has been deleted correctly
                assertTrue(true);
            }

            // Check if the relations have been deleted correctly:
            assertTrue(this.userWithRelations.getDiaries() == null || this.userWithRelations.getDiaries().isEmpty());
            assertTrue(this.userWithRelations.getSkills() == null || this.userWithRelations.getSkills().isEmpty());

            // These relations should not event been created
            assertTrue(this.userWithRelations.getProjects() == null || this.userWithRelations.getProjects().isEmpty());
            assertTrue(this.userWithRelations.getEducations() == null || this.userWithRelations.getEducations().isEmpty());
            assertTrue(this.userWithRelations.getExperiences() == null || this.userWithRelations.getExperiences().isEmpty());

        } catch (Exception e) {
            fail(e.getMessage());
        }

    }

}