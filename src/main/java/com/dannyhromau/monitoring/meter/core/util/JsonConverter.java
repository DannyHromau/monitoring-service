package com.dannyhromau.monitoring.meter.core.util;

import com.dannyhromau.monitoring.meter.api.dto.AuthDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class JsonConverter {
    public static void sendAsJson(HttpServletResponse response,
                                  Object obj) throws IOException {
        response.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        String res;
        if (obj == null) {
            res = "null";
        } else {
            res = mapper.writeValueAsString(obj);
        }
        PrintWriter out = response.getWriter();
        out.print(res);
        out.flush();
    }

    public static Object fromJson(HttpServletRequest req) throws IOException {
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = req.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        String json = buffer.toString();
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, AuthDto.class);
    }
}
