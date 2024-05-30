package com.myprojects.myportfolio.core.mappers;

import com.myprojects.myportfolio.clients.general.views.IView;
import com.myprojects.myportfolio.clients.general.views.Normal;
import com.myprojects.myportfolio.core.dao.User;
import com.myprojects.myportfolio.core.mappers.skills.UserSkillMapper;
import com.myprojects.myportfolio.core.dto.UserDto;
import jakarta.annotation.Nullable;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(componentModel = "spring", uses = {DiaryMapper.class, ProjectMapper.class, EducationMapper.class, ExperienceMapper.class, UserSkillMapper.class}, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public abstract class UserMapper extends BaseMapper<User, UserDto> {

    @Override
    @Mapping(target = "address.nationality", source = "dao.nationality")
    @Mapping(target = "address.nation", source = "dao.nation")
    @Mapping(target = "address.province", source = "dao.province")
    @Mapping(target = "address.city", source = "dao.city")
    @Mapping(target = "address.cap", source = "dao.cap")
    @Mapping(target = "address.address", source = "dao.address")
    public abstract UserDto mapToDto(User dao, IView view);

    @Override
    @Mapping(target = "nationality", source = "address.nationality")
    @Mapping(target = "nation", source = "address.nation")
    @Mapping(target = "province", source = "address.province")
    @Mapping(target = "city", source = "address.city")
    @Mapping(target = "cap", source = "address.cap")
    @Mapping(target = "address", source = "address.address")
    public abstract User mapToDao(UserDto dto);

}
