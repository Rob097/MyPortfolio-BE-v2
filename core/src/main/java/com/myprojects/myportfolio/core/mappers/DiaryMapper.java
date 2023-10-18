package com.myprojects.myportfolio.core.mappers;

import com.myprojects.myportfolio.clients.general.views.IView;
import com.myprojects.myportfolio.core.dao.Diary;
import com.myprojects.myportfolio.core.dto.DiaryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(componentModel = "spring", uses = {StoryMapper.class, UserMapper.class}, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface DiaryMapper extends BaseMapper<Diary, DiaryDto> {

    @Override
    @Mapping(target = "userId", source = "entity.user.id")
    DiaryDto mapToDto(Diary entity, IView view);

    @Override
    @Mapping(target = "user.id", source = "userId")
    Diary mapToDao(DiaryDto dto);

}
