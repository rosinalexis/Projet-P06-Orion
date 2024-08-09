package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.services.TopicService;
import com.openclassrooms.mddapi.validators.ObjectsValidator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("topics")
@RequiredArgsConstructor
public class TopicController {
    private static final Logger log = LoggerFactory.getLogger(TopicController.class);
    private final ObjectsValidator<TopicDto> validator;
    private final TopicService topicService;

    @GetMapping()
    public ResponseEntity<List<TopicDto>> findAll() {

        List<Topic> topicList = topicService.findAll();
        return new ResponseEntity<>(TopicDto.fromEntity(topicList), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TopicDto> save(
            @RequestBody TopicDto topicDto
    ) {
        validator.validate(topicDto);
        Topic newTopic = topicService.save(TopicDto.toEntity(topicDto));

        log.info("Topic saved: {}", newTopic.getId());
        return new ResponseEntity<>(TopicDto.fromEntity(newTopic), HttpStatus.CREATED);
    }

    @PutMapping("/{topicId}/subscribe/{userId}")
    public ResponseEntity<UserDto> subscribe(
            @PathVariable Integer topicId,
            @PathVariable Integer userId
    ) {
        User user = topicService.subscribe(userId, topicId);

        log.info("Topic subscribed: {}", user.getId());
        return new ResponseEntity<>(UserDto.fromEntity(user), HttpStatus.OK);
    }

    @DeleteMapping("/{topicId}/unsubscribe/{userId}")
    public ResponseEntity<UserDto> unsubscribe(
            @PathVariable Integer topicId,
            @PathVariable Integer userId
    ) {
        User user = topicService.unsubscribe(userId, topicId);

        log.info("Topic unsubscribed: {}", user.getId());
        return new ResponseEntity<>(UserDto.fromEntity(user), HttpStatus.OK);
    }

}
