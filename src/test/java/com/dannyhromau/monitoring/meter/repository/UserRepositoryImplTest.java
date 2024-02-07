package com.dannyhromau.monitoring.meter.repository;

import com.dannyhromau.monitoring.meter.model.User;
import com.dannyhromau.monitoring.meter.repository.impl.console.UserRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Testing of user_repository")
public class UserRepositoryImplTest {
    private UserRepositoryImpl userRepository = new UserRepositoryImpl();
    private User user = new User();

    @BeforeEach
    void setup(){
        userRepository.deleteAll();
        userRepository.resetIdGenerator();
        user.setLogin("login");
        user.setPassword("password");
        user.setDeleted(false);
    }

    @Test
    @DisplayName("find user by id when empty repository")
    void getUserByIdWhenNotExists(){
        userRepository.save(user);
        int expectedSize = 1;
        int actualSize = userRepository.findAll().size();
        Assertions.assertEquals(expectedSize, actualSize);
    }
    @Test
    @DisplayName("check incrementing id when add user")
    void incrementIdWhenAddUser(){
        userRepository.save(user);
        User userNext = new User();
        userRepository.save(userNext);
        long expectedId = 2;
        long actualId = userRepository.findAll().get(1).getId();
        Assertions.assertEquals(expectedId, actualId);
    }

    @Test
    @DisplayName("find user by login when exists")
    void findUserByLoginWhenExists(){
        User expectedUser = user;
        userRepository.save(user);
        User actualUser = userRepository.findUserByLogin(user.getLogin()).get();
        Assertions.assertEquals(expectedUser, actualUser);
    }
    @Test
    @DisplayName("delete user when exists")
    void deleteUserWhenExists(){
        userRepository.save(user);
        userRepository.save(new User());
        long id = userRepository.findUserByLogin(user.getLogin()).get().getId();
        userRepository.deleteById(id);
        User actualUser = userRepository.findById(id).get();
        boolean expectedStatus = true;
        boolean actualStatus = actualUser.isDeleted();
        Assertions.assertEquals(expectedStatus, actualStatus);
    }
}
