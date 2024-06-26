package com.dannyhromau.monitoring.system.meter.service;

import com.dannyhromau.monitoring.system.meter.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface UserService {
    User getUserById(UUID id);

    List<User> getAll() ;

    User add(User user);

    UUID deleteUser(UUID id);

    User getUserByLogin(String login);
}
