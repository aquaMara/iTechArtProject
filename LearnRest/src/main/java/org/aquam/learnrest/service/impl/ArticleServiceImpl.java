package org.aquam.learnrest.service.impl;

import lombok.AllArgsConstructor;
import org.aquam.learnrest.model.Article;
import org.aquam.learnrest.repository.ArticleRepository;
import org.aquam.learnrest.service.ArticleService;
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
    private final String ERROR_MESSAGE = "Article not found";
    public final String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/static/article_images";

    @Override
    public Article findById(Long articleId) {
        return articleRepository.findById(articleId).orElseThrow(() -> new EntityNotFoundException(ERROR_MESSAGE));
    }

    @Override
    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    @Override
    public Article create(Article article, MultipartFile file) throws IOException {
        if (articleRepository.findById(article.getArticleId()).isEmpty()) {
            article.setFilePath(uploadImage(file));
            return articleRepository.save(article);
        }
        return null;
    }

    @Override
    public Article update(Article article) {
        if (articleRepository.findById(article.getArticleId()).isPresent())
            return articleRepository.save(article);
        return null;
    }

    @Override
    public void delete(Article article) {
        if (articleRepository.findById(article.getArticleId()).isPresent())
            articleRepository.delete(article);
    }

    @Override
    public void deleteById(Long articleId) {
        if (articleRepository.findById(articleId).isPresent())
            articleRepository.deleteById(articleId);
    }

    @Override
    public String uploadImage(MultipartFile file) throws IOException {
        StringBuilder filename = new StringBuilder();
        Path filenameAndPath = Paths.get(uploadDirectory, file.getOriginalFilename());
        filename.append(file.getOriginalFilename());
        Files.write(filenameAndPath, file.getBytes());
        return filename.toString();
    }
}
