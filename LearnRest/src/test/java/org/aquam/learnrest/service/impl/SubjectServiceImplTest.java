package org.aquam.learnrest.service.impl;

import org.aquam.learnrest.model.Subject;
import org.aquam.learnrest.repository.SubjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumingThat;

class SubjectServiceImplTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void findById() {
    }

    @Test
    void findByName() {
    }

    @Test
    void findAllEmpty() {
        SubjectRepository subjectRepository = Mockito.mock(SubjectRepository.class);
        ModelMapper modelMapper = Mockito.mock(ModelMapper.class);
        SubjectServiceImpl subjectService = new SubjectServiceImpl(subjectRepository, modelMapper);

        Mockito.when(subjectRepository.findAll().isEmpty()).thenThrow(NullPointerException.class);
        assertThrows(NullPointerException.class,
                () -> subjectRepository.findAll().isEmpty(),
                "Different exception or no exception thrown");

        // Mockito.when(subjectRepository.findAll().isEmpty()).thenThrow(NullPointerException.class);
        /*
        assertThrows(NullPointerException.class,
                () -> subjectRepository.findAll(),
                "Different exception or no exception thrown");
         */
    }
    @Test
    void findAllNotEmpty() {
        SubjectRepository subjectRepository = Mockito.mock(SubjectRepository.class);
        ModelMapper modelMapper = Mockito.mock(ModelMapper.class);
        SubjectServiceImpl subjectService = new SubjectServiceImpl(subjectRepository, modelMapper);

        List<Subject> subjectsFromDB = new ArrayList<>();
        subjectsFromDB.add(new Subject(1L, "s1", "h1.jpg"));
        subjectsFromDB.add(new Subject(2L, "s2", "h2.jpg"));
        Mockito.when(subjectRepository.findAll()).thenReturn(subjectsFromDB);
        assertEquals(2, subjectService.findAll().size());
    }

    // assertEquals(2, subjectsFromDB.size());
    @Test
    void findAll2() {
        SubjectRepository subjectRepository = Mockito.mock(SubjectRepository.class);
        ModelMapper modelMapper = Mockito.spy(ModelMapper.class);
        SubjectServiceImpl subjectService = new SubjectServiceImpl(subjectRepository, modelMapper);

        List<Subject> subjectsFromDB = new ArrayList<>();

        subjectsFromDB.add(new Subject(1L, "s1", "h1.jpg"));
        subjectsFromDB.add(new Subject(2L, "s2", "h2.jpg"));

        Mockito.when(subjectRepository.findAll()).thenReturn(subjectsFromDB);
        subjectService.findAll();
        Mockito.verify(subjectRepository).findAll();


    }

    @Test
    void create() {
    }

    @Test
    void testCreate() {
    }

    @Test
    void updateById() {
    }

    @Test
    void testUpdateById() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void uploadImage() {
    }

    @Test
    void toSubject() {
    }
}