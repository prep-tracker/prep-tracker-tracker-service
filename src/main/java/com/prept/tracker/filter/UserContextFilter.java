package com.prept.tracker.filter;

import com.prept.tracker.context.UserContext;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class UserContextFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String userIdStr = httpRequest.getHeader("X-User-Id");
        String username = httpRequest.getHeader("X-User-Name");
        String rolesStr = httpRequest.getHeader("X-User-Roles");
        String plan = httpRequest.getHeader("X-User-Plan");

        if (userIdStr != null) {
            try {
                UserContext.setUserId(Long.parseLong(userIdStr));
            } catch (NumberFormatException e) {
                // ignore
            }
        }
        
        if (username != null) {
            UserContext.setUsername(username);
        }

        if (rolesStr != null) {
            List<String> roles = Arrays.stream(rolesStr.split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .toList();
            UserContext.setRoles(roles);
        } else {
            UserContext.setRoles(Collections.emptyList());
        }

        if (plan != null) {
            UserContext.setPlan(plan);
        } else {
            UserContext.setPlan("FREE");
        }

        try {
            chain.doFilter(request, response);
        } finally {
            UserContext.clear();
        }
    }
}
