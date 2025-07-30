package project.kristiyan.WebServer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurifyConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .sessionManagement(AbstractHttpConfigurer::disable)
                .headers(headers -> headers
                        .contentSecurityPolicy(csp -> csp
                                .policyDirectives(
                                "default-src 'self';" +
                                "script-src 'self' https://cdn.jsdelivr.net https://stackpath.bootstrapcdn.com;"+
                                "style-src 'self' https://cdn.jsdelivr.net https://stackpath.bootstrapcdn.com 'unsafe-inline';"+
                                "font-src 'self' https://cdn.jsdelivr.net;"+
                                "connect-src 'self';"
                            ))
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)

                )
                .authorizeHttpRequests(auth ->
                        {
                            for (String path : SessionAuthorizationFilter.allowedPaths) {
                                auth = auth.requestMatchers(path).permitAll();
                            }
                            auth.anyRequest().authenticated();
                        })
                .addFilterBefore(new SessionAuthorizationFilter(),
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
