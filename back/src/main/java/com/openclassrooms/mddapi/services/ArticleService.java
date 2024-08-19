package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.models.Article;

import java.util.List;

public interface ArticleService {
    Article findById(Integer id);

    List<Article> findAll();

    Article save(Article article);

    List<Article> findAllByUserSubscriptions(Integer userId);
}
