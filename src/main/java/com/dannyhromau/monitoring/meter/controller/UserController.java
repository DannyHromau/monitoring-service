package com.dannyhromau.monitoring.meter.controller;

import com.dannyhromau.monitoring.meter.api.ResponseEntity;
import com.dannyhromau.monitoring.meter.model.User;

import java.util.List;

public interface UserController {
    ResponseEntity<User> getUserById(long id);
    ResponseEntity<List<User>> getAll();
    ResponseEntity<User> add(User user);
    ResponseEntity<Long> deleteUser(long id);
    ResponseEntity<User> getUserByLogin(String login);
}
