package com.dannyhromau.monitoring.meter.servlet.filter;

import com.dannyhromau.monitoring.meter.annotation.AspectLogging;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@AspectLogging
@WebFilter(urlPatterns = "/auth")
public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);
        String path = httpRequest.getRequestURI();
        if (path.equals("/api/v1/auth/login") || path.equals("/api/v1/auth/register")) {
            filterChain.doFilter(request, response);
            return;
        }
        if (session != null && session.getAttribute("authorities") != null) {
            filterChain.doFilter(request, response);
        } else {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    @Override
    public void destroy() {
    }
}
