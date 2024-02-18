package com.dannyhromau.monitoring.meter.controller;


import com.dannyhromau.monitoring.meter.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public interface UserController {
    ResponseEntity<User> getUserById(long id);

    ResponseEntity<List<User>> getAll();

    ResponseEntity<User> add(User user);

    ResponseEntity<Long> deleteUser(long id);

    ResponseEntity<User> getUserByLogin(String login);
}
