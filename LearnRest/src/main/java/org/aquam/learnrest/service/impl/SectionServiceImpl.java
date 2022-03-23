package org.aquam.learnrest.service.impl;

import lombok.AllArgsConstructor;
import org.aquam.learnrest.dto.SectionDTO;
import org.aquam.learnrest.dto.mapper.SectionMapper;
import org.aquam.learnrest.exception.EmptyInputException;
import org.aquam.learnrest.model.Section;
import org.aquam.learnrest.model.Subject;
import org.aquam.learnrest.repository.SectionRepository;
import org.aquam.learnrest.repository.SubjectRepository;
import org.aquam.learnrest.service.SectionService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@AllArgsConstructor
@Service
@Transactional
public class SectionServiceImpl implements SectionService {

    private final SectionRepository sectionRepository;
    private final SubjectRepository subjectRepository;
    private final String ID_ERROR_MESSAGE = "Section with id: %s not found";
    private final String NULL_POINTER_ERROR_MESSAGE = "There are no sections";
    private final String EMPTY_INPUT_ERROR_MESSAGE = "Some fields are empty";
    private final String NOT_SAVED_ERROR_MESSAGE = "Section can not be saved";
    private final String EXISTS_ERROR_MESSAGE = "Section with id:%s already exists";

    private final ModelMapper modelMapper;

    @Override
    public Section findById(Long sectionId) {
        return sectionRepository.findById(sectionId).orElseThrow(() -> new EntityNotFoundException(String.format(ID_ERROR_MESSAGE, sectionId)));
    }

    @Override
    public List<Section> findAll() {
        if (sectionRepository.findAll().isEmpty())
            throw new NullPointerException(NULL_POINTER_ERROR_MESSAGE);
        return sectionRepository.findAll();
    }

    @Override
    public Section create(Section section, Subject subject) {
        if (section.getSectionName() == null || subject.getSubjectName() == null
                || section.getSectionName().isBlank() || subject.getSubjectName().isBlank())
            throw new NullPointerException(EMPTY_INPUT_ERROR_MESSAGE);
        if (subjectRepository.findBySubjectName(subject.getSubjectName()).isEmpty())
            throw new EntityNotFoundException(String.format("Subject with name: %s not found", subject.getSubjectName()));
        Subject loadedSubject = subjectRepository.findBySubjectName(subject.getSubjectName()).get();
        section.setSubject(loadedSubject);
        return sectionRepository.save(section);
    }

    @Override
    public Section create(SectionDTO sectionDTO) {
        Section section = toSection(sectionDTO);
        if (subjectRepository.findById(sectionDTO.getSubjectId()).isEmpty()) {
            throw new EntityNotFoundException(String.format("Subject with id: %s not found", sectionDTO.getSubjectId()));
        }
        Subject loadedSubject = subjectRepository.getById(sectionDTO.getSubjectId());
        section.setSubject(loadedSubject);
        return sectionRepository.save(section);
    }

    @Override
    public Section updateById(Long sectionId, Section newSection, Subject newSubject) {
        if (newSection.getSectionName() == null || newSubject.getSubjectName() == null
                || newSection.getSectionName().isBlank() || newSubject.getSubjectName().isBlank())
            throw new NullPointerException(EMPTY_INPUT_ERROR_MESSAGE);
        if (subjectRepository.findBySubjectName(newSubject.getSubjectName()).isEmpty())
            throw new EntityNotFoundException(String.format("Subject with name: %s not found", newSubject.getSubjectName()));
        if (sectionRepository.findById(sectionId).isPresent()) {
            Section section = sectionRepository.getById(sectionId);
            section.setSectionName(newSection.getSectionName());
            newSubject = subjectRepository.findBySubjectName(newSubject.getSubjectName()).get();
            section.setSubject(newSubject);
            return section;
        }
        throw new EntityNotFoundException(String.format(ID_ERROR_MESSAGE, sectionId));
    }

    @Override
    public Section updateById(Long sectionId, SectionDTO newSectionDTO) {
        if (sectionId == null || sectionRepository.findById(sectionId).isEmpty()) {
            throw new EntityNotFoundException(String.format(ID_ERROR_MESSAGE, sectionId));
        }
        Section newSection = toSection(newSectionDTO);
        Section oldSection = sectionRepository.getById(sectionId);
        if (subjectRepository.findById(newSectionDTO.getSubjectId()).isEmpty()) {
            throw new EntityNotFoundException(String.format("Subject with id: %s not found", newSectionDTO.getSubjectId()));
        }
        Subject loadedSubject = subjectRepository.getById(newSectionDTO.getSubjectId());
        oldSection.setSectionName(newSection.getSectionName());
        oldSection.setSubject(loadedSubject);
        return oldSection;
    }

    @Override
    public boolean deleteById(Long sectionId) {
        if (sectionRepository.findById(sectionId).isPresent()) {
            sectionRepository.deleteById(sectionId);
            return true;
        }
        throw new EntityNotFoundException(String.format(ID_ERROR_MESSAGE, sectionId));
    }

    public Section toSection(SectionDTO sectionDTO) {
        if (sectionDTO.getSectionName() == null || sectionDTO.getSubjectId() == null)   //  || sectionDTO.getSubjectName() == null
            throw new NullPointerException("Section or subject is null");
        if (sectionDTO.getSectionName().isBlank()) //  || sectionDTO.getSubjectName().isBlank()
            throw new EmptyInputException("Section or subject name is blank");
        Section section = modelMapper.map(sectionDTO, Section.class);
        return section;
    }
}
