package org.example.front.feign;

import org.example.front.dto.CreateUserDto;
import org.example.front.dto.EditUserAccountDto;
import org.example.front.dto.UpdatePasswordDto;
import org.example.front.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "gateway", contextId = "account")
public interface AccountFeign {

    @PostMapping("/account/users")
    CreateUserDto createUser(@RequestBody CreateUserDto user);

    @GetMapping("/account/users/{username}")
    UserDto findUserByLogin(@PathVariable("username") String username);

    @PutMapping("/account/users/editPassword")
    UpdatePasswordDto updatePassword(@RequestBody UpdatePasswordDto user);

    @PutMapping("/account/users/editUserAccounts")
    EditUserAccountDto updateUserAccounts(@RequestBody EditUserAccountDto userDto);

    @GetMapping("/account/users/data")
    List<EditUserAccountDto> findAllUsersData();
}
