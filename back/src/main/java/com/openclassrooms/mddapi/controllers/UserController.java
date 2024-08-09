package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.handlers.ExceptionRepresentation;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.services.UserService;
import com.openclassrooms.mddapi.validators.ObjectsValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
@SecurityRequirement(
        name = "bearerAuth"
)
@Tag(name = "Users")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final ObjectsValidator<UserDto> validator;
    private final UserService userService;


    @Operation(
            description = "Get endpoint to find a user by their ID. This endpoint retrieves the user's details using the provided user ID.",
            summary = "Find user by ID",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "User found successfully",
                            content = @Content(mediaType = "application/json",
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
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> findById(
            @PathVariable("userId") Integer userId
    ) {
        User user = userService.findById(userId);

        log.info("User found [OK]");
        return new ResponseEntity<>(UserDto.fromEntity(user), HttpStatus.OK);
    }

    @Operation(
            description = "PUT endpoint to update a user by their ID. This endpoint retrieves the user's details using the provided user ID.",
            summary = "Update user by ID",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "User modified successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserDto.class)
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
                    ),
                    @ApiResponse(responseCode = "404",
                            description = "User not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionRepresentation.class)
                            )
                    ),
                    @ApiResponse(responseCode = "406",
                            description = "User Operation not permitted",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionRepresentation.class)
                            )
                    )
            }
    )
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> update(
            @PathVariable("userId") Integer userId,
            @RequestBody UserDto userDto,
            Authentication authUser
    ) throws BadRequestException {
        User user = userService.searchByEmailOrUsername(authUser.getName());

        if (!user.getId().equals(userId)) {
            throw new BadRequestException("Username does not match");
        }

        validator.validate(userDto);
        User newUser = userService.update(userId, UserDto.toEntity(userDto));

        log.info("User updated [OK]");
        return new ResponseEntity<>(UserDto.fromEntity(newUser), HttpStatus.OK);
    }

}
