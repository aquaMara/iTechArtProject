package org.aquam.learnrest.controller;

import lombok.RequiredArgsConstructor;
import org.aquam.learnrest.dto.UserDTO;
import org.aquam.learnrest.model.AppUser;
import org.aquam.learnrest.model.Subject;
import org.aquam.learnrest.service.impl.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/learn/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @PreAuthorize("hasAnyRole('USER', 'TEACHER', 'ADMIN')")
    @GetMapping("")
    public ResponseEntity<List<AppUser>> getAllUsers() {
        System.out.println("Hello");
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('USER', 'TEACHER', 'ADMIN')")
    @GetMapping("/{userId}")
    public ResponseEntity<AppUser> getUserAccount(@AuthenticationPrincipal @PathVariable("userId") Long userId) {
        return new ResponseEntity<>(userService.findById(userId), HttpStatus.OK);
    }

    // everybody
    @PostMapping("")
    public ResponseEntity<AppUser> createUser(UserDTO userDTO) throws IOException {
        return new ResponseEntity<>(userService.registerUser(userDTO), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PutMapping("/{userId}")
    public ResponseEntity<AppUser> updateUser(@PathVariable Long userId, UserDTO changedUserDTO) {
        return new ResponseEntity<>(userService.updateById(userId, changedUserDTO), HttpStatus.OK); // 200 for updates, 201 for created
    }

    @PreAuthorize("hasRole('USER', 'ADMIN')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<Boolean> deleteUserById(@PathVariable Long userId) {
        return new ResponseEntity(userService.deleteById(userId), HttpStatus.OK);
    }


}
