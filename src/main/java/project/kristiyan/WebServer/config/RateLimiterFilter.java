package project.kristiyan.WebServer.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimiterFilter extends OncePerRequestFilter {
    private final Map<String, SlidingWindow> clientWindows = new ConcurrentHashMap<>();

    @Value("${ratelimit.time_window_ms:1000}")
    private long windowSizeMs;

    @Value("${ratelimit.max_requests_per_time_window:2}")
    private int maxRequests;

    private static class SlidingWindow {
        private long lastResetTime = System.currentTimeMillis();
        private int requestCount = 0;
        private long lastAccessTime = System.currentTimeMillis();

        synchronized boolean tryAcquire(int maxRequests, long windowSizeMs) {
            long currentTime = System.currentTimeMillis();
            lastAccessTime = currentTime;

            if (currentTime - lastResetTime >= windowSizeMs) {
                // Reset the window
                lastResetTime = currentTime;
                requestCount = 0;
            }

            if (requestCount <= maxRequests) {
                requestCount++;
                return true;
            } else {
                return false;
            }
        }

        boolean isStale(long currentTime, long windowSizeMs) {
            // No need for synchronization here; reading stale data is fine for staleness check
            return currentTime - lastAccessTime > windowSizeMs * 10;
        }
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String clientIp = getClientIp(request);

        SlidingWindow window = clientWindows.computeIfAbsent(clientIp, k -> new SlidingWindow());

        if (!window.tryAcquire(maxRequests, windowSizeMs)) {
            // Rate limit exceeded
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.setContentType("application/json");
            response.getWriter().write(
                    "{\"error\":\"Rate limit exceeded\"}"
            );
            return;
        }
        filterChain.doFilter(request, response);
    }

    private String getClientIp(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        return (xForwardedFor != null && !xForwardedFor.isEmpty()) ?
                xForwardedFor.split(",")[0].trim() : request.getRemoteAddr();
    }

    @Scheduled(fixedRateString = "${ratelimit.cleanup_interval_ms:60000}")
    public void cleanup() {
        long currentTime = System.currentTimeMillis();
        clientWindows.entrySet().removeIf(entry ->
                entry.getValue().isStale(currentTime, windowSizeMs));
    }
}
