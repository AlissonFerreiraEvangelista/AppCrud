package br.com.appcrud.crud.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import br.com.appcrud.crud.service.UserDetailsServiceImpl;
import br.com.appcrud.crud.service.UserService;


@Configuration
public class SecurityConfiguration {
	
	@Autowired
	UserDetailsServiceImpl userService;

	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	AuthenSuccessHandler authenSuccessHandler;
		
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	UserService service;
	


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	
   	AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
   	authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);	
   	AuthenticationManager authenticationManager = authenticationManagerBuilder.build();
    	
        http
        	.cors()
        	.and()
        	.csrf().disable()
        	//.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            .authorizeHttpRequests((auth) -> {
				try {
					auth
						.antMatchers(HttpMethod.POST, "/login").permitAll()
						.antMatchers(HttpMethod.GET,"/swagger-ui.html").permitAll()
						.antMatchers(HttpMethod.GET,"/auth/findall").permitAll()
						.anyRequest().authenticated()
						.and()
						.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
						.addFilter(authenticationFilter())
						.addFilter(new JwtAuthorizationFilter(authenticationManager, userService))
						.authenticationManager(authenticationManager)
						.exceptionHandling()
						.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
					
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
				
				})
				
			.httpBasic(Customizer.withDefaults());         
        return http.build();
    }
  
    
    @Bean
    public JsonObjectAuthenticationFilter authenticationFilter() throws Exception {
    	JsonObjectAuthenticationFilter filter = new JsonObjectAuthenticationFilter();
    	filter.setAuthenticationSuccessHandler(authenSuccessHandler);
    	filter.setAuthenticationManager(authenticationManager);
    	return filter;
    }
    
   
    
    
   
}

