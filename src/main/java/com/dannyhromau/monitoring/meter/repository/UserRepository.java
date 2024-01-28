package com.dannyhromau.monitoring.meter.repository;

import com.dannyhromau.monitoring.meter.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> findById(long id);

    List<User> findAll();

    User add(User user);

    void deleteById(long id);

    Optional<User> findUserByLogin(String login);

    void deleteAll();
}
