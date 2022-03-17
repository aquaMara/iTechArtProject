package org.aquam.learnrest.service;

import org.aquam.learnrest.dto.SectionDTO;
import org.aquam.learnrest.model.Section;
import org.aquam.learnrest.model.Subject;

import java.util.List;

public interface SectionService {

    Section findById(Long sectionId);
    List<Section> findAll();
    Section create(Section section, Subject subject);
    Section create(SectionDTO sectionDTO);
    Section updateById(Long sectionId, Section newSection, Subject newSubject);
    Section updateById(Long sectionId, SectionDTO newSectionDTO);
    boolean deleteById(Long sectionId);

}
