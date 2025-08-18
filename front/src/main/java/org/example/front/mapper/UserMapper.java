package org.example.front.mapper;

import org.example.front.dto.CreateUserDto;
import org.example.front.dto.EditUserAccountDto;
import org.example.front.dto.UpdatePasswordDto;
import org.example.front.dto.UserDto;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto editUserAccountDtoToUserDto(String login, EditUserAccountDto dto);

    @Mapping(target = "password", expression = "java(passwordEncoder.encode(createUserDto.password()))")
    UserDto createUserDtoToUserDto(CreateUserDto createUserDto, @Context PasswordEncoder passwordEncoder);

    @Mapping(target = "password", expression = "java(passwordEncoder.encode(createUserDto.password()))")
    UserDto updatePasswordDtoToUserDto(
            String login,
            UpdatePasswordDto createUserDto,
            @Context PasswordEncoder passwordEncoder
    );

    default User userDtoToUserDetails(UserDto dto) {
        return new User(
                dto.login(),
                dto.password(),
                true,
                true,
                true,
                true,
                Collections.emptyList()
        );
    }
}
