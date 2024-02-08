package com.dannyhromau.monitoring.meter.core.util;

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
    public static <T> T fromJson(HttpServletRequest req, T t) throws IOException {
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = req.getReader();
        while (reader.readLine() != null) {
            buffer.append(reader.readLine());
        }
        String json = buffer.toString();
        ObjectMapper mapper = new ObjectMapper();
        return (T) mapper.readValue(json, t.getClass());
    }
}
