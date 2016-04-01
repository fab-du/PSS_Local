package de.app.service;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class ServiceLocalServerInfos {

@Autowired
ApplicationContext context;

public String localServerUrl() throws UnknownHostException{
    String hostname = InetAddress.getLocalHost().getHostAddress();
	return "http://" + hostname + ":" + context.getEnvironment().getProperty("local.server.port"); 
}

}
