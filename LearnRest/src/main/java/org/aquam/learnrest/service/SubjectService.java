package org.aquam.learnrest.service;

import org.aquam.learnrest.model.Subject;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface SubjectService {

    Subject findById(Long subjectId);
    Subject findByName(String name);
    List<Subject> findAll();
    Subject create(Subject subject, MultipartFile file) throws IOException;
    Subject update(Subject subject);
    void delete(Subject subject);
    void deleteById(Long subjectId);

    String uploadImage(MultipartFile file) throws IOException;
}
