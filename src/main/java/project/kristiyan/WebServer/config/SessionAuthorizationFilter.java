package project.kristiyan.WebServer.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import project.kristiyan.WebServer.services.UserService;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class SessionAuthorizationFilter extends OncePerRequestFilter {

    // REGEX
    public static List<String> allowedPaths = List.of(
            "/assets/**",
            "/favicon.ico",
            "/home/**",
           // "/fonts/**",
            "/file/**"
    );

    private static final AntPathMatcher matcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        if (allowedPaths.stream().anyMatch(p -> matcher.match(p, path))) {
            filterChain.doFilter(request, response);
            return;
        }

        HttpSession session = request.getSession(false);
        if (session != null &&
                "true".equals(session.getAttribute(UserService.authSession))) {
            // Mark user as authenticated
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(
                            "sessionUser", null, Collections.emptyList()));

        } else if (request.getMethod().equals("POST")) {
            // Deny access
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;

        } else {
            // Redirect to Login for GET requests
            response.sendRedirect("/admin_login");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
