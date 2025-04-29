package org.example.hrm.mapper;

import org.example.hrm.dto.CandidateDto;
import org.example.hrm.model.Candidate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CandidateMapper extends EntityMapper<CandidateDto, Candidate> {
    @Override
    Candidate toEntity(CandidateDto dto);

    @Override
    CandidateDto toDto(Candidate dto);
}
