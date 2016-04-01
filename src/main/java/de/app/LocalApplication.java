package de.app;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import de.app.client.HeaderInterceptor;
import de.app.properties.AsymmetricCipherProperties;
import de.app.properties.HashProperties;
import de.app.properties.SaltProperties;
import de.app.properties.SymmetricCipherProperties;

@SpringBootApplication
@EnableConfigurationProperties({CryptoneProperties.class,
								SymmetricCipherProperties.class, 
								AsymmetricCipherProperties.class,
								HashProperties.class,
								SaltProperties.class
								})
public class LocalApplication {

	static Logger log = LoggerFactory.getLogger(LocalApplication.class);

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
	
    public static void main(String[] args) throws UnknownHostException {     
        ConfigurableEnvironment env = SpringApplication.run(LocalApplication.class, args).getEnvironment();
        String hostname = InetAddress.getLocalHost().getHostAddress();
        String localServerUrl = "http://" + hostname + ":" + env.getProperty("local.server.port"); 
        log.info( "Please copy this url to your browser:\t" + localServerUrl );
    }
}
