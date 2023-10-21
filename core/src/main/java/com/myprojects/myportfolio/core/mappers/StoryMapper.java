package com.myprojects.myportfolio.core.mappers;

import com.myprojects.myportfolio.clients.general.views.IView;
import com.myprojects.myportfolio.clients.general.views.Normal;
import com.myprojects.myportfolio.core.dao.*;
import com.myprojects.myportfolio.core.dto.StoryDto;
import com.myprojects.myportfolio.core.mappers.skills.SkillMapper;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {SkillMapper.class}, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface StoryMapper extends BaseMapper<Story, StoryDto> {

    @Override
    @Mapping(target = "diaryId", source = "entity.diary.id")
    @Mapping(target = "projectId", source = "entity.project", qualifiedByName = "daoToId")
    @Mapping(target = "educationsIds", source = "entity.educations", qualifiedByName = "daoToId")
    @Mapping(target = "experiencesIds", source = "entity.experiences", qualifiedByName = "daoToId")
    StoryDto mapToDto(Story entity, IView view);

    @Override
    @Mapping(target = "diary.id", source = "diaryId")
    @Mapping(target = "project", source = "projectId", qualifiedByName = "idToProject")
    @Mapping(target = "educations", source = "educationsIds", qualifiedByName = "idToEducation")
    @Mapping(target = "experiences", source = "experiencesIds", qualifiedByName = "idToExperience")
    Story mapToDao(StoryDto dto);

    @Named(value = "idToProject")
    static Project idToProject(Integer id) {
        if(id!=null) {
            return Project.builder().id(id).build();
        }
        return null;
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
        return entity!=null ? entity.getId() : null;
    }

}
