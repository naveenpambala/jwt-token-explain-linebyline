package com.web.utill;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtill {
	
	   private static final String SECRET_KEY = "secret";

	    private static final int TOKEN_VALIDITY = 3600 * 5;

	    public String getUsernameFromToken(String token) {
	        return getClaimFromToken(token, Claims::getSubject);//this is a part of heare order functions
	    }

	    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
	        final Claims claims = getAllClaimsFromToken(token);
	        return claimsResolver.apply(claims);
	    }

	    private Claims getAllClaimsFromToken(String token) {
	        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();//secrate key we are using anything from token so we are manually create the secretkey 
	    }

	    public Boolean validateToken(String token, UserDetails userDetails) {//token validation method
	        final String username = getUsernameFromToken(token);//calling first method here
	        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));//username taken fom user and username taken from token both are equal its ok but not equal  user is  not uthorized
//	        and !isTokenExpired() bellow method retun true the token is expired , its false token valid
	    }

	    private Boolean isTokenExpired(String token) {// if Token is Expired return false or true
	        final java.util.Date expiration = getExpirationDateFromToken(token);//calling the below methodher
	        return expiration.before(new java.util.Date());//new Date return the current date check my exparation date before my current date, my expartion date not before of current date it will return false token is not expired in that case 
	    }

	    public java.util.Date getExpirationDateFromToken(String token) {//TokenExpired
	        return getClaimFromToken(token, Claims::getExpiration);//this is a part of heare order functions
	    }

	    public String generateToken(UserDetails userDetails) {

	        Map<String, Object> claims = new HashMap<>();

	        return Jwts.builder()
	                .setClaims(claims)
	                .setSubject(userDetails.getUsername())
	                .setIssuedAt(new Date(System.currentTimeMillis()))
	                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY * 1000))
	                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
	                .compact();
	    }

}
