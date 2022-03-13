package org.aquam.learnrest.controller;

import lombok.AllArgsConstructor;
import org.aquam.learnrest.service.impl.UserServiceImpl;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class AdminController {

    private final UserServiceImpl userService;
}
