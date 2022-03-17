package org.aquam.learnrest.controller;

import lombok.RequiredArgsConstructor;
import org.aquam.learnrest.dto.SectionDTO;
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
    public ResponseEntity<List<Section>> getAllSections() {
        return new ResponseEntity<>(sectionService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{sectionId}")
    public ResponseEntity<Section> getSectionById(@PathVariable Long sectionId) {
        return new ResponseEntity<>(sectionService.findById(sectionId), HttpStatus.OK);
    }

    /*
    // , @RequestBody Subject subject
    @PostMapping("")
    public ResponseEntity<Section> createSection(Section section, Subject subject) {
        return new ResponseEntity<>(sectionService.create(section, subject), HttpStatus.CREATED);
    }
     */

    @PostMapping("")
    public ResponseEntity<Section> createSection(SectionDTO sectionDTO) {
        return new ResponseEntity<>(sectionService.create(sectionDTO), HttpStatus.CREATED);
    }

    /*
    @PutMapping("/{sectionId}")
    public ResponseEntity<Section> updateSection(@PathVariable Long sectionId, Section newSection, Subject newSubject) {
        return new ResponseEntity<>(sectionService.updateById(sectionId, newSection, newSubject), HttpStatus.OK);
    }
     */
    @PutMapping("/{sectionId}")
    public ResponseEntity<Section> updateSection(@PathVariable Long sectionId, SectionDTO newSectionDTO) {
        return new ResponseEntity<>(sectionService.updateById(sectionId, newSectionDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{sectionId}")
    public ResponseEntity<Boolean> deleteSectionById(@PathVariable Long sectionId) {
        return new ResponseEntity(sectionService.deleteById(sectionId), HttpStatus.OK);
    }
}
