package de.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;


@SpringBootApplication
public class LocalApplication {

	@Bean()
	public ServletContextTemplateResolver templateResolver() {
	    final ServletContextTemplateResolver resolver =
	            new ServletContextTemplateResolver();
	    resolver.setSuffix(".html");
	    resolver.setTemplateMode("HTML5");
	    resolver.setCacheable(false);
	    resolver.setCharacterEncoding("UTF-8");
	    return resolver;
	}

    public static void main(String[] args) {
        SpringApplication.run(LocalApplication.class, args);
    }
}