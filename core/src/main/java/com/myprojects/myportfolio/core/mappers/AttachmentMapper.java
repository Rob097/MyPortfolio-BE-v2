package com.myprojects.myportfolio.core.mappers;

import com.myprojects.myportfolio.clients.general.views.IView;
import com.myprojects.myportfolio.core.dao.Attachment;
import com.myprojects.myportfolio.core.dto.AttachmentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public abstract class AttachmentMapper extends BaseMapper<Attachment, AttachmentDto> {

    @Override
    @Mapping(target = "userId", source = "entity.user.id")
    public abstract AttachmentDto mapToDto(Attachment entity, IView view);

    @Override
    @Mapping(target = "user.id", source = "userId")
    public abstract Attachment mapToDao(AttachmentDto dto);

}
