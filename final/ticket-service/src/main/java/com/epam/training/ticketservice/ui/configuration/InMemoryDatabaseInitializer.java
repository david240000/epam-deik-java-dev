package com.epam.training.ticketservice.ui.configuration;

import com.epam.training.ticketservice.core.user.persistence.entity.User;
import com.epam.training.ticketservice.core.user.persistence.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class InMemoryDatabaseInitializer {

    private final UserRepository userRepository;

    public InMemoryDatabaseInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public  void init() {
        User admin = new User("admin","admin", User.Role.ADMIN);
        userRepository.save(admin);
    }
}
