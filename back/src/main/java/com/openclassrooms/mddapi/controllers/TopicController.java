package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.handlers.ExceptionRepresentation;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.services.TopicService;
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
@RequestMapping("topics")
@RequiredArgsConstructor
@SecurityRequirement(
        name = "bearerAuth"
)
@Tag(name = "Topics")
public class TopicController {
    private static final Logger log = LoggerFactory.getLogger(TopicController.class);
    private final ObjectsValidator<TopicDto> validator;
    private final TopicService topicService;
    private final UserService userService;


    @Operation(
            description = "Get endpoint to retrieve all topics. This endpoint returns a list of all topics properties available.",
            summary = "Retrieve all topics",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Topics retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = TopicDto.class))
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
    public ResponseEntity<List<TopicDto>> findAll() {

        List<Topic> topicList = topicService.findAll();
        return new ResponseEntity<>(TopicDto.fromEntity(topicList), HttpStatus.OK);
    }

    @Operation(
            description = "Post endpoint to save a new topic. This endpoint accepts topic data and  saves the topic information to the database, and returns the topic's details.",
            summary = "Save a new topic",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Topic created successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TopicDto.class)
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
    public ResponseEntity<TopicDto> save(
            @RequestBody TopicDto topicDto
    ) {
        validator.validate(topicDto);

        Topic newTopic = topicService.save(TopicDto.toEntity(topicDto));

        log.info("Topic saved [OK]");
        return new ResponseEntity<>(TopicDto.fromEntity(newTopic), HttpStatus.CREATED);
    }

    @Operation(
            description = "PUT endpoint to subscribe user to a topic. This endpoint accepts topic id information and returns the user details.",
            summary = "Subscribe user to a topic",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User subscribe successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserDto.class)
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
    @PutMapping("/{topicId}/subscribe")
    public ResponseEntity<UserDto> subscribe(
            @PathVariable Integer topicId,
            Authentication authUser
    ) {
        Integer userId = userService.searchByEmailOrUsername(authUser.getName()).getId();
        User user = topicService.subscribe(userId, topicId);

        log.info("Topic subscribed [OK]");
        return new ResponseEntity<>(UserDto.fromEntity(user), HttpStatus.OK);
    }

    @Operation(
            description = "DELETE endpoint to unsubscribe user to a topic. This endpoint accepts topic id information and returns the user details.",
            summary = "Unsubscribe user to a topic",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User unsubscribe successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserDto.class)
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
    @DeleteMapping("/{topicId}/unsubscribe")
    public ResponseEntity<UserDto> unsubscribe(
            @PathVariable Integer topicId,
            Authentication authUser
    ) {
        Integer userId = userService.searchByEmailOrUsername(authUser.getName()).getId();
        User user = topicService.unsubscribe(userId, topicId);

        log.info("Topic unsubscribed [OK]");
        return new ResponseEntity<>(UserDto.fromEntity(user), HttpStatus.OK);
    }

}
