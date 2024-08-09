package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.services.CommentService;
import com.openclassrooms.mddapi.services.UserService;
import com.openclassrooms.mddapi.validators.ObjectsValidator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("comments")
@RequiredArgsConstructor
public class CommentController {
    private static final Logger log = LoggerFactory.getLogger(CommentController.class);
    private final ObjectsValidator<CommentDto> validator;
    private final CommentService commentService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<CommentDto> save(
            @RequestBody CommentDto commentDto,
            Authentication authUser
    ) {
        User user = userService.searchByEmailOrUsername(authUser.getName());
        commentDto.setAuthorId(user.getId());

        validator.validate(commentDto);

        Comment newComment = commentService.save(
                CommentDto.toEntity(commentDto)
        );

        log.info("Comment saved [OK]");
        return new ResponseEntity<>(CommentDto.fromEntity(newComment), HttpStatus.CREATED);
    }

    @GetMapping("/article/{articleId}")
    public ResponseEntity<List<CommentDto>> findByArticleId(
            @PathVariable("articleId") Integer articleId
    ) {

        List<Comment> comments = commentService.findAllByArticleId(articleId);

        log.info("Comment list found [OK]");
        return new ResponseEntity<>(CommentDto.fromEntity(comments), HttpStatus.OK);
    }
}
