package com.openclassrooms.mddapi.controllers;


import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.dto.auth.LoginRequestDto;
import com.openclassrooms.mddapi.dto.auth.RegistrationRequestDto;
import com.openclassrooms.mddapi.dto.auth.TokenDto;
import com.openclassrooms.mddapi.handlers.ExceptionRepresentation;
import com.openclassrooms.mddapi.models.Token;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.services.UserService;
import com.openclassrooms.mddapi.services.auth.AuthenticationService;
import com.openclassrooms.mddapi.validators.ObjectsValidator;
import io.swagger.v3.oas.annotations.Operation;
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

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentications")
public class AuthenticationController {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);
    private final UserService userService;
    private final ObjectsValidator<RegistrationRequestDto> validator;
    private final ObjectsValidator<LoginRequestDto> loginValidator;
    private final AuthenticationService authenticationService;

    @Operation(
            description = "Post endpoint for registering a new user. This endpoint validates the registration request, creates a new user with the provided details, saves the user to the database, generates an authentication token for the user, and returns the token.",
            summary = "Register a new user and generate an authentication token",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = TokenDto.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "401",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionRepresentation.class)
                            )
                    )
            }
    )
    @PostMapping("/register")
    public ResponseEntity<TokenDto> register(
            @RequestBody RegistrationRequestDto request
    ) {

        validator.validate(request);

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setUsername(request.getUsername());

        user = userService.save(user);

        Token token = authenticationService.generateToken(user);

        log.info("User registration [OK]");

        return new ResponseEntity<>(TokenDto.fromEntity(token), HttpStatus.OK);
    }

    @Operation(
            description = "Post endpoint for user login. This endpoint validates the login request, authenticates the user with the provided credentials, generates an authentication token for the user, and returns the token.",
            summary = "Authenticate a user and generate an authentication token",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Login successful, authentication token returned",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TokenDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Authentication failed",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionRepresentation.class)
                            )
                    )
            }
    )
    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(
            @RequestBody LoginRequestDto loginRequest
    ) {

        loginValidator.validate(loginRequest);
        
        Token token = authenticationService.authenticate(loginRequest);

        log.info("User login  with token [OK]");

        return new ResponseEntity<>(TokenDto.fromEntity(token), HttpStatus.OK);
    }

    @Operation(
            description = "Get endpoint to retrieve the current authenticated user's information. This endpoint requires a valid authentication token.",
            summary = "Retrieve current authenticated user information",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User information retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized, invalid or missing authentication token",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionRepresentation.class))
                    )
            }
    )
    @GetMapping("/me")
    @SecurityRequirement(
            name = "bearerAuth"
    )
    public ResponseEntity<UserDto> getCurrentUser(
            Authentication authUser
    ) {
        User user = userService.searchByEmailOrUsername(authUser.getName());

        log.info("User identification [OK]");

        return ResponseEntity.ok(UserDto.fromEntity(user));
    }

}
