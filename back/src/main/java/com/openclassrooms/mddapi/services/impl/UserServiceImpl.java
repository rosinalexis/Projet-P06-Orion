package com.openclassrooms.mddapi.services.impl;

import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repositories.UserRepository;
import com.openclassrooms.mddapi.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User findById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("User with id " + id + " not found")
                );
    }

    @Override
    public User save(User user) {
        Optional<User> findUser = userRepository.findByEmail(user.getEmail());
        if (findUser.isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        user.setUsername(user.getUsername().toLowerCase());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User update(Integer id, User user) {
        return userRepository.findById(id).map(
                oldUser -> {
                    oldUser.setUsername(user.getUsername());
                    oldUser.setEmail(user.getEmail());
                    return userRepository.save(oldUser);
                }).orElseThrow(() -> new EntityNotFoundException("Rental with id " + id + " not found"));
    }

    @Override
    public User searchByEmailOrUsername(String login) {
        return userRepository.searchByEmailOrUsername(login)
                .orElseThrow(
                        () -> new EntityNotFoundException("User with identification " + login + " is not found")
                );
    }
}
