package com.myprojects.myportfolio.core.mappers;

import com.myprojects.myportfolio.core.dao.FeedbackDao;
import com.myprojects.myportfolio.core.dto.FeedbackDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FeedbackMapper extends BaseMapper<FeedbackDao, FeedbackDto> {
}
