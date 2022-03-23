package org.aquam.learnrest.controller;

import lombok.RequiredArgsConstructor;
import org.aquam.learnrest.dto.UserDTO;
import org.aquam.learnrest.model.AppUser;
import org.aquam.learnrest.service.impl.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/learn/register")
@RequiredArgsConstructor
public class RegistrationController {

    private final UserServiceImpl userService;

    @GetMapping("")
    public ResponseEntity<AppUser> createUser(UserDTO userDTO) throws IOException {
        return new ResponseEntity<>(userService.registerUser(userDTO), HttpStatus.CREATED);
    }

}
