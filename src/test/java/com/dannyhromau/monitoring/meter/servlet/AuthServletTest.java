package com.dannyhromau.monitoring.meter.servlet;

import com.dannyhromau.monitoring.meter.api.ResponseEntity;
import com.dannyhromau.monitoring.meter.api.dto.AuthDto;
import com.dannyhromau.monitoring.meter.controller.AuthController;
import com.dannyhromau.monitoring.meter.core.util.JsonConverter;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
@DisplayName("Testing of auth_service")
public class AuthServletTest {


    private HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

    private HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

    private AuthController<AuthDto> authController = Mockito.mock(AuthController.class);
    private AuthServlet authServlet = new AuthServlet();


    @Test
    public void getStatusOkWhenRegister() throws ServletException, IOException {
        AuthDto authDto = new AuthDto();
        ResponseEntity<AuthDto> re = new ResponseEntity<>();
        re.setSystemMessage("ok");
        re.setBody(authDto);
        when(request.getPathInfo()).thenReturn("/login");
        MockedStatic<JsonConverter> mockStatic = Mockito.mockStatic(JsonConverter.class);
        mockStatic.when(() -> JsonConverter.fromJson(request)).thenReturn(authDto);
        when(authController.authorize(authDto)).thenReturn(re);
        authServlet.doPost(request, response);
        verify(response, times(1)).getStatus();
    }
}
