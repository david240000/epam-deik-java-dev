package com.epam.training.ticketservice.core.user.impl;

import com.epam.training.ticketservice.core.user.UserService;
import com.epam.training.ticketservice.core.user.model.UserDto;
import com.epam.training.ticketservice.core.user.persistence.entity.User;
import com.epam.training.ticketservice.core.user.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserDto loggedInUser = null;
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserServiceImpl(UserDto loggedInUser, UserRepository userRepository) {
        this.loggedInUser = loggedInUser;
        this.userRepository = userRepository;
    }

    @Override
    public Optional<UserDto> login(String username, String password) {
        Objects.requireNonNull(username, "Username cannot be null during login!");
        Objects.requireNonNull(password, "Password cannot be null during login!");
        loggedInUser = retrieveUserInfoByNameAndPassword(username, password);
        return getLoggedInUser();
    }

    @Override
    public Optional<UserDto> logout() {
        Optional<UserDto> previouslyLoggedInUser = getLoggedInUser();
        loggedInUser = null;
        return previouslyLoggedInUser;
    }

    @Override
    public Optional<UserDto> getLoggedInUser() {
        return Optional.ofNullable(loggedInUser);
    }

    private UserDto retrieveUserInfoByNameAndPassword(String username, String password) {
        Optional<User> user = userRepository.findByUsernameAndPassword(username, password);
        if (user.isEmpty()) {
            return null;
        }
        return new UserDto(user.get().getUsername(), user.get().getRole());
    }
}
