package project.kristiyan.WebServer.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<SecurityHeadersFilter> securityHeadersFilter() {
        FilterRegistrationBean<SecurityHeadersFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new SecurityHeadersFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(2);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<SessionAuthorizationFilter> sessionFilter() {
        FilterRegistrationBean<SessionAuthorizationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new SessionAuthorizationFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(3);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<RateLimiterFilter> rateLimiterFilter() {
        FilterRegistrationBean<RateLimiterFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new RateLimiterFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }
}
