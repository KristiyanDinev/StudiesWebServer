package project.kristiyan.WebServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.security.autoconfigure.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
import project.kristiyan.database.Database;

@SpringBootApplication(exclude = {
        UserDetailsServiceAutoConfiguration.class
})
@EnableScheduling
public class WebServerApplication {
    public static Database database;

    public static void main(String[] args) {
        database = new Database(null);
        SpringApplication.run(WebServerApplication.class, args);
    }

}
