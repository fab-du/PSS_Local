package de.app.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import de.app.CryptoneProperties;
import de.app.model.form.FormAuthentication;
import de.app.model.form.FormChallengeResponse;
import de.app.model.form.FormCrudResponse;
import de.app.model.form.FormLoginAuthenticateResponse;
import de.app.model.form.FormLoginChallenge;
import de.app.model.form.FormRegister;

@Service
public class ClientSession{
	
	CryptoneProperties env;
	
	//CRES. CREQ
	private  AbstractWriteRequest<FormChallengeResponse, FormLoginChallenge> writerLoginChallenge;
	private  AbstractWriteRequest<FormLoginAuthenticateResponse, FormAuthentication> writerLoginAuthenticate;
	private  AbstractWriteRequest<FormCrudResponse, FormRegister> writerRegister;
	private  AbstractWriteRequest<?, ?> writerLogout;

	@Autowired
	public ClientSession( RestClient client, CryptoneProperties env ) {
		this.env = env;

		writerLoginChallenge = new AbstractWriteRequest<FormChallengeResponse, FormLoginChallenge>(client, FormChallengeResponse.class, FormLoginChallenge.class );
		writerLoginChallenge.setUri(env.getChallenge());
		writerLoginChallenge.setUrl( env.getUrl());
		
		writerLoginAuthenticate = new AbstractWriteRequest<FormLoginAuthenticateResponse,FormAuthentication >(client, FormLoginAuthenticateResponse.class, FormAuthentication.class );
		writerLoginAuthenticate.setUri(env.getAuthenticate());
		writerLoginAuthenticate.setUrl( env.getUrl());

		
		writerLoginAuthenticate = new AbstractWriteRequest<FormLoginAuthenticateResponse,FormAuthentication >(client, FormLoginAuthenticateResponse.class, FormAuthentication.class );
		writerLoginAuthenticate.setUri(env.getAuthenticate());
		writerLoginAuthenticate.setUrl(env.getUrl());

		
		writerRegister = new AbstractWriteRequest<FormCrudResponse, FormRegister>(client, FormCrudResponse.class, FormRegister.class );
		writerRegister.setUri(env.getRegister());
		writerRegister.setUrl(env.getUrl());
		
		writerLogout = new AbstractWriteRequest<>(client, String.class, String.class );
		writerLogout.setUri(env.getLogout());
		writerLogout.setUrl(env.getUrl());
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
