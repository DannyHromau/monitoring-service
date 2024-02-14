package com.dannyhromau.monitoring.meter.servlet;

import com.dannyhromau.monitoring.meter.api.ResponseEntity;
import com.dannyhromau.monitoring.meter.api.dto.AuthDto;
import com.dannyhromau.monitoring.meter.controller.AuthController;
import com.dannyhromau.monitoring.meter.core.util.JsonConverter;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
@DisplayName("Testing of auth_servlet")
public class AuthServletTest {


    private HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

    private HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

    private AuthController<AuthDto> authController = Mockito.mock(AuthController.class);
    private HttpSession session = Mockito.mock(HttpSession.class);
    private AuthServlet authServlet = new AuthServlet();
    private ServletConfig servletConfig = Mockito.mock(ServletConfig.class);
    private ServletContext servletContext = Mockito.mock(ServletContext.class);

    @Before
    public void setup() throws ServletException {
        MockitoAnnotations.initMocks(this);
        when(servletConfig.getServletContext()).thenReturn(servletContext);
        when(servletContext.getAttribute("authController")).thenReturn(authController);
        authServlet = new AuthServlet();
        authServlet.init(servletConfig);
    }

    @Test
    @DisplayName("Get status ok when login")
    public void getStatusOkWhenLogin() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        AuthDto authDto = new AuthDto();
        ResponseEntity<AuthDto> re = new ResponseEntity<>();
        re.setSystemMessage("ok");
        re.setBody(authDto);
        when(request.getPathInfo()).thenReturn("/login");
        MockedStatic<JsonConverter> mockStatic = Mockito.mockStatic(JsonConverter.class);
        mockStatic.when(() -> JsonConverter.fromJson(request)).thenReturn(authDto);
        when(authController.authorize(authDto)).thenReturn(re);
        authServlet.doPost(request, response);
        mockStatic.close();
        verify(response, times(1)).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    @DisplayName("Get status ok when register")
    public void getStatusOkWhenRegister() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        AuthDto authDto = new AuthDto();
        ResponseEntity<Boolean> re = new ResponseEntity<>();
        re.setSystemMessage("ok");
        re.setBody(true);
        when(request.getPathInfo()).thenReturn("/register");
        MockedStatic<JsonConverter> mockStatic = Mockito.mockStatic(JsonConverter.class);
        mockStatic.when(() -> JsonConverter.fromJson(request)).thenReturn(authDto);
        when(authController.register(authDto)).thenReturn(re);
        authServlet.doPost(request, response);
        mockStatic.close();
        verify(response, times(1)).setStatus(HttpServletResponse.SC_OK);
    }
}
