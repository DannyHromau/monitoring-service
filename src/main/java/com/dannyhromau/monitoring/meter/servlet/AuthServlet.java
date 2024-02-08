package com.dannyhromau.monitoring.meter.servlet;

import com.dannyhromau.monitoring.meter.annotation.AspectAuditLogging;
import com.dannyhromau.monitoring.meter.annotation.AspectLogging;
import com.dannyhromau.monitoring.meter.api.ResponseEntity;
import com.dannyhromau.monitoring.meter.api.dto.AuthDto;
import com.dannyhromau.monitoring.meter.controller.AuthController;
import com.dannyhromau.monitoring.meter.core.util.ErrorStatusBuilder;
import com.dannyhromau.monitoring.meter.core.util.JsonConverter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@AspectLogging
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
            ResponseEntity<AuthDto> re = authController.authorize(authDto);
            resp = ErrorStatusBuilder.setHttpStatus(resp, re.getSystemMessage());
            if (re.getBody() != null){
                HttpSession session = req.getSession();
                session.setAttribute("authorities", re.getBody().getAuthorities());
            }
            JsonConverter.sendAsJson(resp, re.getBody());
        } else if (pathInfo.equals("/register")) {
            ResponseEntity<Boolean> re = authController.register(authDto);
            resp = ErrorStatusBuilder.setHttpStatus(resp, re.getSystemMessage());
            JsonConverter.sendAsJson(resp, re.getBody());
        }
    }

}
