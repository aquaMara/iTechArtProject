package org.aquam.learnrest.service;

import org.aquam.learnrest.dto.ArticleDTO;
import org.aquam.learnrest.model.AppUser;
import org.aquam.learnrest.model.Article;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ArticleService {

    Article findById(Long articleId);
    List<Article> findAll();
    Article create(Article article, MultipartFile file, Long sectionId, AppUser user) throws IOException;
    Article create(ArticleDTO articleDTO, MultipartFile file) throws IOException;
    Article updateById(Long articleId, Article newArticle, Long sectionId);
    Article updateById(Long articleId, ArticleDTO newArticle);
    boolean deleteById(Long articleId);

    String uploadImage(MultipartFile file) throws IOException;
}
