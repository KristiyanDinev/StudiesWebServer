package project.kristiyan.WebServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import project.kristiyan.database.Database;

@SpringBootApplication(exclude = {
		UserDetailsServiceAutoConfiguration.class
})
public class WebServerApplication {
	public static Database database;

	public static void main(String[] args) {
		database = new Database(null);
		SpringApplication.run(WebServerApplication.class, args);
	}

}
