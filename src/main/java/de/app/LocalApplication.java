package de.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
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
	
	/*
	 * Resolve for data up/down-load
	 */
	
//	@Bean
//	public CommonsMultipartResolver multipartResolver(){
//		final CommonsMultipartResolver resolver = new CommonsMultipartResolver();
//		resolver.setMaxUploadSize(500000);
//		return resolver;
//	}
	
	@Bean
	public HttpHeaders headers(){
		return new HttpHeaders();
	}
	
	/**
	 * Create a new instance of the {@link RestTemplate} using default settings.
	 * Default {@link HttpMessageConverter}s are initialized.
	 * 
	 *  Those converter are provided : 
	 * 	ByteArrayHttpMessageConverter
	 *	StringHttpMessageConverter
	 *  ResourceHttpMessageConverter
	 *	SourceHttpMessageConverter
	 *  AllEncompassingFormHttpMessageConverter
	 */
	@Bean
	public RestTemplate restTemplate(){
		RestTemplate template = new RestTemplate();
		return new RestTemplate();
	}
	
//	@Bean
//	public CommonsMultipartResolver multipartResolver(){
//		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
//		multipartResolver.setMaxUploadSize(5000000);
//		return multipartResolver;
//	}

    public static void main(String[] args) {
        SpringApplication.run(LocalApplication.class, args);
    }
}