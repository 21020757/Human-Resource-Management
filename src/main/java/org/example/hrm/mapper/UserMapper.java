package org.example.hrm.mapper;

import org.example.hrm.dto.UserDto;
import org.example.hrm.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDto, User> {
    @Override
    @Mapping(source = "email", target = "email")
    UserDto toDto(User entity);

    @Override
    @Mapping(source = "email", target = "email")
    User toEntity(UserDto dto);
}
