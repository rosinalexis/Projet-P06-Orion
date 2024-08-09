package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.services.UserService;
import com.openclassrooms.mddapi.validators.ObjectsValidator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final ObjectsValidator<UserDto> validator;
    private final UserService userService;


    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> findById(
            @PathVariable("userId") Integer userId
    ) {
        User user = userService.findById(userId);

        log.info("User found: {}", user.getId());
        return new ResponseEntity<>(UserDto.fromEntity(user), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDto> save(
            @RequestBody UserDto userDto
    ) {
        validator.validate(userDto);
        User newUser = userService.save(UserDto.toEntity(userDto));

        log.info("User saved: {}", newUser.getId());
        return new ResponseEntity<>(UserDto.fromEntity(newUser), HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> save(
            @PathVariable("userId") Integer userId,
            @RequestBody UserDto userDto
    ) {
        validator.validate(userDto);
        User newUser = userService.update(userId, UserDto.toEntity(userDto));

        log.info("User updated: {}", newUser.getId());
        return new ResponseEntity<>(UserDto.fromEntity(newUser), HttpStatus.OK);
    }


}
