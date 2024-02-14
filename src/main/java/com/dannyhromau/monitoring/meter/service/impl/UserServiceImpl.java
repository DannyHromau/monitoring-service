package com.dannyhromau.monitoring.meter.service.impl;

import com.dannyhromau.monitoring.meter.annotation.AspectLogging;
import com.dannyhromau.monitoring.meter.core.util.ErrorMessages;
import com.dannyhromau.monitoring.meter.exception.DuplicateDataException;
import com.dannyhromau.monitoring.meter.exception.EntityNotFoundException;
import com.dannyhromau.monitoring.meter.model.User;
import com.dannyhromau.monitoring.meter.repository.UserRepository;
import com.dannyhromau.monitoring.meter.service.UserService;
import lombok.RequiredArgsConstructor;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@AspectLogging
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private static final String ENTITY_NOT_FOUND_MESSAGE = ErrorMessages.ENTITY_NOT_FOUND_MESSAGE.label;
    private static final String DUPLICATE_DATA_MESSAGE = ErrorMessages.DUPLICATED_DATA_MESSAGE.label;


    @Override
    public User getUserById(long id) throws EntityNotFoundException, SQLException {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format(ENTITY_NOT_FOUND_MESSAGE, "id", id)));
    }

    @Override
    public List<User> getAll() throws SQLException {
        return userRepository.findAll();
    }

    @Override
    public User add(User user) throws DuplicateDataException, SQLException {
        Optional<User> userOpt = userRepository.findUserByLogin(user.getLogin());
        if (userOpt.isPresent()) {
            throw new DuplicateDataException(DUPLICATE_DATA_MESSAGE);
        } else {
            user = userRepository.save(user);
        }
        return user;
    }

    @Override
    public long deleteUser(long id) {
        userRepository.deleteById(id);
        return id;
    }

    @Override
    public User getUserByLogin(String login) throws EntityNotFoundException, SQLException {
        return userRepository.findUserByLogin(login).orElseThrow(
                () -> new EntityNotFoundException(String.format(ENTITY_NOT_FOUND_MESSAGE, "login", login)));
    }
}
