package com.dannyhromau.monitoring.meter.service;

import com.dannyhromau.monitoring.meter.core.util.ErrorMessages;
import com.dannyhromau.monitoring.meter.exception.DuplicateDataException;
import com.dannyhromau.monitoring.meter.exception.EntityNotFoundException;
import com.dannyhromau.monitoring.meter.exception.InvalidDataException;
import com.dannyhromau.monitoring.meter.exception.UnAuthorizedException;
import com.dannyhromau.monitoring.meter.model.User;
import com.dannyhromau.monitoring.meter.service.impl.ConsoleAuthServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
@DisplayName("Testing of auth_service")
public class AuthServiceImplTest {
    @Mock
    private UserService userService;
    @Mock
    private AuthorityService authorityService;
    private AuthService<User> authService;

    @BeforeEach
    void setUp() {
        authService = new ConsoleAuthServiceImpl(userService, authorityService);
    }


    @Test
    @DisplayName("register user when exists")
    void registerUserWhenExists() throws DuplicateDataException, SQLException {
        User user = new User();
        user.setId(100L);
        user.setLogin("login");
        user.setPassword("password");
        when(userService.add(user)).thenThrow(new DuplicateDataException(ErrorMessages.DUPLICATED_DATA_MESSAGE.label));
        assertThatExceptionOfType(DuplicateDataException.class)
                .isThrownBy(() -> authService.register(user))
                .withMessage(ErrorMessages.DUPLICATED_DATA_MESSAGE.label);
    }

    @Test
    @DisplayName("register user when invalid login format")
    void registerUserWhenInvalidLoginFormat() {
        User user = new User();
        user.setId(100L);
        user.setLogin("lo");
        user.setPassword("password");
        assertThatExceptionOfType(InvalidDataException.class)
                .isThrownBy(() -> authService.register(user))
                .withMessage(ErrorMessages.WRONG_INPUT_FORMAT_MESSAGE.label);
    }

    @Test
    @DisplayName("register user when invalid password format")
    void registerUserWhenInvalidPasswordFormat() {
        User user = new User();
        user.setId(100L);
        user.setLogin("login");
        user.setPassword("pass");
        assertThatExceptionOfType(InvalidDataException.class)
                .isThrownBy(() -> authService.register(user))
                .withMessage(ErrorMessages.WRONG_INPUT_FORMAT_MESSAGE.label);
    }

    @Test
    @DisplayName("authorize user when not exists")
    void authorizeUserWhenNotExists() throws EntityNotFoundException, SQLException {
        User user = new User();
        user.setId(100L);
        user.setLogin("login");
        user.setPassword("password");
        when(userService.getUserByLogin(user.getLogin())).thenThrow(new EntityNotFoundException(
                String.format(ErrorMessages.ENTITY_NOT_FOUND_MESSAGE.label, "login", user.getLogin())
        ));
        assertThatExceptionOfType(UnAuthorizedException.class)
                .isThrownBy(() -> authService.authorize(user))
                .withMessage(ErrorMessages.WRONG_AUTH_MESSAGE.label);
    }

    @Test
    @DisplayName("authorize user when exists")
    void authorizeUserWhenExists() throws UnAuthorizedException, SQLException, EntityNotFoundException {
        User user = new User();
        user.setId(100L);
        user.setLogin("login");
        user.setPassword("password");
        when(userService.getUserByLogin(user.getLogin())).thenReturn(user);
        User actualUser = authService.authorize(user);
        Assertions.assertEquals(user, actualUser);
    }

}
