package com.myprojects.myportfolio.core.mappers;

import com.myprojects.myportfolio.clients.general.views.IView;
import com.myprojects.myportfolio.core.dao.*;
import com.myprojects.myportfolio.core.dto.StoryDto;
import com.myprojects.myportfolio.core.mappers.skills.SkillMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(componentModel = "spring", uses = {SkillMapper.class}, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface StoryMapper extends BaseMapper<Story, StoryDto> {

    @Override
    @Mapping(target = "diaryId", source = "entity.diary.id")
    @Mapping(target = "projectId", source = "entity.project", qualifiedByName = "daoToId")
    @Mapping(target = "educationId", source = "entity.education", qualifiedByName = "daoToId")
    @Mapping(target = "experienceId", source = "entity.experience", qualifiedByName = "daoToId")
    StoryDto mapToDto(Story entity, IView view);

    @Override
    @Mapping(target = "diary.id", source = "diaryId")
    @Mapping(target = "project", source = "projectId", qualifiedByName = "idToProject")
    @Mapping(target = "education", source = "educationId", qualifiedByName = "idToEducation")
    @Mapping(target = "experience", source = "experienceId", qualifiedByName = "idToExperience")
    Story mapToDao(StoryDto dto);

    @Named(value = "idToProject")
    static Project idToProject(Integer id) {
        if (id != null) {
            return Project.builder().id(id).build();
        }
        return null;
    }

    @Named(value = "idToEducation")
    static Education idToEducation(Integer id) {
        if (id != null) {
            return Education.builder().id(id).build();
        }
        return null;
    }

    @Named(value = "idToExperience")
    static Experience idToExperience(Integer id) {
        if (id != null) {
            return Experience.builder().id(id).build();
        }
        return null;
    }

    @Named(value = "daoToId")
    static Integer daoToId(BaseDao entity) {
        return entity != null ? entity.getId() : null;
    }

}
