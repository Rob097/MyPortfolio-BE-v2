package com.myprojects.myportfolio.core.newDataModel.mappers;

import com.myprojects.myportfolio.core.newDataModel.dao.NewEducation;
import com.myprojects.myportfolio.core.newDataModel.dao.NewProject;
import com.myprojects.myportfolio.core.newDataModel.dao.NewStory;
import com.myprojects.myportfolio.core.newDataModel.dto.NewStoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface StoryMapper extends BaseMapper<NewStory, NewStoryDto> {

    @Override
    @Mapping(target = "diaryId", source = "diary.id")
    @Mapping(target = "projectsIds", source = "projects", qualifiedByName = "daoToId")
    @Mapping(target = "educationsIds", source = "educations", qualifiedByName = "daoToId")
    NewStoryDto mapToDto(NewStory entity);

    @Override
    @Mapping(target = "diary.id", source = "diaryId")
    @Mapping(target = "projects", source = "projectsIds", qualifiedByName = "idToProject")
    @Mapping(target = "educations", source = "educationsIds", qualifiedByName = "idToEducation")
    NewStory mapToDao(NewStoryDto dto);

    @Named(value = "idToProject")
    static NewProject idToProject(Integer id) {
        NewProject project = NewProject.builder().build();
        project.setId(id);
        return project;
    }

    @Named(value = "idToEducation")
    static NewEducation idToEducation(Integer id) {
        NewEducation education = NewEducation.builder().build();
        education.setId(id);
        return education;
    }

}
