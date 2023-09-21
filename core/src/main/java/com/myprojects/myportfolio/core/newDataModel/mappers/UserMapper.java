package com.myprojects.myportfolio.core.newDataModel.mappers;

import com.myprojects.myportfolio.core.newDataModel.dao.NewUser;
import com.myprojects.myportfolio.core.newDataModel.dto.NewUserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper<NewUser, NewUserDto> {

    @Override
    @Mapping(target = "address.nationality", source = "nationality")
    @Mapping(target = "address.nation", source = "nation")
    @Mapping(target = "address.province", source = "province")
    @Mapping(target = "address.city", source = "city")
    @Mapping(target = "address.cap", source = "cap")
    @Mapping(target = "address.address", source = "address")
    NewUserDto mapToDto(NewUser dao);

    @Override
    @Mapping(target = "nationality", source = "address.nationality")
    @Mapping(target = "nation", source = "address.nation")
    @Mapping(target = "province", source = "address.province")
    @Mapping(target = "city", source = "address.city")
    @Mapping(target = "cap", source = "address.cap")
    @Mapping(target = "address", source = "address.address")
    NewUser mapToDao(NewUserDto dto);

}
