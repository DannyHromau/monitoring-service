package com.dannyhromau.monitoring.meter.servlet;

import com.dannyhromau.monitoring.meter.api.dto.MeterTypeDto;
import com.dannyhromau.monitoring.meter.controller.MeterTypeController;
import com.dannyhromau.monitoring.meter.core.util.ErrorStatusBuilder;
import com.dannyhromau.monitoring.meter.core.util.JsonConverter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "MeterTypeServlet", urlPatterns = {"/api/v1/meter/type/*"})
public class MeterTypeServlet extends HttpServlet {

    private MeterTypeController meterTypeController;

    @Override
    public void init() throws ServletException {
        meterTypeController = (MeterTypeController) getServletContext().getAttribute("meterTypeController");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            JsonConverter.sendAsJson(resp, "");
        } else if (pathInfo.equals("/all")) {
            resp = ErrorStatusBuilder.setHttpStatus(resp, meterTypeController.getAll().getSystemMessage());
            JsonConverter.sendAsJson(resp, meterTypeController.getAll().getBody());
        } else {
            pathInfo = pathInfo.replaceFirst("/", "");
            try {
                long id = Long.parseLong(pathInfo);
                resp = ErrorStatusBuilder.setHttpStatus(resp, meterTypeController.getMeterById(id).getSystemMessage());
                JsonConverter.sendAsJson(resp, meterTypeController.getMeterById(id).getBody());
            } catch (NumberFormatException e) {
                resp = ErrorStatusBuilder.setHttpStatus(resp, meterTypeController
                        .getMeterByType(pathInfo).getSystemMessage());
                JsonConverter.sendAsJson(resp, meterTypeController.getMeterByType(pathInfo).getBody());
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            try {
                Object o = JsonConverter.fromJson(req);
                MeterTypeDto meterTypeDto = (MeterTypeDto) o;
                meterTypeDto = meterTypeController.add(meterTypeDto).getBody();
                resp = ErrorStatusBuilder.setHttpStatus(resp, meterTypeController.add(meterTypeDto).getSystemMessage());
                JsonConverter.sendAsJson(resp, meterTypeDto);
            } catch (ClassCastException e){
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                JsonConverter.sendAsJson(resp, null);
            }
        }
    }

}
