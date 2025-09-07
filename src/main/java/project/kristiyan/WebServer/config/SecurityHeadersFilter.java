package project.kristiyan.WebServer.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class SecurityHeadersFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse resp = (HttpServletResponse) response;
        resp.setHeader("Content-Security-Policy",
                "default-src 'self'; " +
                        "script-src 'self' https://cdn.jsdelivr.net https://stackpath.bootstrapcdn.com; " +
                        "style-src 'self' https://cdn.jsdelivr.net https://stackpath.bootstrapcdn.com 'unsafe-inline'; " +
                "font-src 'self' https://cdn.jsdelivr.net; " +
                        "connect-src 'self' https://cdn.jsdelivr.net;");
        resp.setHeader("X-Frame-Options", "SAMEORIGIN");
        chain.doFilter(request, response);
    }
}
