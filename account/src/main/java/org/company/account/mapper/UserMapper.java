package org.company.account.mapper;

import org.company.account.domain.User;
import org.company.account.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto entityToUserDto(User user);

    User userDtoToEntity(UserDto dto);
}
