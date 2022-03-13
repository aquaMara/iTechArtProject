package org.aquam.learnrest.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.aquam.learnrest.model.Subject;
import org.aquam.learnrest.repository.SubjectRepository;
import org.aquam.learnrest.service.impl.SubjectServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    // По идее не нужно
    @GetMapping("/{subjectId}")
    public ResponseEntity<Subject> getSubjectById(@PathVariable Long subjectId) {
        // return ResponseEntity.ok(subjectService.findById(subjectId));
        return new ResponseEntity<>(subjectService.findById(subjectId), HttpStatus.OK); // 200
    }

    @PostMapping(value = "/create") // кнопка с действием по этому адресу, а потом сразу от него назад редиректом?
    public ResponseEntity<Subject> createSubject(@RequestBody Subject subject, @RequestPart MultipartFile file) throws IOException {
        return new ResponseEntity<>(subjectService.create(subject, file), HttpStatus.CREATED);  // 201
    }

    @PutMapping("/update")
    public ResponseEntity<Subject> updateSubject(@RequestBody Subject subject) {
        return new ResponseEntity<>(subjectService.update(subject), HttpStatus.OK); // 200 for updates, 201 for created
    }

    @DeleteMapping("/delete/{subjectId}")
    public ResponseEntity<Void> deleteSubjectById(@PathVariable Long subjectId) {
        subjectService.deleteById(subjectId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);   // 204 - No Content
    }

}
    // public ResponseEntity<Subject> createSubject(@RequestBody Subject subject) throws IOException {
        // subjectRepository.save(subject);
        // return new ResponseEntity<>(subject, HttpStatus.CREATED);