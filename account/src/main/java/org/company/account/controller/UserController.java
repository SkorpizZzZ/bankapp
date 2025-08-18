package org.company.account.controller;

import lombok.RequiredArgsConstructor;
import org.company.account.dto.UserDto;
import org.company.account.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping("/editUserAccounts")
    public ResponseEntity<UserDto> updateUserAccounts(@RequestBody UserDto user) {
     return ResponseEntity.ok(userService.updateUserAccounts(user));
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(user));
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDto> findUserByLogin(@PathVariable("username") String login) {
        return ResponseEntity.ok(userService.findUserByLogin(login));
    }

    @PutMapping("/editPassword")
    public ResponseEntity<UserDto> updatePassword(@RequestBody UserDto user) {
        return ResponseEntity.ok(userService.updatePassword(user));
    };
}
