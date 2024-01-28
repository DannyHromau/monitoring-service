package com.dannyhromau.monitoring.meter;

import com.dannyhromau.monitoring.meter.core.util.ErrorMessages;
import com.dannyhromau.monitoring.meter.exception.DuplicateDataException;
import com.dannyhromau.monitoring.meter.exception.EntityNotFoundException;
import com.dannyhromau.monitoring.meter.model.User;
import com.dannyhromau.monitoring.meter.repository.UserRepository;
import com.dannyhromau.monitoring.meter.service.UserService;
import com.dannyhromau.monitoring.meter.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@DisplayName("Testing of user_service")
public class UserServiceImplTest {
    private UserRepository userRepository = Mockito.mock(UserRepository.class);
    private UserService userService = new UserServiceImpl(userRepository);

    @Test
    @DisplayName("add user when exists")
    void addUserWhenExists() {
        User user = new User();
        user.setId(100);
        user.setLogin("login");
        user.setPassword("password");
        when(userRepository.findUserByLogin(user.getLogin()))
                .thenReturn(Optional.of(user));
        Exception exception = assertThrows(DuplicateDataException.class, () ->
                userService.add(user));
        String expectedMessage = ErrorMessages.DUPLICATED_DATA_MESSAGE.label;
        String actualMessage = exception.getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("get user by id when not exists")
    void getUserByIdWhenNotExists() {
        when(userRepository.findById(1))
                .thenReturn(Optional.empty());
        Exception exception = assertThrows(EntityNotFoundException.class, () ->
                userService.getUserById(1));
        String expectedMessage = String.format(ErrorMessages.ENTITY_NOT_FOUND_MESSAGE.label, "id", 1);
        String actualMessage = exception.getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("get user by login when not exists")
    void getUserByLoginWhenNotExists() {
        String login = "login";
        when(userRepository.findUserByLogin(login))
                .thenReturn(Optional.empty());
        Exception exception = assertThrows(EntityNotFoundException.class, () ->
                userService.getUserByLogin(login));
        String expectedMessage = String.format(ErrorMessages.ENTITY_NOT_FOUND_MESSAGE.label, "login", login);
        String actualMessage = exception.getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);
    }
}
