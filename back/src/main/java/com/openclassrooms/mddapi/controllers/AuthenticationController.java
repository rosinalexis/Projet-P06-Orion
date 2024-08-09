package com.openclassrooms.mddapi.controllers;


import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.dto.auth.LoginRequestDto;
import com.openclassrooms.mddapi.dto.auth.RegistrationRequestDto;
import com.openclassrooms.mddapi.dto.auth.TokenDto;
import com.openclassrooms.mddapi.models.Token;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.services.UserService;
import com.openclassrooms.mddapi.services.auth.AuthenticationService;
import com.openclassrooms.mddapi.validators.ObjectsValidator;
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
public class AuthenticationController {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);
    private final UserService userService;
    private final ObjectsValidator<RegistrationRequestDto> validator;
    private final ObjectsValidator<LoginRequestDto> loginValidator;
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<TokenDto> register(
            @RequestBody RegistrationRequestDto request) {

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

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(
            @RequestBody LoginRequestDto loginRequest
    ) {

        loginValidator.validate(loginRequest);

        Token token = authenticationService.authenticate(loginRequest);

        log.info("User login  with token [OK]");

        return new ResponseEntity<>(TokenDto.fromEntity(token), HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser(
            Authentication authUser
    ) {
        User user = userService.searchByEmailOrUsername(authUser.getName());

        log.info("User identification [OK]");

        return ResponseEntity.ok(UserDto.fromEntity(user));
    }

}
