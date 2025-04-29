package org.example.hrm.mapper;

import org.example.hrm.dto.InterviewDto;
import org.example.hrm.model.Interview;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InterviewMapper extends EntityMapper<InterviewDto, Interview> {
    @Override
    InterviewDto toDto(Interview entity);

    @Override
    Interview toEntity(InterviewDto dto);
}
