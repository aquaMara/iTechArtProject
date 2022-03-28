package org.aquam.learnrest.service.impl;

import org.aquam.learnrest.dto.SubjectDTO;
import org.aquam.learnrest.exception.EntitiesNotFoundException;
import org.aquam.learnrest.model.Subject;
import org.aquam.learnrest.repository.SubjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubjectServiceImplTest {

    @Mock
    private SubjectRepository subjectRepository;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private SubjectServiceImpl subjectService;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("findById")
    void findByIdShouldReturnSubject() {
        // Arrange - Given
        Long subjectId = 1L;
        Subject subject = new Subject(subjectId, "subject_name", "filepath.jpg");
        given(subjectRepository.findById(subjectId)).willReturn(java.util.Optional.of(subject));
        // Act - When
        subjectService.findById(subjectId);
        // Assert - Then
        then(subjectRepository).should().findById(subjectId);
    }

    @Test
    @DisplayName("findById")
    void findByIdShouldThrowException() {
        // given(subjectRepository.findById(anyLong())).willThrow(EntityNotFoundException.class);
        assertThrows(EntityNotFoundException.class,
                () -> subjectService.findById(anyLong()));
    }

    @Test
    @DisplayName("findByName")
    void findByNameShouldReturnSubject() {
        String subjectName = "subject_name";
        Subject subject = new Subject(1L, subjectName, "filepath.jpg");
        given(subjectRepository.findBySubjectName(subjectName)).willReturn(java.util.Optional.of(subject));

        subjectService.findByName(subjectName);

        then(subjectRepository).should().findBySubjectName(subjectName);
    }

    @Test
    @DisplayName("findByName")
    void findByNameShouldThrowException() {
        // given(subjectRepository.findBySubjectName(anyString())).willThrow(EntityNotFoundException.class);
        assertThrows(EntityNotFoundException.class,
                () -> subjectService.findByName(anyString()));
    }

    @Test
    @DisplayName("findAll")
    void findAllShouldReturnListOfSubjects() {
        List<Subject> subjects = List.of(new Subject(1L, "subject_name", "filepath.jpg"));
        given(subjectRepository.findAll()).willReturn(subjects);

        subjectService.findAll();

        then(subjectRepository).should(times(2)).findAll();
    }
    @Test
    @DisplayName("findAll")
    void findAllShouldThrowException() {
        // .willThrow(EntitiesNotFoundException.class);
        given(subjectRepository.findAll()).willReturn(new ArrayList<>());
        assertThrows(EntitiesNotFoundException.class,
                () -> subjectService.findAll());
    }

    @Test
    @DisplayName("create")
    void createShouldReturnSubject() throws IOException {

        String existingSubjectName = "existing_subject_name";
        Subject existingSubject = new Subject(1L, existingSubjectName, "filepath.jpg");
        // given(subjectRepository.findBySubjectName(existingSubjectName)).willReturn(Optional.of(existingSubject));

        String newSubjectName = "subject_name";
        Subject subject = new Subject(null, newSubjectName, "filepath.jpg");
        SubjectDTO subjectDTO = new SubjectDTO(null, newSubjectName);
        //given(subjectRepository.findBySubjectName(newSubjectName)).willReturn(Optional.of(subject));
        //assumeTrue(subjectRepository.findBySubjectName(newSubjectName).isPresent());
        given(subjectService.toSubject(subjectDTO)).willReturn(subject);

        MultipartFile mockFile = mock(MultipartFile.class);
        subjectService.create(subjectDTO, mockFile);
        then(subjectRepository).should().save(subject);

        subjectRepository.findBySubjectName(subjectDTO.getSubjectName()).isPresent();



    }

    // given(subjectRepository.findBySubjectName(subjectName)).willThrow(EntityExistsException.class);
    @Test
    @DisplayName("create")
    void createShouldThrowException() throws IOException {
        given(subjectRepository.findBySubjectName(anyString()))
                .willReturn(Optional.of(new Subject(1L, "subject_name", "filepath.jpg")));
        assertThrows(EntityExistsException.class,
                () -> subjectService.create(new SubjectDTO(null, "subject_name"), mock(MultipartFile.class)));

    }

    @Test
    @DisplayName("updateById")
    void updateByIdShouldReturnSubject() {
        Long subjectId = 1L;
        String subjectName = "subject_name";
        Subject subject = new Subject(1L, "subject_name", "filepath.jpg");
        SubjectDTO newSubjectDTO = new SubjectDTO(null, "subjectDTO_name");
        Subject newSubject = new Subject(null, "subjectDTO_name", "filepath.jpg");

        given(subjectRepository.findById(subjectId)).willReturn(Optional.of(subject));
        given(subjectService.toSubject(newSubjectDTO)).willReturn(newSubject);
        subjectService.updateById(subjectId, newSubjectDTO);
        then(subjectRepository).should().save(subject);



    }

    // ??? нужно ли
    @Test
    @DisplayName("updateById")
    void updateByIdShouldThrowException() {
        Long subjectId = 1L;
        Subject subject = new Subject(subjectId, "subject_name", "filepath.jpg");
        SubjectDTO newSubjectDTO = new SubjectDTO(null, "subjectDTO_name");
        Subject newSubject = new Subject(null, "subjectDTO_name", "filepath.jpg");

        given(subjectRepository.findById(subjectId)).willReturn(Optional.of(subject));
        given(subjectService.toSubject(newSubjectDTO)).willThrow(ConstraintViolationException.class);
        assertThrows(ConstraintViolationException.class,
                () -> subjectService.updateById(subjectId, newSubjectDTO));
    }

    /*
    Subject subject = findById(subjectId);
        subjectRepository.delete(subject);
        return true;
     */
    @Test
    void deleteByIdShouldReturnTrue() {
        Long subjectId = 1L;
        Subject subject = new Subject(subjectId, "subject_name", "filepath.jpg");
        given(subjectRepository.findById(subjectId))
                .willReturn(Optional.of(subject))
                .willReturn(Optional.of(subject));
        subjectService.deleteById(subjectId);
        then(subjectRepository).should().delete(subject);

    }

    @Test
    void deleteByIdShouldThrowException() {
    }

    @Test
    void uploadImage() {
    }

    @Test
    void toSubject() {
        SubjectDTO subjectDTO = new SubjectDTO(null, null);
        // Subject subject = new Subject(null, "subjectDTO_name", "filepath.jpg");

    }
}

/*
    @Test
    @DisplayName("findAll")
    void findAllShouldThrowException2() {
        Mockito.when(subjectRepository.findAll()).thenReturn(new ArrayList<>());
        assertThrows(EntitiesNotFoundException.class,
                () -> subjectService.findAll(),
                "Different exception or no exception thrown");
    }
     */