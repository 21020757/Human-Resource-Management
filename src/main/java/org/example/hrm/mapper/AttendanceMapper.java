package org.example.hrm.mapper;

import org.example.hrm.dto.AttendanceDto;
import org.example.hrm.model.Attendance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AttendanceMapper extends EntityMapper<AttendanceDto, Attendance> {
    @Override
    @Mapping(source = "employee.id", target = "employeeId")
    AttendanceDto toDto(Attendance attendance);

    @Override
    @Mapping(source = "employeeId", target = "employee.id")
    Attendance toEntity(AttendanceDto attendanceDto);
}
