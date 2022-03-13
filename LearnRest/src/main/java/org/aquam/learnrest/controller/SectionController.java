package org.aquam.learnrest.controller;

import lombok.RequiredArgsConstructor;
import org.aquam.learnrest.model.Section;
import org.aquam.learnrest.model.Subject;
import org.aquam.learnrest.service.impl.SectionServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/learn/sections")
@RequiredArgsConstructor    // for all final fields
public class SectionController {

    private final SectionServiceImpl sectionService;

    @GetMapping("")
    public ResponseEntity<List<Section>> getAllSubjects() {
        return new ResponseEntity<>(sectionService.findAll(), HttpStatus.OK);
    }

    // По идее не нужно
    @GetMapping("/{sectionId}")
    public ResponseEntity<Section> getSubjectById(@PathVariable Long sectionId) {
        return new ResponseEntity<>(sectionService.findById(sectionId), HttpStatus.OK);
    }

    // , @RequestBody Subject subject
    @PostMapping(value = "/create")
    public ResponseEntity<Section> createSubject(@RequestBody Section section) {
        return new ResponseEntity<>(sectionService.create(section), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Section> updateSubject(@RequestBody Section section) {
        return new ResponseEntity<>(sectionService.update(section), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{sectionId}")
    public ResponseEntity<Void> deleteSubject(@PathVariable Long sectionId) {
        sectionService.deleteById(sectionId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
