package com.dannyhromau.monitoring.meter;

import com.dannyhromau.monitoring.meter.core.util.ErrorMessages;
import com.dannyhromau.monitoring.meter.exception.DuplicateDataException;
import com.dannyhromau.monitoring.meter.exception.EntityNotFoundException;
import com.dannyhromau.monitoring.meter.exception.InvalidDataException;
import com.dannyhromau.monitoring.meter.exception.UnAuthorizedException;
import com.dannyhromau.monitoring.meter.model.User;
import com.dannyhromau.monitoring.meter.service.AuthService;
import com.dannyhromau.monitoring.meter.service.AuthorityService;
import com.dannyhromau.monitoring.meter.service.UserService;
import com.dannyhromau.monitoring.meter.service.impl.AuthorityServiceImpl;
import com.dannyhromau.monitoring.meter.service.impl.ConsoleAuthServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@DisplayName("Testing of auth_service")
public class AuthServiceImplTest {
    private UserService userService = Mockito.mock(UserService.class);
    private AuthorityService authorityService = Mockito.mock(AuthorityService.class);
    private AuthService<User> authService = new ConsoleAuthServiceImpl(userService, authorityService);

    @Test
    @DisplayName("register user when exists")
    void registerUserWhenExists() throws DuplicateDataException {
        User user = new User();
        user.setId(100);
        user.setLogin("login");
        user.setPassword("password");
        when(userService.add(user)).thenThrow(new DuplicateDataException(ErrorMessages.DUPLICATED_DATA_MESSAGE.label));
        Exception exception = assertThrows(DuplicateDataException.class, () ->
                authService.register(user));
        String expectedMessage = ErrorMessages.DUPLICATED_DATA_MESSAGE.label;
        String actualMessage = exception.getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("register user when invalid login format")
    void registerUserWhenInvalidLoginFormat() {
        User user = new User();
        user.setId(100);
        user.setLogin("lo");
        user.setPassword("password");
        Exception exception = assertThrows(InvalidDataException.class, () ->
                authService.register(user));
        String expectedMessage = ErrorMessages.WRONG_INPUT_FORMAT_MESSAGE.label;
        String actualMessage = exception.getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("register user when invalid password format")
    void registerUserWhenInvalidPasswordFormat() {
        User user = new User();
        user.setId(100);
        user.setLogin("login");
        user.setPassword("pass");
        Exception exception = assertThrows(InvalidDataException.class, () ->
                authService.register(user));
        String expectedMessage = ErrorMessages.WRONG_INPUT_FORMAT_MESSAGE.label;
        String actualMessage = exception.getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("authorize user when not exists")
    void authorizeUserWhenNotExists() throws EntityNotFoundException {
        User user = new User();
        user.setId(100);
        user.setLogin("login");
        user.setPassword("password");
        when(userService.getUserByLogin(user.getLogin())).thenThrow(new EntityNotFoundException(
                String.format(ErrorMessages.ENTITY_NOT_FOUND_MESSAGE.label, "login", user.getLogin())
        ));
        Exception exception = assertThrows(UnAuthorizedException.class, () ->
                authService.authorize(user));
        String expectedMessage = ErrorMessages.WRONG_AUTH_MESSAGE.label;
        String actualMessage = exception.getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("authorize user when exists")
    void authorizeUserWhenExists() throws UnAuthorizedException, EntityNotFoundException {
        User user = new User();
        user.setId(100);
        user.setLogin("login");
        user.setPassword("password");
        when(userService.getUserByLogin(user.getLogin())).thenReturn(user);
        User actualUser = authService.authorize(user);
        Assertions.assertEquals(user, actualUser);
    }

}
