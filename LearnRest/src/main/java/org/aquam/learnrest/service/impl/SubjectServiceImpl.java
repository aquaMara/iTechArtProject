package org.aquam.learnrest.service.impl;

import lombok.AllArgsConstructor;
import org.aquam.learnrest.dto.SubjectDTO;
import org.aquam.learnrest.dto.mapper.SubjectMapper;
import org.aquam.learnrest.exception.EmptyInputException;
import org.aquam.learnrest.model.Subject;
import org.aquam.learnrest.repository.SubjectRepository;
import org.aquam.learnrest.service.SubjectService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@AllArgsConstructor
@Service
@Transactional
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;
    public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/static/subject_images";
    private final String ID_ERROR_MESSAGE = "Subject with id: %s not found";
    private final String NAME_ERROR_MESSAGE = "Subject with name: %s not found";
    private final String NULL_POINTER_ERROR_MESSAGE = "There are no subjects";
    private final String EMPTY_INPUT_ERROR_MESSAGE = "Some fields are empty";
    private final String NOT_SAVED_ERROR_MESSAGE = "Subject can not be saved";
    private final String EXISTS_ERROR_MESSAGE = "Subject with name:%s already exists";

    @Override
    public Subject findById(Long subjectId) {
        return subjectRepository.findById(subjectId).orElseThrow(() -> new EntityNotFoundException(String.format(ID_ERROR_MESSAGE, subjectId)));
    }

    @Override
    public Subject findByName(String name) {
        return subjectRepository.findBySubjectName(name).orElseThrow(() -> new EntityNotFoundException(String.format(NAME_ERROR_MESSAGE, name)));
    }

    @Override
    public List<Subject> findAll() {
        if (subjectRepository.findAll().isEmpty())
            throw new NullPointerException(NULL_POINTER_ERROR_MESSAGE);
        return subjectRepository.findAll();
    }

    // no
    @Override
    public Subject create(Subject subject, MultipartFile file) throws IOException {
        if (subject.getSubjectName() == null || subject.getSubjectName().isBlank() || file == null)
            throw new EmptyInputException(EMPTY_INPUT_ERROR_MESSAGE);
        if (subjectRepository.findBySubjectName(subject.getSubjectName()).isEmpty()) {
            subject.setFilePath(uploadImage(file));
            return subjectRepository.save(subject);
        }
        throw new EntityExistsException(String.format(EXISTS_ERROR_MESSAGE, subject.getSubjectName()));
    }

    @Override
    public Subject create(SubjectDTO subjectDTO, MultipartFile file) throws IOException {
        if (file == null)
            throw new NullPointerException("File is null");
        Subject subject = subjectMapper.mapToSubject(subjectDTO);
        if (subjectRepository.findBySubjectName(subject.getSubjectName()).isPresent())
            throw new EntityExistsException(String.format(NAME_ERROR_MESSAGE, subject.getSubjectName()));
        subject.setFilePath(uploadImage(file));
        return subjectRepository.save(subject);
    }

    // no
    @Override
    public Subject updateById(Long subjectId, Subject newSubject) {
        if (newSubject.getSubjectName() == null || newSubject.getSubjectName().isBlank())
            throw new EmptyInputException(EMPTY_INPUT_ERROR_MESSAGE);
        if (subjectRepository.findById(subjectId).isPresent()) {
            Subject subject = subjectRepository.getById(subjectId);
            subject.setSubjectName(newSubject.getSubjectName());
            return subject;
        }
        throw new EntityNotFoundException(String.format(ID_ERROR_MESSAGE, subjectId));
    }

    @Override
    public Subject updateById(Long subjectId, SubjectDTO newSubjectDTO) {
        if (subjectId == null || subjectRepository.findById(subjectId).isEmpty()) {
            throw new EntityNotFoundException(String.format(ID_ERROR_MESSAGE, subjectId));
        }
        Subject newSubject = subjectMapper.mapToSubject(newSubjectDTO);
        Subject subject = subjectRepository.getById(subjectId);
        subject.setSubjectName(newSubject.getSubjectName());
        return subject;
    }

    @Override
    public boolean deleteById(Long subjectId) {
        if (subjectRepository.findById(subjectId).isEmpty())
            throw new EntityNotFoundException(String.format(ID_ERROR_MESSAGE, subjectId));
        subjectRepository.deleteById(subjectId);
        return true;
    }

    // try catch VS throws
    @Override
    public String uploadImage(MultipartFile file) throws IOException {
        StringBuilder filename = new StringBuilder();
        Path filenameAndPath = Paths.get(uploadDirectory, file.getOriginalFilename());
        filename.append(file.getOriginalFilename());
        Files.write(filenameAndPath, file.getBytes());
        return filename.toString();
    }
}
