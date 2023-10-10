package com.myprojects.myportfolio.core.mappers;

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
    @Mapping(target = "diaryId", source = "diary.id")
    @Mapping(target = "projectsIds", source = "projects", qualifiedByName = "daoToId")
    @Mapping(target = "educationsIds", source = "educations", qualifiedByName = "daoToId")
    @Mapping(target = "experiencesIds", source = "experiences", qualifiedByName = "daoToId")
    StoryDto mapToDto(Story entity);

    @Override
    @Mapping(target = "diary.id", source = "diaryId")
    @Mapping(target = "projects", source = "projectsIds", qualifiedByName = "idToProject")
    @Mapping(target = "educations", source = "educationsIds", qualifiedByName = "idToEducation")
    @Mapping(target = "experiences", source = "experiencesIds", qualifiedByName = "idToExperience")
    Story mapToDao(StoryDto dto);

    @Named(value = "idToProject")
    static Project idToProject(Integer id) {
        Project project = Project.builder().build();
        project.setId(id);
        return project;
    }

    @Named(value = "idToEducation")
    static Education idToEducation(Integer id) {
        Education education = Education.builder().build();
        education.setId(id);
        return education;
    }

    @Named(value = "idToExperience")
    static Experience idToExperience(Integer id) {
        Experience experience = Experience.builder().build();
        experience.setId(id);
        return experience;
    }

    @Named(value = "daoToId")
    static Integer daoToId(BaseDao entity) {
        return entity.getId();
    }

}
