package com.dannyhromau.monitoring.meter.servlet.filter;

import com.dannyhromau.monitoring.meter.annotation.AspectLogging;
import com.dannyhromau.monitoring.meter.model.Authority;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@AspectLogging
@WebFilter(urlPatterns = "/api/v1/meter/reading/all")
public class AuthorizationFilter implements Filter {

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
        if (session != null && hasAdminRole(session)) {
            filterChain.doFilter(request, response);
        } else {
            httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    @Override
    public void destroy() {
    }

    private boolean hasAdminRole(HttpSession session) {
        List<Authority> authorities = (List<Authority>) session.getAttribute("authorities");
        Optional<Authority> admin = authorities.stream().filter(a -> a.getName().equals("admin")).findFirst();
        return admin.isPresent();
    }
}