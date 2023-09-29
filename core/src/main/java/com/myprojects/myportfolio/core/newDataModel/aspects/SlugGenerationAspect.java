package com.myprojects.myportfolio.core.newDataModel.aspects;

import com.myprojects.myportfolio.core.newDataModel.aspects.interfaces.SlugSource;
import com.myprojects.myportfolio.core.newDataModel.dao.*;
import com.myprojects.myportfolio.core.newDataModel.repositories.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Aspect
@Component
public class SlugGenerationAspect {

    private final UserRepository userRepository;

    private final ProjectRepository projectRepository;

    private final StoryRepository storyRepository;

    private final EducationRepository educationRepository;

    protected BaseRepository<? extends SlugDao, Integer> repository;

    public SlugGenerationAspect(UserRepository userRepository, ProjectRepository projectRepository, StoryRepository storyRepository, EducationRepository educationRepository) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.storyRepository = storyRepository;
        this.educationRepository = educationRepository;
    }

    /**
     * This method is called before the save method of any CrudRepository.
     * It will calculate the slug for the entity itself and for all the children entities that implement SlugDao
     * @param entity the entity to save
     */
    @Before("execution(* org.springframework.data.repository.CrudRepository+.save(*)) && args(entity)")
    public void generateSlug(BaseDao entity) {

        if (entity instanceof NewUser user) {
            generateUserSlug(user);

            if (user.getProjects() != null) {
                user.getProjects().forEach(this::generateProjectSlug);
            }

            if (user.getDiaries() != null) {
                user.getDiaries().forEach(diary -> {
                    if (diary.getStories() != null) {
                        diary.getStories().forEach(this::generateStorySlug);
                    }
                });
            }

        } else if (entity instanceof NewProject project) {
            generateProjectSlug(project);

            if (project.getStories() != null) {
                project.getStories().forEach(this::generateStorySlug);
            }

        } else if (entity instanceof NewEducation education) {
            generateEducationSlug(education);

            if (education.getStories() != null) {
                education.getStories().forEach(this::generateStorySlug);
            }

        } else if (entity instanceof NewStory story) {
            generateStorySlug(story);

        } else if (entity instanceof NewDiary diary) {
            if (diary.getStories() != null) {
                diary.getStories().forEach(this::generateStorySlug);
            }
        }

    }

    private void generateUserSlug(NewUser entity) {
        this.repository = this.userRepository;
        this.calculateSlug(entity);
    }

    private void generateProjectSlug(NewProject entity) {
        this.repository = this.projectRepository;
        this.calculateSlug(entity);
    }

    private void generateEducationSlug(NewEducation entity) {
        this.repository = this.educationRepository;
        this.calculateSlug(entity);
    }

    private void generateStorySlug(NewStory entity) {
        this.repository = this.storyRepository;
        this.calculateSlug(entity);
    }

    /**
     * Generates a slug for the entity if it implements SlugDao
     *
     * @param entity the entity to generate the slug for
     */
    private void calculateSlug(Object entity) {
        if (entity instanceof SlugDao slugEntity) {
            if (Strings.isBlank(slugEntity.getSlug())) {
                log.info("calculateSlug - BEGIN");

                boolean isDone = false;
                int index = 0;
                String slug;
                List<String> sources = getSlugSources(entity);

                do {
                    String appendix = index == 0 ? "" : ("-" + index);
                    slug = slugEntity.generateSlug(sources, appendix);

                    Optional<? extends SlugDao> existingUser = repository.findBySlugConstraint(slug, slugEntity);

                    if (existingUser.isPresent()) {
                        index++;
                    } else {
                        isDone = true;
                    }

                } while (!isDone);

                slugEntity.setSlug(slug);

                log.info("calculateSlug: {} - END", slug);
            }
        }
    }

    /**
     * Gets the slug sources from the entity
     *
     * @param entity the entity to get the slug sources from
     * @return the slug sources
     */
    private List<String> getSlugSources(Object entity) {
        Class<?> entityClass = entity.getClass();
        Field[] fields = entityClass.getDeclaredFields();
        List<String> sources = new ArrayList<>();

        for (Field field : fields) {
            if (field.isAnnotationPresent(SlugSource.class)) {
                try {
                    field.setAccessible(true);
                    String fieldValue = (String) field.get(entity);
                    if (fieldValue != null && !fieldValue.isEmpty()) {
                        sources.add(fieldValue);
                    }
                } catch (IllegalAccessException e) {
                    log.error("Error while getting slug sources", e);
                }
            }
        }

        return sources;
    }

}
