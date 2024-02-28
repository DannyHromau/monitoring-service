package com.dannyhromau.monitoring.meter.service;

import com.dannyhromau.monitoring.system.meter.Application;
import com.dannyhromau.monitoring.system.meter.core.util.ErrorMessages;
import com.dannyhromau.monitoring.system.meter.exception.DuplicateDataException;
import com.dannyhromau.monitoring.system.meter.exception.EntityNotFoundException;
import com.dannyhromau.monitoring.system.meter.model.User;
import com.dannyhromau.monitoring.system.meter.repository.UserAuthorityRepository;
import com.dannyhromau.monitoring.system.meter.repository.UserRepository;
import com.dannyhromau.monitoring.system.meter.service.UserService;
import com.dannyhromau.monitoring.system.meter.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;


@SpringBootTest(classes = Application.class)
@DisplayName("Testing of user_service")
public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    private UserService userService;
    private UserAuthorityRepository userAuthorityRepository;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository, userAuthorityRepository);
    }

    @Test
    @DisplayName("add user when exists")
    void addUserWhenExists() {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setLogin("login");
        user.setPassword("password");
        when(userRepository.findByLogin(user.getLogin()))
                .thenReturn(Optional.of(user));
        assertThatExceptionOfType(DuplicateDataException.class)
                .isThrownBy(() -> userService.add(user))
                .withMessage(ErrorMessages.DUPLICATED_DATA_MESSAGE.label);
    }

    @Test
    @DisplayName("get user by id when not exists")
    void getUserByIdWhenNotExists() {
        UUID id = UUID.randomUUID();
        when(userRepository.findById(id))
                .thenReturn(Optional.empty());
        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> userService.getUserById(id))
                .withMessage(ErrorMessages.ENTITY_NOT_FOUND_MESSAGE.label, "id", id);
    }

    @Test
    @DisplayName("get user by login when not exists")
    void getUserByLoginWhenNotExists() {
        String login = "login";
        when(userRepository.findByLogin(login))
                .thenReturn(Optional.empty());
        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> userService.getUserByLogin(login))
                .withMessage(ErrorMessages.ENTITY_NOT_FOUND_MESSAGE.label, "login", login);
    }
}
