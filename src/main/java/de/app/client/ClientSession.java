package de.app.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import de.app.model.form.FormAuthentication;
import de.app.model.form.FormChallengeResponse;
import de.app.model.form.FormCrudResponse;
import de.app.model.form.FormLoginAuthenticateResponse;
import de.app.model.form.FormLoginChallenge;
import de.app.model.form.FormRegister;

@Component
public class ClientSession{
	
	@Value("${client.uri.session.login.challenge}")
	private String uri_challenge = "/session/login/challenge";
	@Value("${client.uri.session.login.authenticate}")
	private String uri_authenticate ="/session/login/authenticate";
	//@Value("${client.uri.session.register}")
	private String uri_register="/session/register";
	@Value("${client.uri.session.logout}")
	private String uri_logout="/session/logout";

	//CRES. CREQ
	private  AbstractWriteRequest<FormChallengeResponse, FormLoginChallenge> writerLoginChallenge;
	private  AbstractWriteRequest<FormLoginAuthenticateResponse, FormAuthentication> writerLoginAuthenticate;
	private  AbstractWriteRequest<FormCrudResponse, FormRegister> writerRegister;
	private  AbstractWriteRequest<?, ?> writerLogout;

	@Autowired
	public ClientSession(RestClient client ) {
		writerLoginChallenge = new AbstractWriteRequest<FormChallengeResponse, FormLoginChallenge>(client, FormChallengeResponse.class, FormLoginChallenge.class );
		writerLoginChallenge.setUri(uri_challenge);
		
		writerLoginAuthenticate = new AbstractWriteRequest<FormLoginAuthenticateResponse,FormAuthentication >(client, FormLoginAuthenticateResponse.class, FormAuthentication.class );
		writerLoginAuthenticate.setUri(uri_authenticate);
		
		writerRegister = new AbstractWriteRequest<FormCrudResponse, FormRegister>(client, FormCrudResponse.class, FormRegister.class );
		writerRegister.setUri(uri_register);
		
		writerLogout = new AbstractWriteRequest<>(client, String.class, String.class );
		writerLogout.setUri(uri_logout);
	}

	public ResponseEntity<FormCrudResponse> register( FormRegister registerData ){
		return writerRegister.create(registerData);
	}
	
	public ResponseEntity<FormChallengeResponse> loginChallenge( FormLoginChallenge challenge ){
		return writerLoginChallenge.create(challenge);
	}
	
	public  ResponseEntity<FormLoginAuthenticateResponse> loginAuthenticate( FormAuthentication authData ){
		return writerLoginAuthenticate.create( authData );
	}
	
	public ResponseEntity<?> logout(){
		return  writerLogout.create(null, new String("logout") );
	}
}
