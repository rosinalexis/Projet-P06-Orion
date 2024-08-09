package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.services.CommentService;
import com.openclassrooms.mddapi.validators.ObjectsValidator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("comments")
@RequiredArgsConstructor
public class CommentController {
    private static final Logger log = LoggerFactory.getLogger(CommentController.class);
    private final ObjectsValidator<CommentDto> validator;
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentDto> save(
            @RequestBody CommentDto commentDto
    ) {
        validator.validate(commentDto);
        Comment newComment = commentService.save(
                CommentDto.toEntity(commentDto)
        );

        log.info("Comment saved: {}", newComment.getId());
        return new ResponseEntity<>(CommentDto.fromEntity(newComment), HttpStatus.CREATED);
    }

    @GetMapping("/article/{articleId}")
    public ResponseEntity<List<CommentDto>> findByArticleId(
            @PathVariable("articleId") Integer articleId
    ) {

        List<Comment> comments = commentService.findAllByArticleId(articleId);

        log.info("Comment list found: {}", comments.size());
        return new ResponseEntity<>(CommentDto.fromEntity(comments), HttpStatus.OK);
    }
}
