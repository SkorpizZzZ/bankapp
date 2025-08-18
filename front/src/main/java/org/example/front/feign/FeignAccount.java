package org.example.front.feign;

import org.example.front.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "gateway", contextId = "account")
public interface FeignAccount {

    @PostMapping("/account/users")
    UserDto createUser(@RequestBody UserDto user);

    @GetMapping("/account/users/{username}")
    UserDto findUserByLogin(@PathVariable("username") String username);

    @PutMapping("/account/users/editPassword")
    UserDto updatePassword(@RequestBody UserDto user);

    @PutMapping("/account/users/editUserAccounts")
    UserDto updateUserAccounts(@RequestBody UserDto userDto);
}
