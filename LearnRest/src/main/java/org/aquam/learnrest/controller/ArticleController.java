package org.aquam.learnrest.controller;

import lombok.RequiredArgsConstructor;
import org.aquam.learnrest.dto.ArticleDTO;
import org.aquam.learnrest.model.AppUser;
import org.aquam.learnrest.model.Article;
import org.aquam.learnrest.service.impl.ArticleServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/learn/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleServiceImpl articleService;

    @GetMapping
    ResponseEntity<List<Article>> getAllArticles() {
        return new ResponseEntity<>(articleService.findAll(), HttpStatus.OK);
    }

    // сюда ещё пользователя добавляла, чтобы был виден аккаунт сверху...
    @GetMapping("/{articleId}")
    ResponseEntity<Article> getArticleById(@PathVariable Long articleId) {
        return new ResponseEntity<>(articleService.findById(articleId), HttpStatus.OK);
    }

    /*
    @PostMapping("")
    ResponseEntity<Article> createArticle(Article article, MultipartFile file, Long sectionId, AppUser user) throws IOException {
        return new ResponseEntity<>(articleService.create(article, file, sectionId, user), HttpStatus.CREATED);
    }
     */
    @PostMapping("")
    ResponseEntity<Article> createArticle(ArticleDTO articleDTO, MultipartFile file) throws IOException {
        return new ResponseEntity<>(articleService.create(articleDTO, file), HttpStatus.CREATED);
    }

    /*
    @PutMapping("/{articleId}")
    ResponseEntity<Article> updateArticle(@PathVariable Long articleId, Article article, Long sectionId) {
        return new ResponseEntity<>(articleService.updateById(articleId, article, sectionId), HttpStatus.OK);
    }
     */

    @PutMapping("/{articleId}")
    ResponseEntity<Article> updateArticle(@PathVariable Long articleId, ArticleDTO articleDTO) {
        return new ResponseEntity<>(articleService.updateById(articleId, articleDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{articleId}")
    ResponseEntity<Boolean> deleteArticleById(@PathVariable Long articleId) {
        return new ResponseEntity<>(articleService.deleteById(articleId), HttpStatus.OK);
    }

}
/*
get, post = /api/articles
get, put, delete = /api/articles/{id}
 */
