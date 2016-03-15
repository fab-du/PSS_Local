package de.app;

import java.io.IOException;

import org.springframework.boot.context.embedded.EmbeddedServletContainerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ClientStartUp implements ApplicationListener<EmbeddedServletContainerInitializedEvent> {
	
	@Override
	public void onApplicationEvent(EmbeddedServletContainerInitializedEvent event) {
		int port = event.getEmbeddedServletContainer().getPort();
		String url = "http://localhost:" + port;
		this.open(url);
	}

	boolean isNix(){
		String os = System.getProperty("os.name").toLowerCase().trim();
		return (os.indexOf( "nix") >=0 || os.indexOf( "nux") >=0);
	}
	
	boolean isWin(){
		String os = System.getProperty("os.name").toLowerCase().trim();
		return (os.indexOf("win") >= 0);
	}
	
	boolean isMac(){
		String os = System.getProperty("os.name").toLowerCase().trim();
		return (os.indexOf( "mac" ) >= 0);
	}
	
	private void open( String url ){
	
		String openApp = new String();
		
		if ( isNix() ) openApp = "xdg-open ";
		if ( isMac() ) openApp = "open ";
		if ( isWin() ) openApp = "rundll32 url.dll,FileProtocolHandler ";
		
		Runtime rt = Runtime.getRuntime();
		try {
			rt.exec(openApp + url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
