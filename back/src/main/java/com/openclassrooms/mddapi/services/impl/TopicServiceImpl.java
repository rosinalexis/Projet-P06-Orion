package com.openclassrooms.mddapi.services.impl;

import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repositories.TopicRepository;
import com.openclassrooms.mddapi.services.TopicService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;
    private final UserServiceImpl userServiceImpl;

    @Override
    public Topic save(Topic topic) {
        return topicRepository.save(topic);
    }

    @Override
    public List<Topic> findAll() {
        return topicRepository.findAll();
    }

    @Override
    public Topic findById(Integer id) {
        return topicRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Topic with id " + id + " not found"));
    }

    @Override
    public User subscribe(Integer userId, Integer topicId) {
        Optional<Topic> currentTopic = topicRepository.findById(topicId);
        User currentUser = userServiceImpl.findById(userId);

        if (currentTopic.isEmpty()) {
            throw new IllegalArgumentException("Topic with id " + topicId + " not found");
        }

        if (currentUser.getSubscriptions().contains(currentTopic.get())) {
            throw new IllegalArgumentException("already subscribed to " + currentTopic.get().getName());
        }

        currentUser.getSubscriptions().add(currentTopic.get());
        return userServiceImpl.update(currentUser.getId(), currentUser);
    }

    @Override
    public User unsubscribe(Integer userId, Integer topicId) {
        Optional<Topic> currentTopic = topicRepository.findById(topicId);
        User currentUser = userServiceImpl.findById(userId);

        if (currentTopic.isEmpty()) {
            throw new IllegalArgumentException("Topic with id " + topicId + " not found");
        }

        if (!currentUser.getSubscriptions().contains(currentTopic.get())) {
            throw new IllegalArgumentException("user is not subscribed to " + currentTopic.get().getName());
        }

        currentUser.getSubscriptions().remove(currentTopic.get());
        return userServiceImpl.update(currentUser.getId(), currentUser);
    }

}
