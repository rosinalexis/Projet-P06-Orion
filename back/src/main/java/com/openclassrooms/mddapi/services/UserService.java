package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.models.User;

public interface UserService {

    User findById(Integer id);

    User save(User user);

    User update(Integer id, User user);

    User searchByEmailOrUsername(String login);

}
