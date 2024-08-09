package com.openclassrooms.mddapi.services.impl;

import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.repositories.CommentRepository;
import com.openclassrooms.mddapi.services.ArticleService;
import com.openclassrooms.mddapi.services.CommentService;
import com.openclassrooms.mddapi.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final ArticleService articleService;


    @Override
    public Comment save(Comment comment) {

        userService.findById(comment.getAuthor().getId());
        articleService.findById(comment.getArticle().getId());
        
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> findAllByArticleId(Integer articleId) {
        return commentRepository.findAllByArticleId(articleId);
    }


}
