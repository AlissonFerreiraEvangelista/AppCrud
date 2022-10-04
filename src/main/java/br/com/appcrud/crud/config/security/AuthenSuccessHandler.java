package br.com.appcrud.crud.config.security;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.appcrud.crud.constants.SecurityConstants.Constants;
import br.com.appcrud.crud.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AuthenSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{
	@Autowired
	UserService userService;

	@Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, 
    		Authentication authentication) throws IOException {
		
		UserDetails principal = (UserDetails) authentication.getPrincipal();
    	
    	String token = JWT.create()
    			.withSubject(principal.getUsername())
    			.withExpiresAt(Instant.ofEpochMilli(
    					ZonedDateTime.now(
    						ZoneId.systemDefault())
    						.toInstant()
    						.toEpochMilli() + Constants.EXPIRATION_TIME))
    			.sign(Algorithm.HMAC512(Constants.TOKEN_PASSWORD));
    	response.addHeader("Authorization","Bearer " + token);
    	response.addHeader("Content-Type","application/json");
    	response.getWriter().write("{\"token\": \"" + token + "\"}");
    	
    }
}
