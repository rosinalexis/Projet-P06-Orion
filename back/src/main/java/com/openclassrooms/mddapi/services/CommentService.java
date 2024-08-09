package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.models.Comment;

import java.util.List;

public interface CommentService {

    Comment save(Comment comment);

    List<Comment> findAllByArticleId(Integer articleId);
}
