package org.company.account.controller;

import lombok.RequiredArgsConstructor;
import org.company.account.dto.*;
import org.company.account.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/data")
    public ResponseEntity<List<EditUserAccountDto>> findAllUsersData() {
        return ResponseEntity.ok(userService.findAllUsersData());
    }

    @PostMapping("/transfer/{login}")
    public ResponseEntity<Void> transfer(
            @PathVariable("login") String login,
            @RequestBody TransferExchangeDto transferDto
    ) {
        userService.transfer(login, transferDto);
     return ResponseEntity.ok().build();
    }

    @PutMapping("/editUserAccounts")
    public ResponseEntity<EditUserAccountDto> updateUserAccounts(@RequestBody EditUserAccountDto user) {
     return ResponseEntity.ok(userService.updateUserAccounts(user));
    }

    @PostMapping
    public ResponseEntity<CreateUserDto> createUser(@RequestBody CreateUserDto user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(user));
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDto> findUserByLogin(@PathVariable("username") String login) {
        return ResponseEntity.ok(userService.findUserByLogin(login));
    }

    @PutMapping("/editPassword")
    public ResponseEntity<UpdatePasswordDto> updatePassword(@RequestBody UpdatePasswordDto user) {
        return ResponseEntity.ok(userService.updatePassword(user));
    };
}
