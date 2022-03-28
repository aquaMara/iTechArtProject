package org.aquam.learnrest.service.impl;

import lombok.AllArgsConstructor;
import org.aquam.learnrest.dto.SubjectDTO;
import org.aquam.learnrest.exception.EntitiesNotFoundException;
import org.aquam.learnrest.model.Subject;
import org.aquam.learnrest.repository.SubjectRepository;
import org.aquam.learnrest.service.SubjectService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@Service
@Transactional
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final ModelMapper modelMapper;
    public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/static/subject_images";

    private static Validator validator;
    static {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.usingContext().getValidator();
    }

    @Override
    public Subject findById(Long subjectId) {
        return subjectRepository.findById(subjectId).orElseThrow(()
                -> new EntityNotFoundException("Subject with id: " + subjectId + " not found"));
    }

    @Override
    public Subject findByName(String name) {
        return subjectRepository.findBySubjectName(name).orElseThrow(()
                -> new EntityNotFoundException("Subject with name: " + name + " not found"));
    }

    @Override
    public List<Subject> findAll() {
        /*
        if (!subjectRepository.findAll().isEmpty())
            return subjectRepository.findAll();
        throw new EntitiesNotFoundException("There are no subjects");
         */
        if (subjectRepository.findAll().isEmpty())
            throw new EntitiesNotFoundException("There are no subjects");
        return subjectRepository.findAll();
    }

    // на фронт недейств пока файл не выбран
    @Override
    public Subject create(SubjectDTO subjectDTO, MultipartFile file) throws IOException {
        Optional<Subject> s = subjectRepository.findBySubjectName(subjectDTO.getSubjectName());
        if (subjectRepository.findBySubjectName(subjectDTO.getSubjectName()).isPresent())
            throw new EntityExistsException("Subject with name: " + subjectDTO.getSubjectName() + " already exists");
        Subject subject = toSubject(subjectDTO);
        // subject.setFilePath(uploadImage(file));
        return subjectRepository.save(subject);
    }

    @Override   // без path variable и так не раб
    public Subject updateById(Long subjectId, SubjectDTO newSubjectDTO) {
        Subject subject = findById(subjectId);
        Subject newSubject = toSubject(newSubjectDTO);
        subject.setSubjectName(newSubject.getSubjectName());
        return subjectRepository.save(subject);
    }

    @Override
    public boolean deleteById(Long subjectId) {
        Subject subject = findById(subjectId);
        subjectRepository.delete(subject);
        return true;
    }

    @Override
    public String uploadImage(MultipartFile file) throws IOException {
        StringBuilder filename = new StringBuilder();
        Path filenameAndPath = Paths.get(uploadDirectory, file.getOriginalFilename());
        filename.append(file.getOriginalFilename());
        Files.write(filenameAndPath, file.getBytes());
        return filename.toString();
    }

    public Subject toSubject(SubjectDTO subjectDTO) {
        Set<ConstraintViolation<SubjectDTO>> validationExceptions = validator.validate(subjectDTO);
        if (!validationExceptions.isEmpty())
            throw new ConstraintViolationException(validationExceptions);
        Subject subject = modelMapper.map(subjectDTO, Subject.class);
        return subject;
    }
}

        /*
        if (subjectDTO == null)
            throw new NullPointerException("Subject is null");
         */