package org.aquam.learnrest.controller;

import lombok.RequiredArgsConstructor;
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

    // @ModelAttribute("subject")Subject subject, @ModelAttribute("section")Section section
    @PostMapping("/create")
    ResponseEntity<Article> createArticle(@RequestBody Article article, @RequestBody MultipartFile file) throws IOException {
        return new ResponseEntity<>(articleService.create(article, file), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    ResponseEntity<Article> updateArticle(@RequestBody Article article) {
        return new ResponseEntity<>(articleService.update(article), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{articleId}")
    ResponseEntity<Void> deleteArticleById(@PathVariable Long articleId) {
        articleService.deleteById(articleId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
