package de.app.client;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;

public class HeaderInterceptor implements ClientHttpRequestInterceptor{

	@Autowired
	RestClient restclient;
	
	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
		System.out.println("commmme rerer");
		HttpRequest wrapper = new HttpRequestWrapper(request);
		wrapper.getHeaders().set("fooo", "fooo");
		return execution.execute(wrapper, body);
	}

}
