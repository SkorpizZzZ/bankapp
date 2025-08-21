package org.company.account.mapper;

import org.company.account.domain.User;
import org.company.account.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = AccountMapper.class)
public interface UserMapper {

    EditUserAccountDto entityToEditUserAccountDto(User user);
    UpdatePasswordDto entityToUpdatePasswordDto(User user);
    User createUserDtoToEntity(CreateUserDto dto);
    CreateUserDto entityToCreateUserDto(User dto);

    @Mapping(target = "accounts", source = "accounts")
    UserDto entityToUserDto(User user, List<AccountDto> accounts);
}
