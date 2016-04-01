package de.app.controller;

import java.net.UnknownHostException;

import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.app.service.ServiceLocalServerInfos;


@RestController
@RequestMapping(value="/infos")
public class ControllerMisc {

@Autowired
ServiceLocalServerInfos localServerInfos;


@RequestMapping( value="/url", method = RequestMethod.GET )
@Produces("application/json")
public String localServerUrl() throws UnknownHostException{
	return localServerInfos.localServerUrl();
}

}
