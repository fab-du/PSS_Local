package de.app;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.apache.http.HttpHost;

@Component
public class RestRequest implements FactoryBean<RestTemplate>, 
InitializingBean{

	 private RestTemplate restTemplate;
	 
	@Override
	public void afterPropertiesSet() throws Exception {
		HttpHost host = new HttpHost("localhost", 8080, "http");
		restTemplate = new RestTemplate(
		new ClientContext(host));

		restTemplate.getMessageConverters().add( new ByteArrayHttpMessageConverter());
		restTemplate.getMessageConverters().add( new StringHttpMessageConverter());
		restTemplate.getMessageConverters().add( new ResourceHttpMessageConverter());
	    restTemplate.getMessageConverters().add(new AllEncompassingFormHttpMessageConverter());
	}

	@Override
	public RestTemplate getObject() throws Exception {
		return restTemplate;
	}

	@Override
	public Class<?> getObjectType() {
		return RestTemplate.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
	

}
