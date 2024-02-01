package com.dannyhromau.monitoring.meter.service;

import com.dannyhromau.monitoring.meter.exception.DuplicateDataException;
import com.dannyhromau.monitoring.meter.exception.EntityNotFoundException;
import com.dannyhromau.monitoring.meter.model.User;

import java.util.List;

public interface UserService {
    User getUserById(long id) throws EntityNotFoundException;

    List<User> getAll();

    User add(User user) throws DuplicateDataException;

    long deleteUser(long id);

    User getUserByLogin(String login) throws EntityNotFoundException;
}
