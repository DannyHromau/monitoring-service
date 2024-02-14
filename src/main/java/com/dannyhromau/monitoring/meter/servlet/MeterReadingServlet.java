package com.dannyhromau.monitoring.meter.servlet;

import com.dannyhromau.monitoring.meter.annotation.AspectLogging;
import com.dannyhromau.monitoring.meter.api.ResponseEntity;
import com.dannyhromau.monitoring.meter.api.dto.MeterReadingDto;
import com.dannyhromau.monitoring.meter.controller.MeterReadingController;
import com.dannyhromau.monitoring.meter.core.util.ErrorStatusBuilder;
import com.dannyhromau.monitoring.meter.core.util.JsonConverter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

@AspectLogging
@WebServlet(name = "MeterReadingServlet", urlPatterns = {"/api/v1/meter/reading/*"})
public class MeterReadingServlet extends HttpServlet {

    private MeterReadingController meterReadingController;

    @Override
    public void init() throws ServletException {
        meterReadingController = (MeterReadingController) getServletContext().getAttribute("meterReadingController");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            JsonConverter.sendAsJson(resp, "");
        } else {
            switch (pathInfo) {
                case "/all" -> sendAll(resp);
                case "/month" -> sendByMonth(req, resp);
                case "/date" -> sendByDate(req, resp);
                case "/type" -> sendByType(req, resp);
                case "/actual" -> sendActual(req, resp);
                default -> sendById(pathInfo, resp);
            }
        }
    }

    private void sendById(String pathInfo, HttpServletResponse resp) throws IOException {
        try {
            long id = Long.parseLong(pathInfo);
            ResponseEntity<MeterReadingDto> re = meterReadingController.getById(id);
            resp = ErrorStatusBuilder.setHttpStatus(resp, re.getSystemMessage());
            JsonConverter.sendAsJson(resp, meterReadingController.getById(id).getBody());
        } catch (NumberFormatException e) {
            resp = ErrorStatusBuilder.setHttpStatus(resp, ErrorStatusBuilder.getStatus(e));
            JsonConverter.sendAsJson(resp, null);
        }
    }

    private void sendActual(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getParameterMap().size() != 2) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            JsonConverter.sendAsJson(resp, null);
        } else {
            try {
                long userId = Long.parseLong(req.getParameter("userId"));
                long meterTypeId = Long.parseLong(req.getParameter("meterTypeId"));
                ResponseEntity<MeterReadingDto> re = meterReadingController.getActualMeterReading(userId, meterTypeId);
                resp = ErrorStatusBuilder
                        .setHttpStatus(resp, re.getSystemMessage());
                JsonConverter.sendAsJson(resp, re.getBody());
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                JsonConverter.sendAsJson(resp, null);
            }
        }
    }

    private void sendByType(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getParameterMap().size() != 2) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            JsonConverter.sendAsJson(resp, null);
        } else {
            try {
                long userId = Long.parseLong(req.getParameter("userId"));
                long meterTypeId = Long.parseLong(req.getParameter("meterTypeId"));
                ResponseEntity<List<MeterReadingDto>> re = meterReadingController
                        .getByUserIdAndMeterType(userId, meterTypeId);
                resp = ErrorStatusBuilder
                        .setHttpStatus(resp, re.getSystemMessage());
                JsonConverter.sendAsJson(resp, re);
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                JsonConverter.sendAsJson(resp, null);
            }
        }
    }

    private void sendByDate(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getParameterMap().size() != 3) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            JsonConverter.sendAsJson(resp, null);
        } else {
            try {
                long userId = Long.parseLong(req.getParameter("userId"));
                LocalDate date = LocalDate.parse(req.getParameter("date"));
                long meterTypeId = Long.parseLong(req.getParameter("meterTypeId"));
                ResponseEntity<MeterReadingDto> re = meterReadingController
                        .getMeterReadingByDateAndMeterType(userId, date, meterTypeId);
                resp = ErrorStatusBuilder
                        .setHttpStatus(resp, re.getSystemMessage());
                JsonConverter.sendAsJson(resp, re);
            } catch (NumberFormatException | DateTimeException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                JsonConverter.sendAsJson(resp, null);
            }
        }
    }

    private void sendByMonth(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getParameterMap().size() != 3) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            JsonConverter.sendAsJson(resp, null);
        } else {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
                long userId = Long.parseLong(req.getParameter("userId"));
                YearMonth yearMonth = YearMonth.parse(req.getParameter("yearMonth"), formatter);
                long meterTypeId = Long.parseLong(req.getParameter("meterTypeId"));
                ResponseEntity<MeterReadingDto> re = meterReadingController
                        .getMeterReadingByMonthAndMeterType(userId, yearMonth, meterTypeId);
                resp = ErrorStatusBuilder.setHttpStatus(resp, re.getSystemMessage());
                JsonConverter.sendAsJson(resp, re);
            } catch (NumberFormatException | DateTimeException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                JsonConverter.sendAsJson(resp, null);
            }
        }
    }

    private void sendAll(HttpServletResponse resp) throws IOException {
        ResponseEntity<List<MeterReadingDto>> re = meterReadingController.getAll();
        resp = ErrorStatusBuilder.setHttpStatus(resp, re.getSystemMessage());
        JsonConverter.sendAsJson(resp, re.getBody());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            try {
                Object o = JsonConverter.fromJson(req);
                MeterReadingDto meterReadingDto = (MeterReadingDto) o;
                ResponseEntity<MeterReadingDto> re = meterReadingController.add(meterReadingDto);
                meterReadingDto = re.getBody();
                resp = ErrorStatusBuilder.setHttpStatus(resp, re.getSystemMessage());
                JsonConverter.sendAsJson(resp, meterReadingDto);
            } catch (ClassCastException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                JsonConverter.sendAsJson(resp, null);
            }
        }
    }
}
