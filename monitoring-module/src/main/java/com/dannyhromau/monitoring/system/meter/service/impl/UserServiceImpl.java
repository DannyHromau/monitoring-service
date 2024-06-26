package com.dannyhromau.monitoring.system.meter.service.impl;

import com.dannyhromau.monitoring.system.meter.core.util.ErrorMessages;
import com.dannyhromau.monitoring.system.meter.exception.DuplicateDataException;
import com.dannyhromau.monitoring.system.meter.exception.EntityNotFoundException;
import com.dannyhromau.monitoring.system.meter.model.Authority;
import com.dannyhromau.monitoring.system.meter.model.User;
import com.dannyhromau.monitoring.system.meter.model.UserAuthority;
import com.dannyhromau.monitoring.system.meter.repository.UserAuthorityRepository;
import com.dannyhromau.monitoring.system.meter.repository.UserRepository;
import com.dannyhromau.monitoring.system.meter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserAuthorityRepository userAuthorityRepository;
    private static final String ENTITY_NOT_FOUND_MESSAGE = ErrorMessages.ENTITY_NOT_FOUND_MESSAGE.label;
    private static final String DUPLICATE_DATA_MESSAGE = ErrorMessages.DUPLICATED_DATA_MESSAGE.label;


    @Override
    public User getUserById(UUID id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format(ENTITY_NOT_FOUND_MESSAGE, "id", id)));
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User add(User user) {
        Optional<User> userOpt = userRepository.findByLogin(user.getLogin());
        if (userOpt.isPresent()) {
            throw new DuplicateDataException(DUPLICATE_DATA_MESSAGE);
        } else {
            user = userRepository.save(user);
            List<UserAuthority> userAuthorities = new ArrayList<>();
            for (Authority authority : user.getAuthorities()){
                userAuthorities.add(new UserAuthority(user.getId(), authority.getId()));
            }
        }
        return user;
    }

    @Override
    public UUID deleteUser(UUID id) {
        userRepository.deleteById(id);
        return id;
    }

    @Override
    public User getUserByLogin(String login) {
        return userRepository.findByLogin(login).orElseThrow(
                () -> new EntityNotFoundException(String.format(ENTITY_NOT_FOUND_MESSAGE, "login", login)));
    }
}
