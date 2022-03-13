package org.aquam.learnrest.service.impl;

import lombok.AllArgsConstructor;
import org.aquam.learnrest.exception.AppRequestException;
import org.aquam.learnrest.model.Subject;
import org.aquam.learnrest.repository.SubjectRepository;
import org.aquam.learnrest.service.SubjectService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
    private final String ERROR_MESSAGE = "Subject not found###";
    public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/static/subject_images";

    @Override
    public Subject findById(Long subjectId) {
        return subjectRepository.findById(subjectId).orElseThrow(() -> new AppRequestException(ERROR_MESSAGE));
        // return subjectRepository.findById(subjectId).orElseThrow(() -> new EntityNotFoundException(ERROR_MESSAGE));
    }

    @Override
    public Subject findByName(String name) {
        return subjectRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException(ERROR_MESSAGE));
    }

    @Override
    public List<Subject> findAll() {
        return subjectRepository.findAll();
    }

    @Override
    public Subject create(Subject subject, MultipartFile file) throws IOException {
        if (!subject.getName().isEmpty() && !subject.getName().isBlank()) {
            if (subjectRepository.findByName(subject.getName()).isEmpty()) {
                subject.setFilePath(uploadImage(file));
                return subjectRepository.save(subject);
            }
        }
        return null;
    }

    // отправила с формы предмет с тем же айди но с другим именем
    @Override
    public Subject update(Subject subject) {
        /*
        if (subjectRepository.findById(subject.getSubjectId()).isPresent()) {
            subjectRepository.getById(subject.getSubjectId()).setName(subject.getName());
            return subjectRepository.save(subject);
        }
         */
        if (subjectRepository.findById(subject.getSubjectId()).isPresent())
            subjectRepository.save(subject);
        return null;
    }

    @Override
    public void delete(Subject subject) {
        if (subjectRepository.findById(subject.getSubjectId()).isPresent())
            subjectRepository.delete(subject);
    }

    @Override
    public void deleteById(Long subjectId) {
        if (subjectRepository.findById(subjectId).isPresent())
            subjectRepository.deleteById(subjectId);
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
