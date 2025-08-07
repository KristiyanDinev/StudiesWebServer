package project.kristiyan.WebServer.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;


@Configuration
@EnableJdbcHttpSession
public class SessionConfig {
}
