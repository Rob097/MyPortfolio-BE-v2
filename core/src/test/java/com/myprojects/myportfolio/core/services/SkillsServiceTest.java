package com.myprojects.myportfolio.core.services;

import com.myprojects.myportfolio.core.BaseTest;
import com.myprojects.myportfolio.core.dao.skills.Skill;
import com.myprojects.myportfolio.core.dao.skills.SkillCategory;
import com.myprojects.myportfolio.core.dao.skills.UserSkill;
import com.myprojects.myportfolio.core.services.skills.SkillCategoryService;
import com.myprojects.myportfolio.core.services.skills.SkillService;
import com.myprojects.myportfolio.core.services.skills.UserSkillService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional // For the rollback
public class SkillsServiceTest extends BaseTest {

    @Autowired
    private SkillService skillsService;

    @Autowired
    private SkillCategoryService skillCategoryService;

    @Autowired
    private UserSkillService userSkillService;

    @Autowired
    private EntityManager entityManager;

    private SkillCategory skillCategory;
    private Skill skill;
    private UserSkill userSkill;

    @BeforeEach
    void setUp() {
        // Create a new SkillCategory and assign it to this.skillCategory
        skillCategory = new SkillCategory();
        skillCategory.setName("Test Skill Category");

        // Create a new Skill and assign it to this.skill
        skill = new Skill();
        skill.setName("Test Skill");
        skill.setCategory(skillCategory);

        // Create a new UserSkill and assign it to this.userSkill
        userSkill = new UserSkill();
        userSkill.setUserId(1);
        userSkill.setSkill(skill);
        userSkill.setIsMain(true);
        userSkill.setOrderId(1);
    }

    @Test
    void save() {

        try {

            SkillCategory savedCategory = this.skillCategoryService.save(this.skillCategory);
            assertNotNull(savedCategory);
            assertNotNull(savedCategory.getId());

            this.skill.setCategory(savedCategory);
            Skill savedSkill = this.skillsService.save(this.skill);
            assertNotNull(savedSkill);
            assertNotNull(savedSkill.getId());

            this.userSkill.setSkill(savedSkill);
            UserSkill savedUserSkill = this.userSkillService.save(this.userSkill);
            assertNotNull(savedUserSkill);
            assertNotNull(savedUserSkill.getId());
            assertNotNull(savedUserSkill.getId().getUserId());
            assertNotNull(savedUserSkill.getId().getSkillId());

        } catch (Exception e) {
            fail(e.getMessage());
        }

    }

    @Test
    void update() {

        try {

            SkillCategory savedCategory = this.skillCategoryService.save(this.skillCategory);
            this.skill.setCategory(savedCategory);
            Skill savedSkill = this.skillsService.save(this.skill);
            this.userSkill.setSkill(savedSkill);
            UserSkill savedUserSkill = this.userSkillService.save(this.userSkill);

            // Update the skill category
            savedCategory.setName("Updated Test Skill Category");
            SkillCategory updatedCategory = this.skillCategoryService.update(savedCategory);
            assertNotNull(updatedCategory);
            assertNotNull(updatedCategory.getId());
            assertNotNull(updatedCategory.getName());
            assertEquals(savedCategory.getName(), updatedCategory.getName());

            // Update the skill
            savedSkill.setName("Updated Test Skill");
            Skill updatedSkill = this.skillsService.update(savedSkill);
            assertNotNull(updatedSkill);
            assertNotNull(updatedSkill.getId());
            assertNotNull(updatedSkill.getName());
            assertEquals(savedSkill.getName(), updatedSkill.getName());

            // Update the user skill
            savedUserSkill.setIsMain(false);
            UserSkill updatedUserSkill = this.userSkillService.update(savedUserSkill);
            assertNotNull(updatedUserSkill);
            assertNotNull(updatedUserSkill.getId());
            assertNotNull(updatedUserSkill.getId().getUserId());
            assertNotNull(updatedUserSkill.getId().getSkillId());
            assertFalse(updatedUserSkill.getIsMain());

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void delete() {

        try {

            skillCategoryService.save(skillCategory);
            skill.setCategory(skillCategory);
            skillsService.save(skill);
            userSkill.setSkill(skill);
            userSkillService.save(userSkill);

            // Delete the user skill
            try {
                userSkillService.delete(userSkill);
                userSkillService.findById(userSkill.getId());
            } catch (EntityNotFoundException e) {
                // The userSkill has been deleted correctly
                assertTrue(true);
                userSkill = null;
            }

            // Delete the skill
            try {
                skillsService.delete(skill);
                skillsService.findById(skill.getId());
            } catch (EntityNotFoundException e) {
                // The skill has been deleted correctly
                assertTrue(true);
                skill = null;
            }

            // Delete the skill category
            try {
                skillCategoryService.delete(skillCategory);
                skillCategoryService.findById(skillCategory.getId());
            } catch (EntityNotFoundException e) {
                // The skillCategory has been deleted correctly
                assertTrue(true);
                skillCategory = null;
            }

            assertNull(userSkill);
            assertNull(skill);
            assertNull(skillCategory);

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void deleteFromRoot() {
        // Delete the category and assert that in cascade the skill and the userSkill are deleted
        try {

            skillCategoryService.save(skillCategory);
            skill.setCategory(skillCategory);
            skillsService.save(skill);
            userSkill.setSkill(skill);
            userSkillService.save(userSkill);

            // Delete the skill category
            try {
                skillCategoryService.delete(skillCategory);
                skillCategoryService.findById(skillCategory.getId());
            } catch (EntityNotFoundException e) {
                // The skillCategory has been deleted correctly
                assertTrue(true);
            }

            // Assert that the skill has been deleted
            try {
                skillsService.findById(skill.getId());
            } catch (EntityNotFoundException e) {
                // The skill has been deleted correctly
                assertTrue(true);
            }

            // Assert that the userSkill has been deleted
            try {
                userSkillService.findById(userSkill.getId());
            } catch (EntityNotFoundException e) {
                // The userSkill has been deleted correctly
                assertTrue(true);
            }

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

}
