package org.aquam.learnrest.service.impl;

import lombok.AllArgsConstructor;
import org.aquam.learnrest.dto.ArticleDTO;
import org.aquam.learnrest.dto.mapper.ArticleMapper;
import org.aquam.learnrest.exception.EmptyInputException;
import org.aquam.learnrest.model.AppUser;
import org.aquam.learnrest.model.Article;
import org.aquam.learnrest.model.Section;
import org.aquam.learnrest.repository.ArticleRepository;
import org.aquam.learnrest.repository.SectionRepository;
import org.aquam.learnrest.repository.UserRepository;
import org.aquam.learnrest.service.ArticleService;
import org.modelmapper.ModelMapper;
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
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final SectionRepository sectionRepository;
    private final UserRepository userRepository;
    public final String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/static/article_images";
    private final String ID_ERROR_MESSAGE = "Article with id: %s not found";
    private final String NULL_POINTER_ERROR_MESSAGE = "There are no articles";
    private final String EMPTY_INPUT_ERROR_MESSAGE = "Some fields are empty";

    private final ModelMapper modelMapper;

    @Override
    public Article findById(Long articleId) {
        return articleRepository.findById(articleId).orElseThrow(() -> new EntityNotFoundException(String.format(ID_ERROR_MESSAGE, articleId)));
    }

    @Override
    public List<Article> findAll() {
        if (articleRepository.findAll().isEmpty())
            throw new NullPointerException(NULL_POINTER_ERROR_MESSAGE);
        return articleRepository.findAll();
    }

    // links, literature are not necessary
    // проверка на пользователя
    // получается много репозиториев внутри
    @Override
    public Article create(Article article, MultipartFile file, Long sectionId, AppUser user) throws IOException {
        if (file == null || article.getHeading() == null || article.getContent() == null ||
            sectionId == null || user.getUserId() == null)
            throw new NullPointerException("Not all the fields are sent");
        if (article.getHeading().isBlank() || article.getContent().isBlank())
            throw new EmptyInputException("Not all the fields are filled");
        if (sectionRepository.findById(sectionId).isEmpty())
            throw new EntityNotFoundException(String.format("Section with id: %s not found", sectionId));
        if (userRepository.findById(user.getUserId()).isEmpty())
            throw new EntityNotFoundException(String.format("User with id: %s not found", user.getUserId()));
        article.setFilePath(uploadImage(file));
        article.setSection(sectionRepository.findById(sectionId).get());
        // article.setUser(userRepository.findById(user.getUserId()).get());
        return articleRepository.save(article);
    }

    @Override
    public Article create(ArticleDTO articleDTO, MultipartFile file) throws IOException {
        if (file == null)
            throw new NullPointerException("File is null");
        Article article = toArticle(articleDTO);
        if (sectionRepository.findById(articleDTO.getSectionId()).isEmpty())
            throw new EntityNotFoundException(String.format("Section with id: %s not found", articleDTO.getSectionId()));
        if (userRepository.findById(articleDTO.getUserId()).isEmpty())
            throw new EntityNotFoundException(String.format("User with id: %s not found", articleDTO.getUserId()));
        article.setFilePath(uploadImage(file));
        // article.setSection(sectionRepository.getById(articleDTO.getSectionId()));
        article.setUser(userRepository.getById(articleDTO.getUserId()));
        return articleRepository.save(article);
    }

    @Override
    public Article updateById(Long articleId, Article newArticle, Long sectionId) {
        if (newArticle.getHeading() == null || newArticle.getHeading().isBlank() ||
                newArticle.getContent() == null || newArticle.getContent().isBlank())
            throw new NullPointerException(EMPTY_INPUT_ERROR_MESSAGE);
        if (sectionId != null && sectionRepository.findById(sectionId).isEmpty())
            throw new EntityNotFoundException(String.format("Section with id: %s not found", sectionId));
        if (!articleRepository.findById(articleId).isPresent())
            throw new EntityNotFoundException(String.format(ID_ERROR_MESSAGE, articleId));
        Article oldArticle = articleRepository.getById(articleId);
        oldArticle.setHeading(newArticle.getHeading());
        oldArticle.setContent(newArticle.getContent());
        if (newArticle.getLiterature() != null && !newArticle.getLiterature().isBlank())
            oldArticle.setLiterature(newArticle.getLiterature());
        if (newArticle.getLink() != null && !newArticle.getLink().isBlank())
            oldArticle.setLink(newArticle.getLink());
        if (sectionId != null) {
            Section section = sectionRepository.findById(sectionId).get();
            oldArticle.setSection(section);
        }
        return oldArticle;
    }

    @Override
    public Article updateById(Long articleId, ArticleDTO newArticleDTO) {
        if (articleId == null || articleRepository.findById(articleId).isEmpty()) {
            throw new EntityNotFoundException(String.format("Article with id: %s not found", articleId));
        }
        Article oldArticle = articleRepository.getById(articleId);
        Article newArticle = toArticle(newArticleDTO);
        if (sectionRepository.findById(newArticleDTO.getSectionId()).isEmpty())
            throw new EntityNotFoundException(String.format("Section with id: %s not found", newArticleDTO.getSectionId()));
        else
            oldArticle.setSection(sectionRepository.getById(newArticleDTO.getSectionId()));
        if (userRepository.findById(newArticleDTO.getUserId()).isEmpty())
            throw new EntityNotFoundException(String.format("User with id: %s not found", newArticleDTO.getUserId()));
        else
            oldArticle.setUser(userRepository.getById(newArticleDTO.getUserId()));
        if (newArticle.getLiterature() != null)
            oldArticle.setLiterature(newArticle.getLiterature());
        if (newArticle.getLink() != null)
            oldArticle.setLink(newArticle.getLink());
        oldArticle.setHeading(newArticle.getHeading());
        oldArticle.setContent(newArticleDTO.getContent());
        return oldArticle;
    }

    @Override
    public boolean deleteById(Long articleId) {
        if (articleRepository.findById(articleId).isPresent()) {
            articleRepository.deleteById(articleId);
            return true;
        }
        throw new EntityNotFoundException(String.format(ID_ERROR_MESSAGE, articleId));
    }

    @Override
    public String uploadImage(MultipartFile file) throws IOException {
        StringBuilder filename = new StringBuilder();
        Path filenameAndPath = Paths.get(uploadDirectory, file.getOriginalFilename());
        filename.append(file.getOriginalFilename());
        Files.write(filenameAndPath, file.getBytes());
        return filename.toString();
    }

    public Article toArticle(ArticleDTO articleDTO) {
        if (articleDTO.getSectionId() == null || articleDTO.getUserId() == null
                || articleDTO.getHeading() == null || articleDTO.getContent() == null)
            throw new NullPointerException("Some fields are null");
        if (articleDTO.getHeading().isBlank() || articleDTO.getContent().isBlank()
                || articleDTO.getLink().isBlank() || articleDTO.getLiterature().isBlank())
            throw new EmptyInputException("Some fields are blank");

        Article article = modelMapper.map(articleDTO, Article.class);
        return article;

        /*
        if (articleDTO.getLiterature() != null)
            article.setLiterature(articleDTO.getLiterature());
        if (articleDTO.getLink() != null)
            article.setLink(articleDTO.getLink());
         */
    }
}