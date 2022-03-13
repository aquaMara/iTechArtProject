package org.aquam.learnrest.service;

import org.aquam.learnrest.model.Section;

import java.util.List;

public interface SectionService {

    Section findById(Long sectionId);
    List<Section> findAll();
    Section create(Section section);
    Section update(Section section);
    void deleteById(Long sectionId);
    void delete(Section section);

}
