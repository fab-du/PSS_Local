package de.app.utils;

import de.app.model.Session;
import io.jsonwebtoken.Jwts; 
import io.jsonwebtoken.Claims;


public class TokenUtils {

    public Session parseToken( String token, String B ) throws Exception{
        Claims claims = Jwts.parser().setSigningKey( B ).parseClaimsJws(token).getBody();
        return this.claimsToSessionObj( claims );
    }


    public String generateToken( Session session ){
    	return null;
    }

    private Session claimsToSessionObj( Claims claim ){
        Session session = new Session();
        
        String email = claim.getSubject();
        Long id = new Long(claim.getId());
        
        session.setId(id);
        session.setEmail( email );
        return session;
    }
}
