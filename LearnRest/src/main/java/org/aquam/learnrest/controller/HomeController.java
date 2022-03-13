package org.aquam.learnrest.controller;

import lombok.RequiredArgsConstructor;
import org.aquam.learnrest.model.Subject;
import org.aquam.learnrest.service.impl.SubjectServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/learn")
@RequiredArgsConstructor // final fields only
public class HomeController {

    private final SubjectServiceImpl subjectService;

    @GetMapping("")
    public ResponseEntity<List<Subject>> getAllSubjects() {
        return new ResponseEntity<>(subjectService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/home")
    public ResponseEntity<Subject> getAll() {
        // all subjects + all articles + user account
        return null;
    }
}
