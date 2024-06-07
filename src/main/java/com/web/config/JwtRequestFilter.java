package com.web.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.web.service.JwtService;
import com.web.utill.JwtUtill;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter{
	
	@Autowired
	private JwtUtill jwtUtill;
	
	@Autowired
	private JwtService  jwtService;
	
	
	   @Override
	    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

	        final String requestTokenHeader = request.getHeader("Authorization");//getting the header beacuse of we mention "Authorization" in header in postman

	        String username = null;
	        String jwtToken = null;

	        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {//we are send the Jwt token through Bearer and header starting with Bearer
	            jwtToken = requestTokenHeader.substring(7);
	            try {
	                username = jwtUtill.getUsernameFromToken(jwtToken);//retriving username from jwttoken,putting username creating the jwttoken all are diffrent things which are going to generate the jwt,retriving username from jwttoken and jwttolken expired or not all this kind of information stored in another package com.web.utill;
	            } catch (IllegalArgumentException e) {
	                System.out.println("Unable to get JWT Token");
	            } catch (ExpiredJwtException e) {
	                System.out.println("JWT Token has expired");
	            }
	        } else {
	            System.out.println("JWT token does not start with Bearer");
	        }

	        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

	            UserDetails userDetails = jwtService.loadUserByUsername(username);

	            if (jwtUtill.validateToken(jwtToken, userDetails)) {// we have validate token over here by using the jwttoken and userDetials so we have add validate method in JwtUtill
	            	// below logic is inbuoilt that can provided by spring security
	                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());//getAuthorities() is inbuilt method
	                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));//request is the first method  doFilterInternal HttpServletRequest
	                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
	            }
	        }
	        filterChain.doFilter(request, response);

	    }
// JwtRequestfilter is the class which will be responseble for trying to retriving authorization header,it will try to retrive bearer token	 on we got Bearer token it will
//	   try to getting user name and jwt token , wheater the token is expired or not and the token conains currect information or not and once token is validate and all 
//	   information is currect i will alow that particular request

}
