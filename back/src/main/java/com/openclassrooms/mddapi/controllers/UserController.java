package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.services.UserService;
import com.openclassrooms.mddapi.validators.ObjectsValidator;
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
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final ObjectsValidator<UserDto> validator;
    private final UserService userService;


    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> findById(
            @PathVariable("userId") Integer userId
    ) {
        User user = userService.findById(userId);

        log.info("User found [OK]");
        return new ResponseEntity<>(UserDto.fromEntity(user), HttpStatus.OK);
    }

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
