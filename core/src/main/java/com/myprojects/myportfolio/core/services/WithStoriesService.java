package com.myprojects.myportfolio.core.services;

import com.myprojects.myportfolio.core.dao.BaseDao;
import com.myprojects.myportfolio.core.dao.Story;
import com.myprojects.myportfolio.core.dao.WithStoriesDao;
import com.myprojects.myportfolio.core.repositories.BaseRepository;
import com.myprojects.myportfolio.core.repositories.StoryRepository;
import org.apache.commons.lang.Validate;

public abstract class WithStoriesService<T extends BaseDao> extends BaseService<T> implements WithStoriesServiceI<T> {

    private final String NO_STORIES_ENTITY = "The entity does not have a relation with stories";

    protected BaseRepository<T, Integer> repository;

    protected StoryRepository storyRepository;

    public WithStoriesService(BaseRepository<T, Integer> repository, StoryRepository storyRepository) {
        super(repository);
        this.repository = repository;
        this.storyRepository = storyRepository;
    }

    /**
     * Creates a new entity.
     * <p>
     * The stories that are not yet persisted will be created.
     * The stories already persisted but not present in the entity that is being created will be ignored.
     * <p>
     *
     * @param entity the entity to create
     * @return the created entity
     */
    @Override
    public T save(T entity) {
        Validate.notNull(entity, fieldMissing("entity"));
        Validate.isTrue(entity instanceof WithStoriesDao, NO_STORIES_ENTITY);

        // Check if the entity already exists
        this.checkIfEntityAlreadyExists(entity.getId());

        // When creating a entity you can only create new stories. You can't connect existing stories.
        ((WithStoriesDao) entity).getStories().removeIf(story -> story.getId() != null);

        return repository.save(entity);

    }

    /**
     * Updates an existing entity.
     * <p>
     * The stories that are not yet persisted will be created.
     * The stories already persisted but not present in the entity that is being updated will be ignored.
     * The stories already persisted and present in the entity that is being updated will be updated but not removed.
     * <p>
     *
     * @param entity the entity to update
     * @return the updated entity
     */
    @Override
    public T update(T entity) {
        Validate.notNull(entity, fieldMissing("entity"));
        Validate.isTrue(entity instanceof WithStoriesDao, NO_STORIES_ENTITY);

        // Check if the entity does not exist
        this.checkIfEntityDoesNotExist(entity.getId());

        /* When creating an entity you can only create new stories. You can't connect existing stories.
         * However, you can update existing stories that are already connected to the entity.
         * Here we remove the stories that already exists in the database but are not part of the entity.
         */
        ((WithStoriesDao) entity).getStories().removeIf(story -> story.getId() != null && !storyRepository.findById(story.getId()).orElse(new Story()).getEntityId(entity.getClass()).equals(entity.getId()));

        // Load the existing stories in order to not lose them (or change them).
        repository.findById(entity.getId()).ifPresent(existingProject ->
                ((WithStoriesDao) existingProject).getStories().forEach(existingStory ->
                        ((WithStoriesDao) entity).getStories().add(existingStory)
                )
        );

        // Connect the stories to the entity
        entity.completeRelationships();

        // if entity.getMainStoryId() is not null, check if it's a story connected to the entity:
        if (((WithStoriesDao) entity).getMainStoryId() != null) {
            boolean isMainStoryConnectedToEntity = ((WithStoriesDao) entity).getStories().stream()
                    .filter(story -> story.getId() != null)
                    .anyMatch(story -> story.getId().equals(((WithStoriesDao) entity).getMainStoryId()));
            if (!isMainStoryConnectedToEntity) {
                throw new IllegalArgumentException("The main story is not connected to the entity");
            }
        }

        return repository.save(entity);

    }

    /**
     * Remove one or more stories to an entity
     *
     * @param entityId the entity id
     * @param storyIds the story ids
     */
    @Override
    public void removeStoriesFromEntity(Integer entityId, Integer[] storyIds) {
        Validate.notNull(entityId, fieldMissing("entityId"));
        Validate.notNull(storyIds, fieldMissing("storyIds"));
        Validate.isTrue(storyIds.length > 0, "storyIds must contain at least one element");

        T entity = repository.findById(entityId).orElseThrow(() -> new RuntimeException("Entity not found"));
        Validate.isTrue(entity instanceof WithStoriesDao, NO_STORIES_ENTITY);

        for (Integer storyId : storyIds) {
            ((WithStoriesDao) entity).getStories().removeIf(story -> story.getId().equals(storyId));
        }

        repository.save(entity);
    }

}
