package com.dannyhromau.monitoring.meter.service;

import com.dannyhromau.monitoring.meter.core.util.ErrorMessages;
import com.dannyhromau.monitoring.meter.exception.DuplicateDataException;
import com.dannyhromau.monitoring.meter.exception.EntityNotFoundException;
import com.dannyhromau.monitoring.meter.model.User;
import com.dannyhromau.monitoring.meter.repository.UserRepository;
import com.dannyhromau.monitoring.meter.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;


@ExtendWith({MockitoExtension.class})
@DisplayName("Testing of user_service")
public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    @DisplayName("add user when exists")
    void addUserWhenExists() throws SQLException {
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
    void getUserByIdWhenNotExists() throws SQLException {
        when(userRepository.findById(UUID.randomUUID()))
                .thenReturn(Optional.empty());
        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> userService.getUserById(UUID.randomUUID()))
                .withMessage(ErrorMessages.ENTITY_NOT_FOUND_MESSAGE.label, "id", 1);
    }

    @Test
    @DisplayName("get user by login when not exists")
    void getUserByLoginWhenNotExists() throws SQLException {
        String login = "login";
        when(userRepository.findByLogin(login))
                .thenReturn(Optional.empty());
        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> userService.getUserByLogin(login))
                .withMessage(ErrorMessages.ENTITY_NOT_FOUND_MESSAGE.label, "login", login);
    }
}
