package org.aquam.learnrest.service;

import org.aquam.learnrest.model.Article;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ArticleService {

    Article findById(Long articleId);
    List<Article> findAll();
    void create(Article article, MultipartFile file) throws IOException;
    void update(Article article);
    void delete(Article article);

    String uploadImage(MultipartFile file) throws IOException;
}
