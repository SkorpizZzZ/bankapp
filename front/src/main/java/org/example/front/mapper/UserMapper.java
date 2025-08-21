package org.example.front.mapper;

import org.example.front.dto.CreateUserDto;
import org.example.front.dto.UserDto;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@Mapper(componentModel = "spring")
public interface UserMapper {

    default CreateUserDto encodePassAndReturn(CreateUserDto user, @Context PasswordEncoder passwordEncoder) {
        return new CreateUserDto(
                user.login(),
                passwordEncoder.encode(user.password()),
                null,
                user.name(),
                user.birthdate()
        );
    }

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
