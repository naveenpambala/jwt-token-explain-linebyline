package com.web.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.web.entity.JwtRequest;
import com.web.entity.JwtResponse;
import com.web.entity.Users;
import com.web.repo.UserRepo;
import com.web.utill.JwtUtill;

@Service
public class JwtService implements UserDetailsService{
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private JwtUtill jwtUtill;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	 public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception {
	       
		 String userName = jwtRequest.getUserName();
	        String userPassword = jwtRequest.getUserPassword();
	        authenticate(userName, userPassword);

	        UserDetails userDetails = loadUserByUsername(userName);
	        String newGeneratedToken = jwtUtill.generateToken(userDetails);

	        Users users = userRepo.findById(userName).get();
	        return new JwtResponse(users, newGeneratedToken);
	    }

	    @Override
	    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	        Users users = userRepo.findById(username).get();

	        if (users != null) {
	            return new org.springframework.security.core.userdetails.User(
	                    users.getUserName(),
	                    users.getUserPassword(),
	                    getAuthority(users)
	            );
	        } else {
	            throw new UsernameNotFoundException("User not found with username: " + username);
	        }
	    }

	    private Set getAuthority(Users users) {
	        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
	        users.getRole().forEach(role -> {
	            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
	        });
	        return authorities;
	    }

	    private void authenticate(String userName, String userPassword) throws Exception {
	        try {
	            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, userPassword));
	        } catch (DisabledException e) {
	            throw new Exception("USER_DISABLED", e);
	        } catch (BadCredentialsException e) {
	            throw new Exception("INVALID_CREDENTIALS", e);
	        }
	    }
}
