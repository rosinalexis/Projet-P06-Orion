package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.ArticleDto;
import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.services.ArticleService;
import com.openclassrooms.mddapi.validators.ObjectsValidator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("articles")
@RequiredArgsConstructor
public class ArticlesController {

    private static final Logger log = LoggerFactory.getLogger(ArticlesController.class);
    private final ObjectsValidator<ArticleDto> validator;
    private final ArticleService articleService;

    @GetMapping()
    public ResponseEntity<List<ArticleDto>> findAll() {

        List<Article> articleList = articleService.findAll();
        return new ResponseEntity<>(ArticleDto.fromEntity(articleList), HttpStatus.OK);
    }

    @GetMapping("/{articleId}")
    public ResponseEntity<ArticleDto> findById(
            @PathVariable("articleId") Integer articleId
    ) {
        Article article = articleService.findById(articleId);

        log.info("Article found: {}", article.getId());
        return new ResponseEntity<>(ArticleDto.fromEntity(article), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ArticleDto> save(
            @RequestBody ArticleDto articleDto
    ) {
        validator.validate(articleDto);
        Article newArticles = articleService.save(ArticleDto.toEntity(articleDto));

        log.info("Articles saved: {}", newArticles.getId());
        return new ResponseEntity<>(ArticleDto.fromEntity(newArticles), HttpStatus.CREATED);
    }

}
