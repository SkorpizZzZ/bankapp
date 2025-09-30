package org.example.front.feign;

import org.example.front.dto.CreateUserDto;
import org.example.front.dto.EditUserAccountDto;
import org.example.front.dto.UpdatePasswordDto;
import org.example.front.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "account", url = "${app.feign.account-service.url}")
public interface AccountFeign {

    @PostMapping("/users")
    CreateUserDto createUser(@RequestBody CreateUserDto user);

    @GetMapping("/users/{username}")
    UserDto findUserByLogin(@PathVariable("username") String username);

    @PutMapping("/users/editPassword")
    UpdatePasswordDto updatePassword(@RequestBody UpdatePasswordDto user);

    @PutMapping("/users/editUserAccounts")
    EditUserAccountDto updateUserAccounts(@RequestBody EditUserAccountDto userDto);

    @GetMapping("/users/data")
    List<EditUserAccountDto> findAllUsersData();
}
