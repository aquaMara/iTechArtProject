package org.aquam.learnrest.service.impl;

import org.aquam.learnrest.dto.ArticleDTO;
import org.aquam.learnrest.exception.EntitiesNotFoundException;
import org.aquam.learnrest.model.AppUser;
import org.aquam.learnrest.model.Article;
import org.aquam.learnrest.model.Section;
import org.aquam.learnrest.model.UserRole;
import org.aquam.learnrest.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;



@ExtendWith(MockitoExtension.class)
class ArticleServiceImplTest {


    @Mock
    private ArticleRepository articleRepository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private UserServiceImpl userService;
    @Mock
    private SectionServiceImpl sectionService;
    @InjectMocks
    private ArticleServiceImpl articleService;
    @Mock
    private ImageUploaderImpl imageUploader;
    @Mock
    private MultipartFile multipartFile;

    @Test
    @DisplayName("findById")
    void findByIdShouldReturnArticle() {
        Long articleId = 1L;
        Article article = new Article(articleId, "heading", "content", "link", "literature", "filePath.jpg");
        given(articleRepository.findById(articleId))
                .willReturn(Optional.of(article));
        articleService.findById(articleId);
        then(articleRepository).should().findById(articleId);
    }

    @Test
    @DisplayName("findById")
    void findByIdShouldThrowException() {
        Long articleId = 1L;
        given(articleRepository.findById(articleId))
                .willReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,
                () -> articleService.findById(articleId));
    }

    @Test
    @DisplayName("findAll")
    void findAllShouldReturnListOfArticles() {
        Article article = new Article(1L, "heading", "content", "link", "literature", "filePath.jpg");
        given(articleRepository.findAll())
                .willReturn(Arrays.asList(article));
        articleService.findAll();
        then(articleRepository).should(times(2)).findAll();

    }

    @Test
    @DisplayName("findAll")
    void findAllShouldThrowEntitiesNotFoundException() {
        given(articleRepository.findAll())
                .willReturn(new ArrayList<>());
        assertThrows(EntitiesNotFoundException.class,
                () -> articleService.findAll());
    }

    @Test
    @DisplayName("create")
    void createShouldReturnArticle() throws IOException {
        /*
        String newSubjectName = "subject_name";
        Subject subject = new Subject(null, newSubjectName, "filepath.jpg");
        SubjectDTO subjectDTO = new SubjectDTO(null, newSubjectName);
        given(subjectService.toSubject(subjectDTO)).willReturn(subject);
        given(imageUploader.uploadImage(any(MultipartFile.class), anyString())).willReturn(anyString());
        subjectService.create(subjectDTO, multipartFile);
        then(subjectRepository).should().save(subject);
         */
        Article article = new Article(null, "heading", "content", "link", "literature", "filePath.jpg");
        ArticleDTO articleDTO = new ArticleDTO(null, "heading", "content", "link", "literature", 1L, 1L);
        given(articleService.toArticle(articleDTO)).willReturn(article);
        given(imageUploader.uploadImage(any(MultipartFile.class), anyString())).willReturn(anyString());
        given(modelMapper.map(articleDTO, Article.class)).willReturn(article);
        articleService.create(articleDTO, multipartFile);
        then(articleRepository).should().save(article);
    }

    @Test
    @DisplayName("create")
    void createShouldThrowConstraintViolationException() {
        ArticleDTO articleDTO = new ArticleDTO(null, null, null, "link", "literature", 1L, 1L);
        assertThrows(ConstraintViolationException.class,
                () -> articleService.toArticle(articleDTO));
    }
    @Test
    @DisplayName("create")
    void createShouldNotThrowConstraintViolationException() throws IOException {
        ArticleDTO articleDTO = new ArticleDTO(null, "heading", "content", null, null, 1L, 1L);
        Article article = new Article(null, "heading", "content", null, null, "filePath.jpg");

        // verify(articleRepository).save(article);
        given(articleService.toArticle(articleDTO)).willReturn(article);
        given(modelMapper.map(articleDTO, Article.class)).willReturn(article);
        given(imageUploader.uploadImage(any(MultipartFile.class), anyString())).willReturn(anyString());
        articleService.create(articleDTO, multipartFile);
        then(articleRepository).should().save(article);
    }

    @Test
    void updateById() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void uploadImage() {
    }

    @Test
    void toArticle() {
    }

}