package de.app;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import de.app.client.HeaderInterceptor;

@SpringBootApplication
@EnableConfigurationProperties(CryptoneProperties.class)
public class LocalApplication {

//	@Bean()
//	public ServletContextTemplateResolver templateResolver() {
//	    final ServletContextTemplateResolver resolver =
//	            new ServletContextTemplateResolver();
//	    resolver.setSuffix(".html");
//	    resolver.setTemplateMode("HTML5");
//	    resolver.setCacheable(false);
//	    resolver.setCharacterEncoding("UTF-8");
//	    return resolver;
//	}
//	
	/*
	 * Resolve for data up/down-load
	 */
	
//	
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
	
	//To resolve ${} in @Value
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
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
		List<ClientHttpRequestInterceptor> interceptors = template.getInterceptors();
		//interceptors.add( new SignatureInterceptor());
		interceptors.add( new HeaderInterceptor());
		template.setInterceptors(interceptors);
		return template;
	}
	
	
//	@Bean
//	public CommonsMultipartResolver multipartResolver(){
//		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
//		return multipartResolver;
//	}

    public static void main(String[] args) {     
        SpringApplication.run(LocalApplication.class, args);
    }
}
