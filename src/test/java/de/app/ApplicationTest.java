package de.app;

import com.jayway.*;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;

import static com.jayway.restassured.RestAssured.expect;
import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.config.EncoderConfig.encoderConfig;
import static com.jayway.restassured.config.JsonConfig.jsonConfig;
import static com.jayway.restassured.config.RedirectConfig.redirectConfig;
import static com.jayway.restassured.config.RestAssuredConfig.newConfig;
import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;

import org.aspectj.lang.annotation.After;
import org.mockito.*;
import org.assertj.*;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.github.javafaker.Faker;

import de.app.client.*;
import de.app.model.form.FormLogin;
import de.app.model.form.FormRegister;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = LocalApplication.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@IntegrationTest("server.port:0")
public class ApplicationTest {
	
	private final static Logger log = LoggerFactory.getLogger( ApplicationTest.class);

    @Value("${local.server.port}")
	int port;
    @Autowired
    ClientSession clientSession;
    @Autowired 
    ClientUser clientUser;
    @Autowired 
    ClientFriend clientFriend;
    @Autowired
    RestClient restClient;
    Faker faker;
    
    FormRegister reg_user;
    FormLogin login_user;
    
    String url; 
    String url_login;
    String url_logout;
    String url_register;
    String url_users;
    String url_documents;
    String url_friends;
    
    @Before
    public void setup(){
    	url = new String("http://localhost:") + port;
    	url_login = url + "/session/login";
    	url_logout = url + "/session/logout";
    	url_register= url + "/session/register";
    	faker = new Faker();
    	
    	reg_user = new FormRegister();
    	reg_user.setCompany(faker.company().name());
    	reg_user.setEmail(faker.internet().emailAddress());
    	reg_user.setFirstname(faker.name().name());
    	reg_user.setSecondname(faker.name().lastName());
    	reg_user.setPassphrase("passphrase");
    	reg_user.setPassword("password");
    	
    	login_user = new FormLogin();
    	login_user.setEmail( reg_user.getEmail() );
    	login_user.setPassword("password");
    	login_user.setPassphrase("passphrase");
    	
    	
    	RestAssured.baseURI = "http://localhost";
    	RestAssured.port = port;
    }
    
    @Test
    public void testRegisterSuccess(){
    	given()
    		.body(reg_user)
    		.contentType(ContentType.JSON)
    	.when()
    		.post("/session/register")
    			.then()
    				.statusCode(HttpStatus.CREATED.value());
    }
    
    @Test
    public void testRegisterFailedOnMissingArgument(){
    	reg_user.setEmail(null);
    	given()
			.body(reg_user)
			.contentType(ContentType.JSON)
		.when()
			.post("/session/register")
				.then()
					.statusCode(HttpStatus.BAD_REQUEST.value());
    }
    
    @Test
    public void testLoginFailedOnMissingArguments(){
    	FormLogin login = new FormLogin();
    	
    	given()
		.body(login)
		.contentType(ContentType.JSON)
		.when()
		.post("/session/login")
			.then()
				.statusCode(HttpStatus.BAD_REQUEST.value());
    }
    
    @Test
    public void testLoginFailedOnMissingArgumentEmail(){
    	FormLogin login = new FormLogin();
    	login.setPassword("password");
    	
    	given()
		.body(login)
		.contentType(ContentType.JSON)
		.when()
		.post("/session/login")
			.then()
				.statusCode(HttpStatus.BAD_REQUEST.value());
    }
    
    @Test
    public void testLoginFailedOnMissingArgumentPassword(){
    	FormLogin login = new FormLogin();
    	login.setEmail("email@email.com");
    	
    	given()
		.body(login)
		.contentType(ContentType.JSON)
		.when()
		.post("/session/login")
			.then()
				.statusCode(HttpStatus.BAD_REQUEST.value());
    }
    
    @Test
    public void testLoginByNotProvidingPassphrase(){
    	FormLogin login = new FormLogin();
    	login.setEmail("dodo@dodo.com");
    	//login.setPassphrase("passphrase");
    	login.setPassword("dodo");
    	
    	given()
		.body(login)
		.contentType(ContentType.JSON)
		.when()
		.post("/session/login")
			.then()
				.statusCode(HttpStatus.OK.value());
    	
    	clientSession.logout();
    }
    
    @Test
    public void testFullLogin(){
    	FormLogin login = new FormLogin();
    	login.setEmail("dodo@dodo.com");
    	login.setPassphrase("dodo");
    	login.setPassword("dodo");
    	
    	given()
		.body(login)
		.contentType(ContentType.JSON)
		.when()
		.post("/session/login")
			.then()
				.statusCode(HttpStatus.OK.value());
    	
    	clientSession.logout();
    }
    
}
