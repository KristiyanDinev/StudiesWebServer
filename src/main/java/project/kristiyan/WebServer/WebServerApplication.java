package project.kristiyan.WebServer;

import gg.jte.CodeResolver;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.DirectoryCodeResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import project.kristiyan.database.Database;

import java.nio.file.Path;

@SpringBootApplication
public class WebServerApplication {
	public static Database database;

	public static void main(String[] args) {
		database = new Database(null);
		CodeResolver codeResolver = new DirectoryCodeResolver(Path.of("jte"));
		TemplateEngine templateEngine = TemplateEngine.create(
				new DirectoryCodeResolver(Path.of("jte")),
				ContentType.Html);
		SpringApplication.run(WebServerApplication.class, args);
	}

}
