package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.handlers.ExceptionRepresentation;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.services.CommentService;
import com.openclassrooms.mddapi.services.UserService;
import com.openclassrooms.mddapi.validators.ObjectsValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@SecurityRequirement(
        name = "bearerAuth"
)
@Tag(name = "Comments")
public class CommentController {
    private static final Logger log = LoggerFactory.getLogger(CommentController.class);
    private final ObjectsValidator<CommentDto> validator;
    private final CommentService commentService;
    private final UserService userService;

    @Operation(
            description = "Post endpoint to save a new Comment. This endpoint accepts comment data and  saves the comment information to the database, and returns the comment's details.",
            summary = "Comment a new topic",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Comment created successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CommentDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400",
                            description = "Bad Request",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionRepresentation.class)
                            )
                    ),
                    @ApiResponse(responseCode = "401",
                            description = "Unauthorized / Invalid Token",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionRepresentation.class)
                            )
                    )
            }
    )
    @PostMapping
    public ResponseEntity<CommentDto> save(
            @RequestBody CommentDto commentDto,
            Authentication authUser
    ) {
        User user = userService.searchByEmailOrUsername(authUser.getName());
        commentDto.setAuthorId(user.getId());
        commentDto.setAuthorName(user.getName());

        validator.validate(commentDto);

        Comment newComment = commentService.save(
                CommentDto.toEntity(commentDto)
        );

        CommentDto newCommentDto = CommentDto.fromEntity(newComment);
        newCommentDto.setAuthorName(user.getName());

        log.info("Comment saved [OK]");
        return new ResponseEntity<>(newCommentDto, HttpStatus.CREATED);
    }

    @Operation(
            description = "Get endpoint to retrieve all comments. This endpoint returns a list of all comments properties available of a specific article.",
            summary = "Retrieve all comments of a specific article",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Comment retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = CommentDto.class))
                            )
                    ),
                    @ApiResponse(responseCode = "401",
                            description = "Unauthorized / Invalid Token",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionRepresentation.class)
                            )
                    )
            }
    )
    @GetMapping("/article/{articleId}")
    public ResponseEntity<List<CommentDto>> findByArticleId(
            @PathVariable("articleId") Integer articleId
    ) {

        List<Comment> comments = commentService.findAllByArticleId(articleId);

        log.info("Comment list found [OK]");
        return new ResponseEntity<>(CommentDto.fromEntity(comments), HttpStatus.OK);
    }
}
