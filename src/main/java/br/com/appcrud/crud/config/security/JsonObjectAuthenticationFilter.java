package br.com.appcrud.crud.config.security;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.appcrud.crud.dto.UserLoginCredentialsDto;
import br.com.appcrud.crud.entity.UserEntity;
import lombok.RequiredArgsConstructor;





@RequiredArgsConstructor
public class JsonObjectAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	
	private final ObjectMapper objectMapper = new ObjectMapper();
	
    @Autowired
    AuthenticationManager authenticationManager;
    

    @Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
    	
		try {
			BufferedReader reader = request.getReader();
			StringBuilder sb = new StringBuilder();
			String line;
			while((line = reader.readLine()) != null) {
				sb.append(line);
			}
			UserEntity authRequest = objectMapper.readValue(sb.toString(), UserEntity.class);
			
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
					authRequest.getUsername(), authRequest.getPassword()
					);
			setDetails(request, token);
			return this.getAuthenticationManager().authenticate(token);
		} catch (IOException e) {		
			throw new RuntimeException("Falha na autenticação! ", e);
		}  	
    }
    
    
    
    
    
}
