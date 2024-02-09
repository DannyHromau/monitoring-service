package com.dannyhromau.monitoring.meter.servlet;

import com.dannyhromau.monitoring.meter.annotation.AspectLogging;
import com.dannyhromau.monitoring.meter.api.ResponseEntity;
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
import java.util.List;

@AspectLogging
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
            ResponseEntity<List<MeterTypeDto>> re = meterTypeController.getAll();
            resp = ErrorStatusBuilder.setHttpStatus(resp, re.getSystemMessage());
            JsonConverter.sendAsJson(resp, re.getBody());
        } else {
            pathInfo = pathInfo.replaceFirst("/", "");
            try {
                long id = Long.parseLong(pathInfo);
                ResponseEntity<MeterTypeDto> re = meterTypeController.getMeterById(id);
                resp = ErrorStatusBuilder.setHttpStatus(resp, re.getSystemMessage());
                JsonConverter.sendAsJson(resp, re.getBody());
            } catch (NumberFormatException e) {
                ResponseEntity<MeterTypeDto> re = meterTypeController.getMeterByType(pathInfo);
                resp = ErrorStatusBuilder.setHttpStatus(resp, re.getSystemMessage());
                JsonConverter.sendAsJson(resp, re.getBody());
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
                ResponseEntity<MeterTypeDto> re = meterTypeController.add(meterTypeDto);
                meterTypeDto = re.getBody();
                resp = ErrorStatusBuilder.setHttpStatus(resp, re.getSystemMessage());
                JsonConverter.sendAsJson(resp, meterTypeDto);
            } catch (ClassCastException e){
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                JsonConverter.sendAsJson(resp, null);
            }
        }
    }

}
