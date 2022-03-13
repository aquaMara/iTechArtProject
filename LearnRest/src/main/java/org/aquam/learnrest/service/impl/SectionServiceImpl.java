package org.aquam.learnrest.service.impl;

import lombok.AllArgsConstructor;
import org.aquam.learnrest.model.Section;
import org.aquam.learnrest.repository.SectionRepository;
import org.aquam.learnrest.service.SectionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@AllArgsConstructor
@Service
@Transactional
public class SectionServiceImpl implements SectionService {

    private final SectionRepository sectionRepository;
    private final String ERROR_MESSAGE = "Section not found";

    @Override
    public Section findById(Long sectionId) {
        return sectionRepository.findById(sectionId).orElseThrow(() -> new EntityNotFoundException(ERROR_MESSAGE));
    }

    @Override
    public List<Section> findAll() {
        return sectionRepository.findAll();
    }

    @Override
    public Section create(Section section) {
        if (!section.getName().isEmpty() && !section.getName().isBlank()) {
            if (!section.getSubject().getName().isEmpty() && !section.getSubject().getName().isBlank()) {
                if (sectionRepository.findById(section.getSectionId()).isEmpty())
                    return sectionRepository.save(section);
            }
        }
        return null;

    }

    // id is same
    @Override
    public Section update(Section section) {
        if (sectionRepository.findById(section.getSectionId()).isPresent())
            return sectionRepository.save(section);
        return null;
    }

    @Override
    public void deleteById(Long sectionId) {
        if (sectionRepository.findById(sectionId).isPresent())
            sectionRepository.deleteById(sectionId);
    }

    @Override
    public void delete(Section section) {
        if (sectionRepository.findById(section.getSectionId()).isPresent())
            sectionRepository.delete(section);
    }
}
