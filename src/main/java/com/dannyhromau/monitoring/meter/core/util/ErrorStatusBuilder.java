package com.dannyhromau.monitoring.meter.core.util;

import com.dannyhromau.monitoring.meter.exception.DuplicateDataException;
import com.dannyhromau.monitoring.meter.exception.InvalidDataException;
import com.dannyhromau.monitoring.meter.exception.UnAuthorizedException;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

public class ErrorStatusBuilder {
    public static final String STATUS_UNAUTHORIZED = "unauthorized";
    public static final String STATUS_SERVER_ERROR = "internal server error";
    public static final String STATUS_CONFLICT = "conflict";
    public static final String STATUS_BED_REQUEST = "bad request";
    public static String getStatus(Exception e) {
        if (e instanceof InvalidDataException || e instanceof IllegalArgumentException) {
            return STATUS_BED_REQUEST;
        } else if (e instanceof DuplicateDataException) {
            return STATUS_CONFLICT;
        } else if (e instanceof SQLException) {
            return STATUS_SERVER_ERROR;
        } else if (e instanceof UnAuthorizedException) {
            return STATUS_UNAUTHORIZED;
        } else {
            return "undefined status";
        }
    }

    public static HttpServletResponse setHttpStatus(HttpServletResponse resp, String systemMessage) {
        switch (systemMessage) {
            case (STATUS_SERVER_ERROR):
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            case (STATUS_CONFLICT):
                resp.setStatus(HttpServletResponse.SC_CONFLICT);
            case (STATUS_BED_REQUEST):
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            default:
                resp.setStatus(HttpServletResponse.SC_OK);
        }
        return resp;
    }
}
