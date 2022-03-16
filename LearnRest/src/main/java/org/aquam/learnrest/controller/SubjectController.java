package org.aquam.learnrest.controller;

import lombok.RequiredArgsConstructor;
import org.aquam.learnrest.model.Subject;
import org.aquam.learnrest.service.impl.SubjectServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotEmpty;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/learn/subjects")
@RequiredArgsConstructor    // for all final fields
public class SubjectController {

    private final SubjectServiceImpl subjectService;

    @GetMapping("")
    public ResponseEntity<List<Subject>> getAllSubjects() {
        // return ResponseEntity.ok(subjectService.findAll());
        return new ResponseEntity<>(subjectService.findAll(), HttpStatus.OK);   // 200
    }

    @GetMapping("/{subjectId}")
    public ResponseEntity<Subject> getSubjectById(@PathVariable Long subjectId) {
        return new ResponseEntity<>(subjectService.findById(subjectId), HttpStatus.OK); // 200
    }

    @PostMapping("")    // , @RequestBody MultipartFile file
    public ResponseEntity<Subject> createSubject(Subject subject, MultipartFile file) throws IOException {
        return new ResponseEntity<>(subjectService.create(subject, file), HttpStatus.CREATED);  // 201
    }

    @PutMapping("/{subjectId}")
    public ResponseEntity<Subject> updateSubject(@PathVariable Long subjectId, Subject newSubject) {
        return new ResponseEntity<>(subjectService.updateById(subjectId, newSubject), HttpStatus.OK); // 200 for updates, 201 for created
    }

    @DeleteMapping("/{subjectId}")  // HttpStatus.ACCEPTED = 202
    public ResponseEntity<Boolean> deleteSubjectById(@PathVariable Long subjectId) {
        return new ResponseEntity(subjectService.deleteById(subjectId), HttpStatus.OK);   // 204 - No Content
    }

}

/*
get, post = /api/articles
get, put, delete = /api/articles/{id}
 */