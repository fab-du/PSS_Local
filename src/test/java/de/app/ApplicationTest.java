package de.app;

import javax.ws.rs.client.ClientRequestFilter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import de.app.client.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = LocalApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class ApplicationTest {
	
    @Value("${local.server.port}")
	int port;
    
    @Autowired
    ClientSession clientSession;
    @Autowired 
    ClientUser clientUser;
    @Autowired 
    ClientFriend clientFriend;
    
    @Before
    public void setup(){
    }
    
    @Test
    public void testRegister(){
    	
    }
    
    @Test
    public void testLogin(){
    	
    }
    
    @Test
    public void testLogout(){
    	
    }
    
}
