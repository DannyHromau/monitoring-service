package com.dannyhromau.monitoring.meter.service.impl;

import com.dannyhromau.monitoring.meter.core.util.ErrorMessages;
import com.dannyhromau.monitoring.meter.exception.DuplicateDataException;
import com.dannyhromau.monitoring.meter.exception.EntityNotFoundException;
import com.dannyhromau.monitoring.meter.model.User;
import com.dannyhromau.monitoring.meter.repository.UserRepository;
import com.dannyhromau.monitoring.meter.service.UserService;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private static final String ENTITY_NOT_FOUND_MESSAGE = ErrorMessages.ENTITY_NOT_FOUND_MESSAGE.label;
    private static final String DUPLICATE_DATA_MESSAGE = ErrorMessages.DUPLICATED_DATA_MESSAGE.label;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserById(long id) throws EntityNotFoundException {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format(ENTITY_NOT_FOUND_MESSAGE, "id", id)));
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User add(User user) throws DuplicateDataException {
        Optional<User> userOpt = userRepository.findUserByLogin(user.getLogin());
        if (userOpt.isPresent()) {
            throw new DuplicateDataException(DUPLICATE_DATA_MESSAGE);
        } else {
            user = userRepository.add(user);
        }
        return user;
    }

    @Override
    public long deleteUser(long id) {
        userRepository.deleteById(id);
        return id;
    }

    @Override
    public User getUserByLogin(String login) throws EntityNotFoundException {
        return userRepository.findUserByLogin(login).orElseThrow(
                () -> new EntityNotFoundException(String.format(ENTITY_NOT_FOUND_MESSAGE, "login", login)));
    }
}
