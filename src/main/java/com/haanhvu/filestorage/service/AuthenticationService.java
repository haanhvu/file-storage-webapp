package com.haanhvu.filestorage.service;

import java.util.ArrayList;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.haanhvu.filestorage.model.User;

@Service
public class AuthenticationService implements AuthenticationProvider{
	
	private UserService userService;
	private HashService hashService;
	
	public AuthenticationService(UserService userService, HashService hashService) {
		this.userService = userService;
		this.userService = userService;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationServiceException{
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();

		User user = this.userService.getUserByName(username);
		
		if(user != null) {
			String encodedSalt = user.getSalt();
			String hashedPassword = hashService.getHashedValue(password, encodedSalt);		
			if(user.getPassword().equals(hashedPassword)){
				return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
			}
		}
		
		return null;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
	
}