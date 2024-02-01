package com.dannyhromau.monitoring.meter.repository.impl.console;

import com.dannyhromau.monitoring.meter.model.User;
import com.dannyhromau.monitoring.meter.repository.UserRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {

    private static Map<Long, User> userStorage = new HashMap<>();
    private static long generatedId = 1;

    @Override
    public Optional<User> findById(long id) {
        return Optional.of(userStorage.get(id));
    }

    @Override
    public List<User> findAll() {
        return userStorage.values().stream().toList();
    }

    @Override
    public User save(User user) {
        user.setId(generatedId);
        userStorage.put(user.getId(), user);
        generatedId++;
        return user;
    }

    @Override
    public void deleteById(long id) {
        if (userStorage.get(id) != null) {
            userStorage.get(id).setDeleted(true);
        }
    }

    @Override
    public Optional<User> findUserByLogin(String login) {
        return findAll()
                .stream()
                .filter(u -> u.getLogin().equals(login))
                .findFirst();
    }

    @Override
    public void deleteAll() {
        userStorage.clear();
    }

    public long resetIdGenerator() {
        return generatedId = 1;
    }
}
