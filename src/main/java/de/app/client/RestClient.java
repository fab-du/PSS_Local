package de.app.client;

import java.util.List;
import javax.annotation.PostConstruct;

import org.apache.commons.fileupload.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import jersey.repackaged.com.google.common.collect.ImmutableList;

@Component
public class RestClient{
	
	private final RestTemplate restTemplate;
	private final HttpHeaders headers;

	@Autowired
	public RestClient( RestTemplate restTemplate, HttpHeaders headers ) {
		this.restTemplate = restTemplate;
		List<HttpMessageConverter<?>> messageConverters = this.restTemplate.getMessageConverters();
	    for (HttpMessageConverter<?> converter : messageConverters) {
	        if (converter instanceof MappingJackson2HttpMessageConverter) {
	            MappingJackson2HttpMessageConverter jsonConverter = (MappingJackson2HttpMessageConverter) converter;
	            jsonConverter.setObjectMapper(new ObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false));

	            jsonConverter.setSupportedMediaTypes(ImmutableList.of(new MediaType("application", "json", MappingJackson2HttpMessageConverter.DEFAULT_CHARSET), new MediaType("text", "javascript", MappingJackson2HttpMessageConverter.DEFAULT_CHARSET)));
	        }
	    }
	    messageConverters.add( new ByteArrayHttpMessageConverter());
	    messageConverters.add( new FormHttpMessageConverter());
        
		this.restTemplate.setMessageConverters(messageConverters);
		this.headers = headers;
		//headers.add("Content-Type", "application/json");
	}
	
	@PostConstruct
	public void init(){	
	}
	
	public RestTemplate getRestTemplate(){
		return this.restTemplate;
	}
	
	public void setHeader(String headerName , String headerValue ){
		this.headers.add(headerName, headerValue);
	}
	
	public HttpHeaders getHeaders(){
		return this.headers;
	}
	public void clearHeaders(){
		this.headers.clear();
	}
}
