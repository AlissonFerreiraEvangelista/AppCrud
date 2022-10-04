package br.com.appcrud.crud.config.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.appcrud.crud.constants.SecurityConstants.Constants;
import br.com.appcrud.crud.service.UserDetailsServiceImpl;
import io.jsonwebtoken.Jwts;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter{
	
	private final UserDetailsServiceImpl userService;
	
	
	public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserDetailsServiceImpl userService) {
		super(authenticationManager);
		this.userService = userService;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		String header = request.getHeader(Constants.HEADER_AUTH);
		if(header == null || !header.startsWith(Constants.PARAM_BEARER)) {
			return;
		}
		
		UsernamePasswordAuthenticationToken auth = getAuthentication(request);
		
		if(auth == null) {
			chain.doFilter(request, response);
			return;
		}
				
		SecurityContextHolder.getContext().setAuthentication(auth);
		chain.doFilter(request, response);
		
	}
	
	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
	    
		String token = request.getHeader(Constants.HEADER_AUTH);
		
		// if(token != null) {
		    
		// 	String username = JWT.require(Algorithm.HMAC512(Constants.TOKEN_PASSWORD))
		// 			.build()
		// 			.verify(token.replace(Constants.PARAM_BEARER, ""))
		// 			.getSignature();
		// 	if(username != null){
		// 		UserDetails userDetails = userService.loadUserByUsername(username);
		// 		return new UsernamePasswordAuthenticationToken(userDetails, null, new ArrayList<>());
				
		// 	}
		// 	return null;
		// }
		// return null;
		if(token != null) {
		    
			String username = JWT.require(Algorithm.HMAC512(Constants.TOKEN_PASSWORD))
			.build()
			.verify(token.replace(Constants.PARAM_BEARER, ""))
			.getSubject();
			if(username != null){
				UserDetails userDetails = userService.loadUserByUsername(username);
				return new UsernamePasswordAuthenticationToken(userDetails.getUsername(),userDetails.getPassword(), userDetails.getAuthorities());
				
			}
			return null;
		}
		return null;
		
		
		
	}

}
