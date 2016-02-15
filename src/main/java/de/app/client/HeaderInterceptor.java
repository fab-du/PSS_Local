package de.app.client;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;

import de.crypto.Hash;

public class HeaderInterceptor implements ClientHttpRequestInterceptor{

	@Autowired
	RestClient restclient;
	
	
	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
		HttpRequest wrapper = new HttpRequestWrapper(request);
		Hash hash = new Hash();
		String md = hash.hash(body);
		wrapper.getHeaders().add("hash", md);
		return execution.execute(wrapper, body);
	}
}

