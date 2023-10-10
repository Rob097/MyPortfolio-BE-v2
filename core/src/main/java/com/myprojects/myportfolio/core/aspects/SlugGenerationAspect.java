package com.myprojects.myportfolio.core.aspects;

import com.myprojects.myportfolio.core.aspects.interfaces.SlugSource;
import com.myprojects.myportfolio.core.dao.*;
import com.myprojects.myportfolio.core.repositories.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;

@Slf4j
@Aspect
@Component
public class SlugGenerationAspect {

    private final UserRepository userRepository;

    private final ProjectRepository projectRepository;

    private final StoryRepository storyRepository;

    private final EducationRepository educationRepository;

    private final ExperienceRepository experienceRepository;

    protected BaseRepository<? extends SlugDao, Integer> repository;

    private final Map<Object, Boolean> visitedEntities = new HashMap<>();

    public SlugGenerationAspect(UserRepository userRepository, ProjectRepository projectRepository, StoryRepository storyRepository, EducationRepository educationRepository, ExperienceRepository experienceRepository) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.storyRepository = storyRepository;
        this.educationRepository = educationRepository;
        this.experienceRepository = experienceRepository;
    }

    /**
     * This method is called before the save method of any CrudRepository.
     * It will calculate the slug for the entity itself and for all the children entities that implement SlugDao
     * @param entity the entity to save
     */
    @Before("execution(* org.springframework.data.repository.CrudRepository+.save(*)) && args(entity)")
    public void newGenerateSlug(BaseDao entity) {
        List<String> computedSlugs = new ArrayList<>();
        visitedEntities.clear(); // Clear the map for each new iteration
        iterateEntity(entity, computedSlugs);
    }

    /**
     * Iterates over the entity and all the children entities that implement SlugDao
     *
     * @param entity the entity to iterate over
     */
    private void iterateEntity(Object entity, List<String> computedSlugs) {

        // Check if this entity has been visited before to avoid infinite recursion
        if (visitedEntities.putIfAbsent(entity, true) != null) {
            return;
        }

        if (entity instanceof SlugDao slugEntity) {
            if (Strings.isBlank(slugEntity.getSlug())) {
                calculateSlug(slugEntity, computedSlugs);
            }
        }

        Class<?> entityClass = entity.getClass();
        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object fieldValue = field.get(entity);
                if (fieldValue instanceof BaseDao) {
                    iterateEntity(fieldValue, computedSlugs);
                } else if (fieldValue instanceof Set<?> set) {
                    for (Object setValue : set) {
                        if (setValue instanceof BaseDao) {
                            iterateEntity(setValue, computedSlugs);
                        }
                    }
                }
            } catch (IllegalAccessException e) {
                log.error("Error while getting slug sources", e);
            }
        }
    }


    /**
     * Generates a slug for the entity
     *
     * @param entity the entity to generate the slug for
     */
    private void calculateSlug(SlugDao entity, List<String> computedSlugs) {
        if (Strings.isBlank(entity.getSlug())) {
            List<String> sources = getSlugSources(entity);

            // If one of the sources is null, we can't generate a slug
            if (sources.isEmpty() || sources.stream().anyMatch(Objects::isNull)) {
                return;
            }

            log.info("calculateSlug - BEGIN");
            setRepository(entity);
            boolean isDone = false;
            int index = 0;
            String slug;

            try {
                do {
                    String appendix = index == 0 ? "" : ("-" + index);
                    slug = entity.generateSlug(sources, appendix);

                    Optional<? extends SlugDao> existingUser = repository.findBySlugConstraint(slug, entity);

                    if (existingUser.isPresent() || computedSlugs.contains(slug)) {
                        index++;
                    } else {
                        isDone = true;
                    }

                } while (!isDone);

                entity.setSlug(slug);

                log.info("calculateSlug: {} - END", slug);
            } catch (Exception e) {
                log.error("Error while generating slug: " + e.getMessage());
            }
        }
    }

    /**
     * Sets the repository according to the entity
     *
     * @param entity the entity to set the repository for
     */
    private <T extends SlugDao> void setRepository(T entity) {
        if (entity instanceof NewUser) {
            this.repository = this.userRepository;
        } else if (entity instanceof NewProject) {
            this.repository = this.projectRepository;
        } else if (entity instanceof NewEducation) {
            this.repository = this.educationRepository;
        } else if (entity instanceof NewStory) {
            this.repository = this.storyRepository;
        } else if (entity instanceof NewExperience) {
            this.repository = this.experienceRepository;
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
