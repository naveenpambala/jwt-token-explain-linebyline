package com.web.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class JwtResponse {

	private Users users;
	private String jwtToken;
	
	 

	 
	
	
	
}
