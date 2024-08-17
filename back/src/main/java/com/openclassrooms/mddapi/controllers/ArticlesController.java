package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.ArticleDto;
import com.openclassrooms.mddapi.handlers.ExceptionRepresentation;
import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.services.ArticleService;
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
@RequestMapping("articles")
@RequiredArgsConstructor
@SecurityRequirement(
        name = "bearerAuth"
)
@Tag(name = "Articles")
@CrossOrigin
public class ArticlesController {

    private static final Logger log = LoggerFactory.getLogger(ArticlesController.class);
    private final ObjectsValidator<ArticleDto> validator;
    private final ArticleService articleService;
    private final UserService userService;

    @Operation(
            description = "Get endpoint to retrieve all articles. This endpoint returns a list of all articles properties available.",
            summary = "Retrieve all articles",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Articles retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = ArticleDto.class))
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
    @GetMapping()
    public ResponseEntity<List<ArticleDto>> findAll() {

        List<Article> articleList = articleService.findAll();
        return new ResponseEntity<>(ArticleDto.fromEntity(articleList), HttpStatus.OK);
    }

    @Operation(
            description = "Get endpoint to retrieve a article. This endpoint returns a article's details.",
            summary = "Retrieve a article",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Article retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ArticleDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "401",
                            description = "Unauthorized / Invalid Token",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionRepresentation.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404",
                            description = "Not Found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionRepresentation.class)
                            )
                    )
            }
    )
    @GetMapping("/{articleId}")
    public ResponseEntity<ArticleDto> findById(
            @PathVariable("articleId") Integer articleId
    ) {
        Article article = articleService.findById(articleId);

        log.info("Article found [OK]");
        return new ResponseEntity<>(ArticleDto.fromEntity(article), HttpStatus.OK);
    }


    @Operation(
            description = "Post endpoint to save a new article. This endpoint accepts article data and  saves the article information to the database, and returns the article's details.",
            summary = "Save a new article",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Article created successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ArticleDto.class)
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
    public ResponseEntity<ArticleDto> save(
            @RequestBody ArticleDto articleDto,
            Authentication authUser
    ) {

        User user = userService.searchByEmailOrUsername(authUser.getName());
        articleDto.setAuthorId(user.getId());
        articleDto.setAuthorName(user.getName());

        validator.validate(articleDto);

        Article newArticles = articleService.save(ArticleDto.toEntity(articleDto));

        log.info("Articles saved [OK]");
        return new ResponseEntity<>(ArticleDto.fromEntity(newArticles), HttpStatus.CREATED);
    }

}
