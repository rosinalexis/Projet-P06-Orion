package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.models.User;

import java.util.List;

public interface TopicService {

    Topic save(Topic topic);

    List<Topic> findAll();

    Topic findById(Integer id);

    User subscribe(Integer userId, Integer topicId);

    User unsubscribe(Integer userId, Integer topicId);
}
