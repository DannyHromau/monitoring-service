package com.dannyhromau.monitoring.meter.servlet;

import com.dannyhromau.monitoring.meter.api.dto.AuthDto;
import com.dannyhromau.monitoring.meter.controller.AuthController;
import com.dannyhromau.monitoring.meter.core.util.ErrorStatusBuilder;
import com.dannyhromau.monitoring.meter.core.util.JsonConverter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "MeterTypeServlet", urlPatterns = {"/api/v1/auth/*"})
public class AuthServlet extends HttpServlet {

    private AuthController<AuthDto> authController;

    @Override
    public void init() throws ServletException {
        authController = (AuthController<AuthDto>) getServletContext().getAttribute("authController");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        AuthDto authDto = null;
        if (pathInfo == null || pathInfo.equals("/")) {
            JsonConverter.sendAsJson(resp, "");
        } else {
            try {
                Object o = JsonConverter.fromJson(req);
                authDto = (AuthDto) o;
            } catch (ClassCastException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                JsonConverter.sendAsJson(resp, null);
            }
        }
        if (pathInfo.equals("/login")) {
            resp = ErrorStatusBuilder.setHttpStatus(resp, authController.authorize(authDto).getSystemMessage());
            JsonConverter.sendAsJson(resp, authController.authorize(authDto));
        } else if (pathInfo.equals("/register")) {
            resp = ErrorStatusBuilder.setHttpStatus(resp, authController.register(authDto).getSystemMessage());
            JsonConverter.sendAsJson(resp, authController.register(authDto));
        }
    }

}
